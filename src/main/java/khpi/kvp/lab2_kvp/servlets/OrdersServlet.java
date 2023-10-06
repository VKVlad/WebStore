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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("userId");
        User user = daoUser.findById(userId);

        String goodId = req.getParameter("productId");

        Good good = daoGood.findById(Long.valueOf(goodId));

        Order order = new Order(user, "manager1", good, 1, good.getPrice(), LocalDate.now());
        Order findOrder = daoOrder.findByKey(order, user, good);
        if (findOrder == null) {
            daoOrder.insert(order);
            String successme = "Success";
            System.out.println("Setting successMessage attribute");
            req.setAttribute("successMessage", successme);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/productServlet");
            dispatcher.forward(req, resp);
        } else {
            String errorMessage = "You ordered it already";
            req.setAttribute("errorMessage", errorMessage);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/productServlet");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
