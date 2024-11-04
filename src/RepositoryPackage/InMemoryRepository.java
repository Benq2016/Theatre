package src.RepositoryPackage;


import src.Domain.HasID;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryRepository<T extends HasID> implements Repository<T> {

    private final Map<Integer, T> data = new HashMap<>();


    @Override
    public void create(T obj) {
        data.putIfAbsent(obj.getID(),obj);
    }

    @Override
    public void delete(T obj) {
        data.remove(obj.getID());
    }

    @Override
    public void update(T obj) {
        data.replace(obj.getID(),obj);
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
