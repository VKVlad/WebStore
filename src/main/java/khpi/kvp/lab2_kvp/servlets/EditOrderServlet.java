package khpi.kvp.lab2_kvp.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import khpi.kvp.lab2_kvp.dao.DAOGood;
import khpi.kvp.lab2_kvp.dao.DAOOrder;
import khpi.kvp.lab2_kvp.dao.DAOUser;
import khpi.kvp.lab2_kvp.entity.Good;
import khpi.kvp.lab2_kvp.entity.Order;
import khpi.kvp.lab2_kvp.entity.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet("/editOrder")
public class EditOrderServlet extends HttpServlet {
    private DAOOrder daoOrder = new DAOOrder();
    private DAOGood daoGood = new DAOGood();
    private DAOUser daoUser = new DAOUser();
    public void init() throws ServletException {
        super.init();
        daoOrder = new DAOOrder();
        daoGood = new DAOGood();
        daoUser = new DAOUser();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder jsonData = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonData.append(line);
        }

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonData.toString(), JsonObject.class);

        Good good = daoGood.findById(jsonObject.get("good").getAsLong());
        Long id = jsonObject.get("id").getAsLong();
        int count = jsonObject.get("count").getAsInt();
        double priceOrder;
        if(count >= 3) {
               priceOrder = count * good.getPriceOpt();
        } else {
            priceOrder = count * good.getPrice();
        }
        LocalDate sendTime = null;
        String senddd = jsonObject.get("sendTime").getAsString();
        if(senddd != null && !senddd.isEmpty()) {
            sendTime = LocalDate.parse(jsonObject.get("sendTime").getAsString());
        }
        LocalDate sendTake = LocalDate.parse(jsonObject.get("sendTake").getAsString());
        User receiver = daoUser.findById(jsonObject.get("receiver").getAsLong());
        String manager = jsonObject.get("manager").getAsString();
        Order order = new Order(receiver, manager, good, count, priceOrder, sendTake, sendTime);
        daoOrder.update(id, order);
        resp.sendRedirect(req.getContextPath() + "/showOrders");
    }
}
