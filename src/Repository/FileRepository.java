package Repository;

import Domain.HasID;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class FileRepository<T extends HasID> implements Repository<T> {

//    private final Map<Integer, T> data = new HashMap<>();
    private final String filePath;

    public FileRepository(String filePath) {
        this.filePath = filePath;
    }

    protected abstract String serialize(T obj);

    protected abstract T deserialize(String data);

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

    @Override
    public void update(T obj) {
        delete(obj.getID());
        create(obj);
    }

    @Override
    public T getByID(Integer id) {
        return getAll().stream().
                filter(obj -> obj.getID().equals(id))
                .findFirst().
                orElse(null);
    }

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
