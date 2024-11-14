package Repository;

import java.util.List;

/**
 * The Repository interface defines a generic contract for repository classes that
 * manage persistent data storage and retrieval for a given entity type.
 *
 * @param <T> the type of the objects that the repository will manage
 */
public interface Repository <T>{

    /**
     * Creates a new object in the repository.
     * @param obj the object to be created
     */
    void create(T obj);

    /**
     * Deletes an object from the repository by its unique identifier.
     * @param objID the unique identifier of the object to be deleted
     */
    void delete(Integer objID);

    /**
     * Updates an existing object in the repository.
     * @param obj the object to be updated
     */
    void update(T obj);

    /**
     * Retrieves an object from the repository by its unique identifier.
     * @param id the unique identifier of the object to retrieve
     * @return the object with the specified ID, or null if no such object exists
     */
    T getByID(Integer id);

    /**
     * Retrieves all objects from the repository.
     * @return a list containing all objects in the repository
     */
    List<T> getAll();
}