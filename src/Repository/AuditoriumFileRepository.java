package Repository;

import Domain.Auditorium;

/**
 * A repository for managing Auditorium objects, which are stored in a file.
 * The CRUD operations are handled by the abstract class.
 * It serializes and deserializes Auditorium objects to and from a file.
 */
public class AuditoriumFileRepository extends FileRepository<Auditorium> {

    /**
     * Constructs an AuditoriumFileRepository with the specified file path.
     *
     * @param filePath the path to the file used for storing auditorium data
     */
    public AuditoriumFileRepository(String filePath) {
        super(filePath);
    }

    /**
     * Serializes an Auditorium object into a string representation.
     * The string format is: <id>,<name>,<rows>,<cols>.
     *
     * @param obj the Auditorium object to serialize
     * @return a string representation of the Auditorium object
     */
    @Override
    protected String serialize(Auditorium obj) {
        return obj.getID() + "," + obj.getName() + "," + obj.getRows() + "," + obj.getCols();
    }

    /**
     * Deserializes a string into an Auditorium object.
     * The expected string format is: <id>,<name>,<rows>,<cols>.
     *
     * @param data the string data to deserialize
     * @return the Auditorium object constructed from the string data
     */
    @Override
    protected Auditorium deserialize(String data) {
        String[] objectParts = data.split(",");

        int id = Integer.parseInt(objectParts[0]);
        String name = objectParts[1];
        int rows = Integer.parseInt(objectParts[2]);
        int cols = Integer.parseInt(objectParts[3]);

        return new Auditorium(id, name, rows, cols);
    }
}
