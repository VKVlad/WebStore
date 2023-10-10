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


@WebServlet("/deleteGood")
public class DeleteGoodServlet extends HttpServlet {
    private DAOGood daoGood = new DAOGood();
    private DAOOrder daoOrder = new DAOOrder();
    public void init() throws ServletException {
        super.init();
        daoGood = new DAOGood();
        daoOrder = new DAOOrder();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String goodId = req.getParameter("productId");
        Good good = daoGood.findById(Long.valueOf(goodId));
        List<Order> orders = daoOrder.getListByGood(good);
        for(Order or : orders) {
            daoOrder.delete(or.getId());
        }
        daoGood.delete(Long.valueOf(goodId));

        if (daoGood.findById(Long.valueOf(goodId)) == null) {
            String successMessageDelete = "Removal completed successfully";
            session.setAttribute("successMessageDelete", successMessageDelete);
        } else {
            String errorMessageDelete = "Removal failed";
            session.setAttribute("errorMessageDelete", errorMessageDelete);
        }
        session.removeAttribute("productsFilter");
        resp.sendRedirect(req.getContextPath() + "/productServlet");
    }

}
