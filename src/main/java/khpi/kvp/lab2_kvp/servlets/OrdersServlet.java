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
import khpi.kvp.lab2_kvp.dao.DAOUser;
import khpi.kvp.lab2_kvp.entity.Good;
import khpi.kvp.lab2_kvp.entity.Order;
import khpi.kvp.lab2_kvp.entity.User;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/ordersServlet")
public class OrdersServlet extends HttpServlet {
    private DAOUser daoUser = new DAOUser();
    private DAOOrder daoOrder = new DAOOrder();
    private DAOGood daoGood = new DAOGood();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("userId");
        User user = daoUser.findById(userId);

        String goodId = req.getParameter("productId");
        String quantityParam = req.getParameter("quantity");
        int quantity = Integer.parseInt(quantityParam);
        Good good = daoGood.findById(Long.valueOf(goodId));
        double price;
        if(quantity >= 3){
            price = good.getPriceOpt() * quantity;
        } else {
            price = good.getPrice() * quantity;
        }
        Order order = new Order(user, "manager1", good, quantity, price, LocalDate.now());
        Order findOrder = daoOrder.findByKey(order, user, good);
        if (findOrder == null) {
            daoOrder.insert(order);
            String successMessage = "Order placed successfully";
            session.setAttribute("successMessage", successMessage);
        } else {
            String errorMessage = "You have already ordered this product";
            session.setAttribute("errorMessage", errorMessage);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("/productServlet");
        dispatcher.forward(req, resp);
    }
}