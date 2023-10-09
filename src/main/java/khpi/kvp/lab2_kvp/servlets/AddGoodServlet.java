package khpi.kvp.lab2_kvp.servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import khpi.kvp.lab2_kvp.dao.DAOGood;
import khpi.kvp.lab2_kvp.entity.Good;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/addGood")
public class AddGoodServlet extends HttpServlet {
    private DAOGood daoGood = new DAOGood();
    public void init() throws ServletException {
        super.init();
        daoGood = new DAOGood();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        StringBuilder jsonData = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonData.append(line);
        }

        Gson gson = new Gson();
        Good product = gson.fromJson(jsonData.toString(), Good.class);
        daoGood.insert(product);
        response.sendRedirect(request.getContextPath() + "/productServlet");
    }
}
