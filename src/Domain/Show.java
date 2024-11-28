package Domain;
import java.util.Date;
import java.util.Map;
import java.text.SimpleDateFormat;

/**
 * Represents a show in the system, including details about its title, auditorium,
 * casting roles, and date. Implements the HasID interface to uniquely identify each show.
 */
public class Show implements HasID{
    private static Integer idCounter = 0;
    private final Integer id;
    private final String title;
    private final Auditorium audit;
    private final Map<Actor, String> roles;
    private final Date date;
    private final int price;

    /**
     * Constructor for a new Show with specified parameters.
     * @param title the title of the show
     * @param audit the auditorium where the show will be held
     * @param roles a map of actors to their roles in the show
     * @param date the date of the show
     */
    public Show(String title, Date date, Auditorium audit, Map<Actor, String> roles, int price) {
        this.id = ++idCounter;
        this.title = title;
        this.audit = audit;
        this.roles = roles;
        this.date = date;
        this.price = price;
    }

    /**
     * Constructor for a new Show with specified parameters and a stored ID.
     * @param title the title of the show
     * @param audit the auditorium where the show will be held
     * @param roles a map of actors to their roles in the show
     * @param date the date of the show
     */
    public Show(Integer id, String title, Date date, Auditorium audit, Map<Actor, String> roles, int price) {
        this.id = id;
        this.title = title;
        this.audit = audit;
        this.roles = roles;
        this.date = date;
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public Map<Actor, String> getRoles() {
        return roles;
    }

//    public void setRoles(Map<Actor, String> roles) {
//        this.roles = roles;
//    }

    public Auditorium getAudit() {
        return audit;
    }

//    public void setAudit(Auditorium audit) {
//        this.audit = audit;
//    }

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(date);

        return "Show{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + dateString +
                ", casting=" + roles +
                ", auditorium=" + audit.getName() +
                "}";
    }

    /**
     * This is used for changing the IdCounter to be the next available id (Used for DB and File Repo)
     * @param startingNumber - the number where the variable should start (to have unique ID for every auditorium)
     * */
    public void setIdCounter(Integer startingNumber) {
        idCounter = startingNumber;
    }
}