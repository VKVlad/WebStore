package khpi.kvp.lab2_kvp.dao;

import khpi.kvp.lab2_kvp.dbutil.DBConnector;
import khpi.kvp.lab2_kvp.entity.Good;
import khpi.kvp.lab2_kvp.entity.Order;
import khpi.kvp.lab2_kvp.entity.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class DAOOrder implements Idao<Order>{
    @Override
    public List<Order> getAllList() {
        List<Order> list;
        try (Session session = DBConnector.getSessionFactory().openSession()) {
            list = session.createQuery("from Order", Order.class).getResultList();
        }
        return list;
    }

    @Override
    public Order findById(Long id) {
        Order orderInDB;
        try (Session session = DBConnector.getSessionFactory().openSession()) {
            orderInDB = session.get(Order.class, id);
        }
        return orderInDB;
    }
    public Order findByKey(Order template, User user, Good good) {
        List<Order> list;
        Order orderInDB = null;
        try (Session session = DBConnector.getSessionFactory().openSession()) {
            list = session.createQuery("FROM Order WHERE receiver.id = :userId AND good.id = :goodId", Order.class)
                    .setParameter("userId", user.getId()).setParameter("goodId", good.getId())
                    .getResultList();
            Optional<Order> listFiltered = list.stream().filter(order -> order.getGood().getNazva().equals(template.getGood().getNazva())).findFirst();
            if (listFiltered.isPresent()) {
                orderInDB = listFiltered.get();
            }
        }
        return orderInDB;
    }

    public List<Order> getListByUser(User template) {
        List<Order> list;
        try (Session session = DBConnector.getSessionFactory().openSession()) {
            list = session.createQuery("FROM Order WHERE receiver.id = :userId", Order.class)
                    .setParameter("userId", template.getId())
                    .getResultList();

            for (Order order : list) {
                Hibernate.initialize(order.getGood());
            }
        }
        return list;
    }

    public List<Order> getListByGood(Good template) {
        List<Order> list;
        try (Session session = DBConnector.getSessionFactory().openSession()) {
            list = session.createQuery("FROM Order WHERE good.id = :goodId", Order.class)
                    .setParameter("goodId", template.getId())
                    .getResultList();

            // Initialize the Good entities within the Hibernate session
            for (Order order : list) {
                Hibernate.initialize(order.getGood());
            }
        }
        return list;
    }

    @Override
    public boolean insert(Order entityToSave) {
        boolean insrtOk;
        Transaction transaction = null;
        try (Session session = DBConnector.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(entityToSave);
            transaction.commit();
            insrtOk = true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error: insert data " + getClass());
            insrtOk = false;
        }
        return insrtOk;
    }

    @Override
    public boolean update(Long id, Order entityToUpdate) {
        Order orderForEdit = findById(id);
        boolean updtOk;
        if (orderForEdit == null) {
            System.err.println("Object was not found!");
            updtOk = false;
        } else {
            Transaction transaction = null;
            try (Session session = DBConnector.getSessionFactory().openSession()) {
                entityToUpdate.setId(orderForEdit.getId());
                transaction = session.beginTransaction();
                session.update(entityToUpdate);
                transaction.commit();
                updtOk = true;
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println("Error: update data: " + getClass());
                updtOk = false;
            }
        }
        return updtOk;
    }

    @Override
    public boolean delete(Long id) {
        boolean dltOk;
        Order orderForDel = findById(id);
        if (orderForDel == null) {
            System.err.println("Object was not found!");
            dltOk = false;
        } else {
            Transaction transaction = null;
            try (Session session = DBConnector.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                session.delete(orderForDel);
                transaction.commit();
                dltOk = true;
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println("Error: delete data " + getClass());
                dltOk = false;
            }
        }
        return dltOk;
    }
}
