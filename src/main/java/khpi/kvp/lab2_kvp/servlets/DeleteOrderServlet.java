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
import khpi.kvp.lab2_kvp.entity.Order;

import java.io.IOException;
import java.util.List;

@WebServlet("/deleteOrder")
public class DeleteOrderServlet extends HttpServlet {
    private DAOOrder daoOrder = new DAOOrder();
    public void init() throws ServletException {
        super.init();
        daoOrder = new DAOOrder();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String orderId = req.getParameter("orderId");
        daoOrder.delete(Long.valueOf(orderId));
        if (daoOrder.findById(Long.valueOf(orderId)) == null) {
            String successMessageDelete = "Removal completed successfully";
            session.setAttribute("successMessageDelete", successMessageDelete);
        } else {
            String errorMessageDelete = "Removal failed";
            session.setAttribute("errorMessageDelete", errorMessageDelete);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("/showOrders");
        dispatcher.forward(req, resp);
    }
}
