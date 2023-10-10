package khpi.kvp.lab2_kvp.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import khpi.kvp.lab2_kvp.dao.DAOGood;
import khpi.kvp.lab2_kvp.entity.Good;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/filterProducts")
public class FilterProductServlet extends HttpServlet {
    public String category = null;
    public String sortOrder = null;
    public Integer maxPrice = null;
    public Integer minPrice = null;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Good> list = (new DAOGood()).getAllList();
        System.out.println("Size of list before sorting: " + list.size());

        BufferedReader reader = request.getReader();
        StringBuilder jsonData = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonData.append(line);
        }

        Gson gson = new Gson();
        JsonObject jsonObject;

        try {
            jsonObject = gson.fromJson(jsonData.toString(), JsonObject.class);

            if (jsonObject != null) {
                category = jsonObject.has("category") ? jsonObject.get("category").getAsString() : null;
                maxPrice = jsonObject.has("maxPrice") ? jsonObject.get("maxPrice").getAsInt() : null;
                minPrice = jsonObject.has("minPrice") ? jsonObject.get("minPrice").getAsInt() : null;
                sortOrder = jsonObject.has("sortOrder") ? jsonObject.get("sortOrder").getAsString() : null;
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Pohuy");
        }

        System.out.println(category + " " + maxPrice + " " + minPrice + " "+ sortOrder);


        if (category != null && !category.isEmpty()) {
            list = getListByCategory(list, category);
        }

        if (sortOrder != null && !sortOrder.isEmpty()) {
            list = applySortOrder(list, sortOrder);
        }

        if (maxPrice != null || minPrice != null) {
            if (maxPrice == null || maxPrice == 10000) {
                maxPrice = Integer.MAX_VALUE;
            }
            if (minPrice == null || minPrice == 0) {
                minPrice = Integer.MIN_VALUE;
            }
            list = applyPrice(list, maxPrice, minPrice);
        }

        HttpSession session = request.getSession();
        session.setAttribute("productsFilter", list);
        response.sendRedirect(request.getContextPath() + "/productServlet");
    }

    public List<Good> getListByCategory(List<Good> list, String category) {
        List<Good> filteredList = new ArrayList<>(list);

        return filteredList.stream()
                .filter(good -> good.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public List<Good> applySortOrder(List<Good> list, String sortOrder) {
        System.out.println("Applying sort order: " + sortOrder);
        System.out.println("Size: " + list.size());
        List<Good> sortedList = new ArrayList<>(list);

        if (sortOrder.equals("asc")) {
            System.out.println("Sorting in ascending order.");
            sortedList.sort(Comparator.comparingDouble(Good::getPrice));
        } else if (sortOrder.equals("desc")) {
            System.out.println("Sorting in descending order.");
            sortedList.sort(Comparator.comparingDouble(Good::getPrice).reversed());
        }

        return sortedList;
    }

    public List<Good> applyPrice(List<Good> list, int maxPrice, int minPrice) {
        List<Good> filteredList = list.stream()
                .filter(good -> good.getPrice() <= maxPrice && good.getPrice() >= minPrice)
                .collect(Collectors.toList());

        return filteredList;
    }
}
