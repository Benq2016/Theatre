package Repository;

import Controller.TheatreController;
import Domain.Actor;
import Domain.Auditorium;
import Domain.Show;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class ShowFileRepository extends FileRepository<Show> {
    String filePath;
    TheatreController tc;

    public ShowFileRepository(String filePath){
        super(filePath);
    }

    public void setTheatreController(TheatreController tc){
        this.tc = tc;
    }

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

    protected Show deserialize(String data) {
        String[] objectParts = data.split(",");

        int id = Integer.parseInt(objectParts[0]);
        String title = objectParts[1];

        Date date = parseDate(objectParts[2]);

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
        return new Show(id, title,date, auditorium, actorRoles, price);
    }

    // Helper method to parse Date from a string
    private Date parseDate(String dateString) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

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
