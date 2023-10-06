package khpi.kvp.lab2_kvp.dao;

import khpi.kvp.lab2_kvp.dbutil.DBConnector;
import khpi.kvp.lab2_kvp.entity.Good;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DAOGood implements Idao<Good>{
    @Override
    public List<Good> getAllList() {
        List<Good> list;
        try (Session session = DBConnector.getSessionFactory().openSession()) {
            Query<Good> query = session.createQuery("FROM Good", Good.class);
            list = query.getResultList();
        }
        return list;
    }

    @Override
    public Good findById(Long id) {
        Good goodInDB;
        try (Session session = DBConnector.getSessionFactory().openSession()) {
            goodInDB = session.get(Good.class, id);
        }
        return goodInDB;
    }

    public Good findByKey(Good template) {
        List<Good> list;
        Good goodInDB = null;
        try (Session session = DBConnector.getSessionFactory().openSession()) {
            list = session.createQuery("from Good", Good.class).getResultList();
            Optional<Good> listFiltered = list.stream().filter(good -> good.getNazva().equals(template.getNazva())).findFirst();
            if (listFiltered.isPresent()) {
                goodInDB = listFiltered.get();
            }
        }
        return goodInDB;
    }

    @Override
    public boolean insert(Good entityToSave) {
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
    public boolean update(Long id, Good entityToUpdate) {
        Good goodForEdit = findById(id);
        boolean updtOk;
        if (goodForEdit == null) {
            System.err.println("Object was not found!");
            updtOk = false;
        } else {
            Transaction transaction = null;
            try (Session session = DBConnector.getSessionFactory().openSession()) {
                entityToUpdate.setId(goodForEdit.getId());
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
        Good goodForDel = findById(id);
        if (goodForDel == null) {
            System.err.println("Object was not found!");
            dltOk = false;
        } else {
            Transaction transaction = null;
            try (Session session = DBConnector.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                session.delete(goodForDel);
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
