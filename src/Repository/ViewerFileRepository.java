package Repository;

import Domain.Viewer;
import Domain.EMail;

/**
 * A repository for managing Viewer objects, which are stored in a file.
 * The CRUD operations are handled by the abstract class.
 * It serializes and deserializes Viewer objects to and from a file.
 */
public class ViewerFileRepository extends FileRepository<Viewer> {

    /**
     * Constructs a ViewerFileRepository with the specified file path.
     *
     * @param filePath the path to the file used for storing viewer data
     */
    public ViewerFileRepository(String filePath) {
        super(filePath);
    }

    /**
     * Serializes a Viewer object into a string representation.
     * The string format is: <id>,<name>,<age>,<emailAddress>,<emailPassword>.
     *
     * @param obj the Viewer object to serialize
     * @return a string representation of the Viewer object
     */
    @Override
    protected String serialize(Viewer obj) {
        return obj.getID() + "," + obj.getName() + "," + obj.getAge() + "," +
                obj.getEmail().getEmailAddress() + "," + obj.getEmail().getPassword();
    }

    /**
     * Deserializes a string into a Viewer object.
     * The expected string format is: <id>,<name>,<age>,<emailAddress>,<emailPassword>.
     *
     * @param data the string data to deserialize
     * @return the Viewer object constructed from the string data
     */
    @Override
    protected Viewer deserialize(String data) {
        String[] objectParts = data.split(",");

        int id = Integer.parseInt(objectParts[0]);
        String name = objectParts[1];
        int age = Integer.parseInt(objectParts[2]);
        String emailAddress = objectParts[3];
        String emailPassword = objectParts[4];

        EMail email = new EMail(emailAddress, emailPassword);
        return new Viewer(id, name, age, email);
    }
}
