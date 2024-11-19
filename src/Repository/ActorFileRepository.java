package Repository;

import Domain.Actor;
import Domain.EMail;

/**
 * A repository for managing Actor objects, which are stored in a file.
 * The CRUD operations are handled by the abstract class
 * It serializes and deserializes the Actor objects to and from a file.
 */
public class ActorFileRepository extends FileRepository<Actor> {

    /**
     * Constructs an ActorFileRepository with the specified file path.
     *
     * @param filePath the path to the file used for storing actor data
     */
    public ActorFileRepository(String filePath) {
        super(filePath);
    }

    /**
     * Serializes an Actor object into a string representation.
     * The string format is: <id>,<name>,<age>,<emailAddress>,<emailPassword>,<salary>.
     *
     * @param obj the Actor object to serialize
     * @return a string representation of the Actor object
     */
    @Override
    protected String serialize(Actor obj) {
        return obj.getID() + "," + obj.getName() + "," + obj.getAge() + "," +
                obj.getEmail().getEmailAddress()+ "," + obj.getEmail().getPassword() + "," + obj.getSalary();
    }

    /**
     * Deserializes a string into an Actor object.
     * The expected string format is: <id>,<name>,<age>,<emailAddress>,<emailPassword>,<salary>.
     *
     * @param data the string data to deserialize
     * @return the Actor object constructed from the string data
     */
    @Override
    protected Actor deserialize(String data) {
        String[] objectParts = data.split(",");


        int id = Integer.parseInt(objectParts[0]);
        String name = objectParts[1];
        int age = Integer.parseInt(objectParts[2]);
        String emailAddress = objectParts[3];
        String emailPassword = objectParts[4];
        int salary = Integer.parseInt(objectParts[5]);

        EMail email = new EMail(emailAddress, emailPassword);
        return new Actor(id, name, age, email,salary);
    }
}
