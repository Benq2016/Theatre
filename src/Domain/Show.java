package Domain;
import java.util.Map;

/**
 * Represents a show in the system, including details about its title, auditorium,
 * casting roles, and date. Implements the HasID interface to uniquely identify each show.
 */
public class Show implements HasID{
    private final Integer id;
    private final String title;
    private final Auditorium audit;
    private final Map<Actor, String> roles;
    private final String date;
    private final int price;

    /**
     * Constructor for a new Show with specified parameters.
     * @param id the unique identifier of the show
     * @param title the title of the show
     * @param audit the auditorium where the show will be held
     * @param roles a map of actors to their roles in the show
     * @param date the date of the show
     */
    public Show(Integer id, String title, String date, Auditorium audit, Map<Actor, String> roles, int price) {
        this.id = id;
        this.title = title;
        this.audit = audit;
        this.roles = roles;
        this.date = date;
        this.price = price;
    }

//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }

    /**
     * Returns the roles assigned to actors in the show.
     * @return a map of actors and their respective roles
     */
    public Map<Actor, String> getRoles() {
        return roles;
    }

//    public void setRoles(Map<Actor, String> roles) {
//        this.roles = roles;
//    }

    /**
     * Returns the auditorium where the show is held.
     * @return the auditorium for the show
     */
    public Auditorium getAudit() {
        return audit;
    }

//    public void setAudit(Auditorium audit) {
//        this.audit = audit;
//    }

    /**
     * Returns the title of the show.
     * @return the title of the show
     */
    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

//    public void setTitle(String title) {
//        this.title = title;
//    }

    /**
     * Returns the unique ID of the show.
     * @return the ID of the show
     */
    @Override
    public Integer getID() {
        return this.id;
    }

    /**
     * Provides a string representation of the Show object, detailing its ID, title,
     * date, casting roles, and auditorium name.
     * @return a string describing the show's attributes
     */
    @Override
    public String toString() {
        return "Show{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", casting=" + roles +
                ", auditorium=" + audit.getName() +
                "}";
    }
}