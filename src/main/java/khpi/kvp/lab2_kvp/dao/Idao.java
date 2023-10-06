package khpi.kvp.lab2_kvp.dao;

import java.util.List;

public interface Idao<E> {
    List<E> getAllList();
    E findById(Long id);
    boolean insert(E entityToSave);
    boolean update(Long id,E entityToUpdate);
    boolean delete(Long id);
}
