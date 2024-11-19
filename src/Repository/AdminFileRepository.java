package Repository;

import Domain.Admin;
import Domain.EMail;

/**
 * A repository for managing Admin objects, which are stored in a file.
 * The CRUD operations are handled by the abstract class
 * It serializes and deserializes Admin objects to and from a file.
 */
public class AdminFileRepository extends FileRepository<Admin> {

    /**
     * Constructs an AdminFileRepository with the specified file path.
     *
     * @param filePath the path to the file used for storing admin data
     */
    public AdminFileRepository(String filePath) {
        super(filePath);
    }

    /**
     * Serializes an Admin object into a string representation.
     * The string format is: <id>,<name>,<age>,<emailAddress>,<emailPassword>.
     *
     * @param obj the Admin object to serialize
     * @return a string representation of the Admin object
     */
    @Override
    protected String serialize(Admin obj) {
        return obj.getID() + "," + obj.getName() + "," + obj.getAge() + "," +
                obj.getEmail().getEmailAddress() + "," + obj.getEmail().getPassword();
    }

    /**
     * Deserializes a string into an Admin object.
     * The expected string format is: <id>,<name>,<age>,<emailAddress>,<emailPassword>.
     *
     * @param data the string data to deserialize
     * @return the Admin object constructed from the string data
     */
    @Override
    protected Admin deserialize(String data) {
        String[] objectParts = data.split(",");

        int id = Integer.parseInt(objectParts[0]);
        String name = objectParts[1];
        int age = Integer.parseInt(objectParts[2]);
        String emailAddress = objectParts[3];
        String emailPassword = objectParts[4];

        EMail email = new EMail(emailAddress, emailPassword);
        return new Admin(id, name, age, email);
    }
}
