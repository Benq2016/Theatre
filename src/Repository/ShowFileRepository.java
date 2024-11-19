package Repository;

import Controller.TheatreController;
import Domain.Actor;
import Domain.Auditorium;
import Domain.Show;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The ShowFileRepository class is responsible for managing the persistence of Show objects to a file.
 * It extends the FileRepository class and provides specific methods to serialize and
 * deserialize Show objects.
 * This class also interacts with the TheatreController to retrieve related entities such as
 * Actor and Auditorium.
 */
public class ShowFileRepository extends FileRepository<Show> {
//    String filePath;
    TheatreController tc; // it connects this Repository with the TheatreController,
                            // for getting the auditorium of the show

    /**
     * Constructs a new ShowFileRepository with the specified file path.
     *
     * @param filePath the path to the file where Show objects are stored.
     */
    public ShowFileRepository(String filePath){
        super(filePath);
    }

    /**
     * Sets the TheatreController to be used for retrieving related entities such as Actor
     * and Auditorium.
     * It is added by a setter because of circular dependency in the MAIN of the App
     *
     * @param tc the TheatreController instance.
     */
    public void setTheatreController(TheatreController tc){
        this.tc = tc;
    }

    /**
     * Serializes a Show object into a string representation suitable for storing in a file.
     * The serialized string format is: <id>,<title>,<date>,<auditId>,<rolesSerialized>,<price>
     * Where:
     * - <id>: the ID of the show (integer)
     * - <title>: the title of the show (string)
     * - <date>: the date of the show (string, formatted as "EEE MMM dd HH:mm:ss z yyyy")
     * - <auditId>: the ID of the associated auditorium (integer)
     * - <rolesSerialized>: a serialized string of roles in the format "actorID:role|actorID:role|..." (string)
     * - <price>: the price of the show (integer)
     *
     * @param show the Show object to be serialized.
     * @return a string representation of the Show object.
     */
    protected String serialize(Show show) {
        String rolesSerialized = show.getRoles().entrySet().stream()
                .map(entry -> entry.getKey().getID() + ":" + entry.getValue())
                .collect(Collectors.joining("|"));

        return show.getID() + "," +
                show.getTitle() + "," +
                show.getDate() + "," +
                show.getAudit().getID() + "," +
                rolesSerialized + "," +
                show.getPrice();
    }


    /**
     * Deserializes a string representing a Show object into a Show instance.
     *
     * The format of the input string is expected to be: <id>,<title>,<date>,<auditId>,<roles>,<price>.
     * Where <roles> is a pipe-separated list of actor ID and role pairs in the format:
     * <actorID>:<roleName>|<actorID>:<roleName>...
     *
     * @param data The serialized string representing a Show.
     * @return A Show object constructed from the serialized data.
     * @throws IllegalArgumentException if any of the actor IDs referenced in the roles are not found.
     */
    protected Show deserialize(String data) {
        String[] objectParts = data.split(",");

        int id = Integer.parseInt(objectParts[0]);
        String title = objectParts[1];

        Date date = parseDate(objectParts[2]); // custom method for parsing Data objects

        int auditId = Integer.parseInt(objectParts[3]);
        int price = Integer.parseInt(objectParts[5]);

        Map<Integer, String> roles = Arrays.stream(objectParts[4].split("\\|"))
                .map(roleStr -> roleStr.split(":"))
                .collect(Collectors.toMap(
                        roleParts -> Integer.parseInt(roleParts[0]),
                        roleParts -> roleParts[1]
                ));

        Auditorium auditorium = tc.viewAuditorium(auditId);

        Map<Actor, String> actorRoles = mapActorsToRoles(roles);

        // Return the Show object constructed from the deserialized data
        return new Show(id, title, date, auditorium, actorRoles, price);
    }

    private Date parseDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        try {
            return sdf.parse(dateString);  // Parse the string into a Date object
        } catch (ParseException e) {
            e.printStackTrace();
            return null;  // Handle the error appropriately (e.g., return null or throw an exception)
        }
    }

    // Method to retrieve an Actor by ID
    private Actor findActorById(int id) {

        return tc.viewActors().stream()
                .filter(actor -> actor.getID() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Actor not found with ID: " + id));
    }

    // Method to map Actor IDs to Actors and their respective roles
    private Map<Actor, String> mapActorsToRoles(Map<Integer, String> roles) {
        // Convert the roles map (ActorID -> RoleName) to a map of Actor -> RoleName
        return roles.entrySet().stream()
                .map(entry -> {
                    // Get the actor by ID
                    Actor actor = findActorById(entry.getKey());
                    // Map the actor to their role name
                    return Map.entry(actor, entry.getValue());
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
