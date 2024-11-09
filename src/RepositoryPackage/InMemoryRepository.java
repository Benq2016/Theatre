package RepositoryPackage;


import Domain.HasID;
import Domain.IDGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryRepository<T extends HasID> implements Repository<T> {

    private final Map<Integer, T> data = new HashMap<>();


    @Override
    public void create(T obj) {
//        if (obj.getID() == null) {
//            int generatedId = IDGenerator.generateId(obj.getClass().getSimpleName());
//            obj.setID(generatedId);
//        }
        data.putIfAbsent(obj.getID(), obj);
    }

    @Override
    public void delete(Integer objID) {
        data.remove(objID);
    }

    @Override
    public void update(T obj) {
        data.replace(obj.getID(), obj);
    }

    @Override
    public T getByID(Integer id) {
        return data.get(id);
    }

    @Override
    public List<T> getAll() {
        return data.values().stream().toList();
    }
}
