package khpi.kvp.lab2_kvp.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import khpi.kvp.lab2_kvp.dao.DAOGood;
import khpi.kvp.lab2_kvp.entity.Good;

import java.io.IOException;
import java.util.List;

@WebServlet("/productServlet")
public class ProductListServlet extends HttpServlet {

    private static final int NUM_PRODUCTS = 10;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Good> list = (new DAOGood()).getAllList();
        if(list == null) {
            System.out.println("Null");
        } else {
            System.out.println("Not null");
        }
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        session = request.getSession();
        session.setAttribute("userId", userId);
        request.setAttribute("products", list);
        request.getRequestDispatcher("/ProductList.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
