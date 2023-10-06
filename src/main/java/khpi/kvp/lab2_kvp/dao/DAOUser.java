package khpi.kvp.lab2_kvp.dao;

import khpi.kvp.lab2_kvp.dbutil.DBConnector;
import khpi.kvp.lab2_kvp.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class DAOUser implements Idao<User>{
    @Override
    public List<User> getAllList() {
        List<User> list;
        try (Session session = DBConnector.getSessionFactory().openSession()) {
            list = session.createQuery("from User", User.class).getResultList();
        }
        return list;
    }

    @Override
    public User findById(Long id) {
        User userInDB;
        try (Session session = DBConnector.getSessionFactory().openSession()) {
            userInDB = session.get(User.class, id);
        }
        return userInDB;
    }

    public User findByKey(User template) {
        List<User> list;
        User userInDB = null;
        try (Session session = DBConnector.getSessionFactory().openSession()) {
            list = session.createQuery("from User", User.class).getResultList();
            Optional<User> listFiltered = list.stream().filter(user -> user.getLogin().equals(template.getLogin())).findFirst();
            if (listFiltered.isPresent()) {
                userInDB = listFiltered.get();
            }
        }
        return userInDB;
    }

    @Override
    public boolean insert(User entityToSave) {
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
    public boolean update(Long id, User entityToUpdate) {
        User userForEdit = findById(id);
        boolean updtOk;
        if (userForEdit == null) {
            System.err.println("Object was not found!");
            updtOk = false;
        } else {
            Transaction transaction = null;
            try (Session session = DBConnector.getSessionFactory().openSession()) {
                entityToUpdate.setId(userForEdit.getId());
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
        User userForDel = findById(id);
        if (userForDel == null) {
            System.err.println("Object was not found!");
            dltOk = false;
        } else {
            Transaction transaction = null;
            try (Session session = DBConnector.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                session.delete(userForDel);
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
