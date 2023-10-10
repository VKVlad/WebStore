package khpi.kvp.lab2_kvp.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import khpi.kvp.lab2_kvp.dao.DAOGood;
import khpi.kvp.lab2_kvp.dao.DAOOrder;
import khpi.kvp.lab2_kvp.entity.Good;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/editGood")
public class EditGoodServlet extends HttpServlet {
    private DAOGood daoGood = new DAOGood();
    public void init() throws ServletException {
        super.init();
        daoGood = new DAOGood();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Read the JSON data from the request
        BufferedReader reader = request.getReader();
        StringBuilder jsonData = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonData.append(line);
        }

        Gson gson = new Gson();
        Good product = gson.fromJson(jsonData.toString(), Good.class);
        daoGood.update(product.getId(), product);
        HttpSession session = request.getSession();
        session.removeAttribute("productsFilter");
        response.sendRedirect(request.getContextPath() + "/productServlet");
    }
}
