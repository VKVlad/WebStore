package khpi.kvp.lab2_kvp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import khpi.kvp.lab2_kvp.dao.DAOOrder;
import khpi.kvp.lab2_kvp.dao.DAOUser;
import khpi.kvp.lab2_kvp.entity.Good;
import khpi.kvp.lab2_kvp.entity.Order;
import khpi.kvp.lab2_kvp.entity.User;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/showOrders")
public class ShowOrdersServlet extends HttpServlet {
    private DAOOrder daoOrder;
    private DAOUser daoUser;

    @Override
    public void init() throws ServletException {
        super.init();
        // Initialize the DAOUser
        daoUser = new DAOUser();
        daoOrder = new DAOOrder();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("userId");

        if (userId != null) {
            User user = daoUser.findById(userId);

            List<Good> goods = new ArrayList<>();
            List<Order> orders = daoOrder.getListByUser(user);

            if (orders != null) {
                for (Order order : orders) {
                    goods.add(order.getGood());
                }
            }
            req.setAttribute("orders", goods);
        } else {
            req.setAttribute("errorMessage", "User ID not found in the session.");
        }
        String successMessageDelete = (String) session.getAttribute("successMessageDelete");
        if (successMessageDelete != null) {
            session.removeAttribute("successMessageDelete");
            req.setAttribute("successMessageDelete", successMessageDelete);
        }

        String errorMessageDelete = (String) session.getAttribute("errorMessageDelete");
        if (errorMessageDelete != null) {
            session.removeAttribute("errorMessageDelete");
            req.setAttribute("errorMessageDelete", errorMessageDelete);
        }
        req.getRequestDispatcher("/Orders.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/showOrders");
    }
}