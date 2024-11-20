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
     Deserializes a string into a `Show` object.
     *
     * The input string format is:
     * <id>,<title>,<date>,<auditId>,<roles>,<price>
     * Where:
     * - `<id>` is the unique identifier of the show.
     * - `<title>` is the title of the show.
     * - `<date>` is the show date, in the format "EEE MMM dd HH:mm:ss z yyyy".
     * - `<auditId>` is the ID of the auditorium associated with the show.
     * - `<roles>` is a pipe-separated list of actor-role pairs in the format:
     *   `<actorID>:<roleName>|<actorID>:<roleName>`.
     * - `<price>` is the price of the show.
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

        return new Show(id, title, date, auditorium, actorRoles, price);
    }

    /**
     * Parses a date string into a `Date` object.
     *
     * @param dateString The date string in the format "EEE MMM dd HH:mm:ss z yyyy".
     * @return A `Date` object parsed from the string, or `null` if parsing fails.
     */
    private Date parseDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        try {
            return sdf.parse(dateString);  // Parse the string into a Date object
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves an `Actor` object by its ID.
     *
     * @param id The unique identifier of the actor.
     * @return The `Actor` object with the specified ID.
     * @throws IllegalArgumentException If no actor is found with the given ID.
     */
    private Actor findActorById(int id) {

        return tc.viewActors().stream()
                .filter(actor -> actor.getID() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Actor not found with ID: " + id));
    }

    /**
     * Maps actor IDs to `Actor` objects and their roles.
     *
     * @param roles A map where keys are actor IDs and values are role names.
     * @return A map where keys are `Actor` objects and values are role names.
     * @throws IllegalArgumentException If any actor ID is not found.
     */
    private Map<Actor, String> mapActorsToRoles(Map<Integer, String> roles) {

        return roles.entrySet().stream()
                .map(entry -> {
                    Actor actor = findActorById(entry.getKey());
                    return Map.entry(actor, entry.getValue());
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
