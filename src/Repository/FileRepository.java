package Repository;

import Domain.HasID;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A repository for managing objects of type T, where T extends HasID.
 * This repository interacts with a file system for persistent storage.
 * The operations are implemented for CRUD (Create, Read, Update, Delete) operations on the data.
 *
 * @param <T> the type of objects managed by the repository, must implement HasID
 */
public abstract class FileRepository<T extends HasID> implements Repository<T> {

    /**
     * The file path where data is stored.
     */
    private final String filePath;

    /**
     * Constructs a FileRepository with the specified file path.
     *
     * @param filePath the path to the file used for storage
     */
    public FileRepository(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Serializes the given object to a string representation.
     * This method must be implemented by the subclasses.
     *
     * @param obj the object to serialize
     * @return the string representation of the object
     */
    protected abstract String serialize(T obj);

    /**
     * Deserializes a string into an object of type T.
     * This method must be implemented by the subclasses.
     *
     * @param data the string data to deserialize
     * @return the deserialized object of type T
     */
    protected abstract T deserialize(String data);

    /**
     * Creates a new object and writes it to the file if an object with the same ID does not already exist.
     *
     * @param obj the object to create and store
     */
    @Override
    public void create(T obj) {
        List<T> allObj = getAll();
        for (T object : allObj){
            if (obj.getID().equals(object.getID())){
                return;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true))) {
            writer.write(serialize(obj));
            writer.newLine();
        }catch (IOException e){
            throw new RuntimeException("Problem during writing to the file", e);
        }

    }

    /**
     * Deletes an object from the file based on its ID.
     * This method overwrites the file, removing the object with the specified ID.
     *
     * @param objID the ID of the object to delete
     */
    @Override
    public void delete(Integer objID) {  // writes the file from scratch except for the one that we want to delete
        List<T> allObj = getAll();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,false))) {
            for (T object : allObj){
                if (!object.getID().equals(objID)){
                    writer.write(serialize(object));
                    writer.newLine();
                }
            }
        }catch (IOException e){
            throw new RuntimeException("Problem during writing to the file", e);
        }

    }

    /**
     * Updates an existing object by deleting it and then creating the new version.
     *
     * @param obj the updated object to replace the old one
     */
    @Override
    public void update(T obj) {
        delete(obj.getID());
        create(obj);
    }

    /**
     * Retrieves an object by its ID.
     *
     * @param id the ID of the object to retrieve
     * @return the object with the specified ID, or null if no such object exists
     */
    @Override
    public T getByID(Integer id) {
        return getAll().stream().
                filter(obj -> obj.getID().equals(id))
                .findFirst().
                orElse(null);
    }

    /**
     * Retrieves all objects from the file.
     *
     * @return a list of all objects stored in the file
     */
    @Override
    public List<T> getAll() {
        List<T> allObjects = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String lineFromFile;
            while ((lineFromFile = reader.readLine()) != null) {
                allObjects.add(deserialize(lineFromFile));
            }

        }catch (IOException e){
            throw new RuntimeException("Problem during reading from the file", e);
        }

        return allObjects;
    }
}
