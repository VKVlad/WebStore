import khpi.kvp.lab2_kvp.dao.DAOGood;
import khpi.kvp.lab2_kvp.dao.DAOOrder;
import khpi.kvp.lab2_kvp.dao.DAOUser;
import khpi.kvp.lab2_kvp.entity.Good;
import khpi.kvp.lab2_kvp.entity.Order;
import khpi.kvp.lab2_kvp.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class Tests {
    private DAOUser daoUser = new DAOUser();
    private DAOOrder daoOrder = new DAOOrder();
    private DAOGood daoGood = new DAOGood();
    @Test
    void testInsert() {
        User user = daoUser.findByKey(new User("user"));

        Good good = daoGood.findById(59L);

        Order order = new Order(user, "manager1", good, 1, good.getPrice(), LocalDate.now());

        daoOrder.insert(order);
    }

    @Test
    void testFindOrder() {
        Order order = daoOrder.findById(81L);
        System.out.println(order.getCount());
    }
}
