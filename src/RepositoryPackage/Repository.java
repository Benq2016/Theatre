package src.RepositoryPackage;

import java.util.List;

public interface Repository <T>{
    void create(T obj);
    void delete(T obj);
    void update(T obj);
    T getByID(Integer id);
    List<T> getAll();
}
