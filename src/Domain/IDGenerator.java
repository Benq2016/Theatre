package Domain;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

//////////NINCS HASZNÁLVA, NYUGI//////////
public class IDGenerator {
    private static final Map<String, Integer> lastIds = new HashMap<>();
    private static final String FILE_NAME = "RepositoryPackage/lastID.txt";

    public static void initialize() {
        loadFromFile();
    }

    private static void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String className = parts[0].trim();
                    int id = Integer.parseInt(parts[1].trim());
                    lastIds.put(className, id);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading ID file. Starting fresh.");
        }
    }

    public static synchronized int generateId(String className) {
        int lastId = lastIds.getOrDefault(className, 0);
        lastId++;
        lastIds.put(className, lastId);

        saveToFile();
        return lastId;
    }

    private static void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<String, Integer> entry : lastIds.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to ID file.");
        }
    }
}

