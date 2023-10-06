package khpi.kvp.lab2_kvp.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import khpi.kvp.lab2_kvp.dao.DAOUser;
import khpi.kvp.lab2_kvp.entity.User;

import java.io.IOException;

@WebServlet(name = "login_servlet", urlPatterns ="/login")
public class LoginServlet extends HttpServlet {
    private DAOUser daoUser;

    public void init() throws ServletException {
        super.init();
        daoUser = new DAOUser();
    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = daoUser.findByKey(new User(username));

        long userId = user.getId();

        HttpSession session = req.getSession();
        session.setAttribute("userId", userId);
        if (user != null && user.getPassword().equals(password)) {
            resp.sendRedirect(req.getContextPath() + "/productServlet");
        } else {
            String errorMessage = "Invalid username or password. Please try again.";
            req.setAttribute("errorMessage", errorMessage);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/Login.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
