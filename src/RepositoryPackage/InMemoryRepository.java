package RepositoryPackage;


import Domain.HasID;
import Domain.IDGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An in-memory implementation of the Repository interface for managing entities
 * of type T that extend HasID. This repository uses a Map to store objects based on their unique ID.
 * @param <T> the type of object being stored in the repository, which must implement the {@link HasID} interface.
 */
public class InMemoryRepository<T extends HasID> implements Repository<T> {

    private final Map<Integer, T> data = new HashMap<>();

    /**
     * Creates a new object in the repository, associating it with its unique ID.
     * If an object with the same ID already exists, the new object is not added.
     * @param obj the object to be created in the repository
     */
    @Override
    public void create(T obj) {
//        if (obj.getID() == null) {
//            int generatedId = IDGenerator.generateId(obj.getClass().getSimpleName());
//            obj.setID(generatedId);
//        }
        data.putIfAbsent(obj.getID(), obj);
    }

    /**
     * Deletes an object from the repository by its unique ID.
     * @param objID the unique ID of the object to be deleted
     */
    @Override
    public void delete(Integer objID) {
        data.remove(objID);
    }

    /**
     * Updates an existing object in the repository. If the object does not already exist,
     * it is added as a new object.
     * @param obj the object to be updated
     */
    @Override
    public void update(T obj) {
        data.replace(obj.getID(), obj);
    }

    /**
     * Retrieves an object from the repository by its unique ID.
     * @param id the unique ID of the object to retrieve
     * @return the object associated with the provided ID, or null if no such object exists
     */
    @Override
    public T getByID(Integer id) {
        return data.get(id);
    }

    /**
     * Returns a list of all objects stored in the repository.
     * @return a list of all objects in the repository
     */
    @Override
    public List<T> getAll() {
        return data.values().stream().toList();
    }
}
