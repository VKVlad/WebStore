package khpi.kvp.lab2_kvp.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import khpi.kvp.lab2_kvp.dao.DAOUser;
import khpi.kvp.lab2_kvp.entity.User;

import java.io.IOException;

@WebServlet(name = "registration_servlet", urlPatterns ="/registration")
public class RegistrationServlet extends HttpServlet {
    private DAOUser daoUser;

    public void init() throws ServletException {
        super.init();
        daoUser = new DAOUser();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("usernamereg");
        String password = req.getParameter("passwordreg");

        User user = daoUser.findByKey(new User(username));

        if (user != null) {
            String errorMessage = "User is already exists.";
            req.setAttribute("errorMessageReg", errorMessage);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/Login.jsp");
            dispatcher.forward(req, resp);
        } else {
            User userReg = new User(username, password);
            daoUser.insert(userReg);
            resp.sendRedirect("Login.jsp");
        }
    }
}
