package Domain;

/**
 * Represents a ticket in the system for a specific show and viewer. Each ticket
 * contains information about the show, viewer, auditorium, price, and seat.
 * Implements the HasID interface to uniquely identify each ticket.
 */
public class Ticket implements HasID{
    private static Integer idCounter;
    private final int id;
    private final String showName;
    private final String viewerName;
    private final String auditoriumName;
    private final int price;
    private final int seat;

    /**
     * Constructor for a new Ticket with specified parameters.
     * @param showName the name of the show associated with the ticket
     * @param viewerName the name of the viewer who purchased the ticket
     * @param auditoriumName the name of the auditorium where the show is held
     * @param price the price of the ticket
     * @param seat the seat number assigned to the ticket
     */
    public Ticket(String showName, String viewerName, String auditoriumName, int price, int seat) {
        this.id = ++idCounter;
        this.showName = showName;
        this.viewerName = viewerName;
        this.auditoriumName = auditoriumName;
        this.price = price;
        this.seat = seat;
    }

    /**
     * Constructor for a new Ticket with specified parameters and a stored ID.
     * @param id the stored id from the File or DB
     * @param showName the name of the show associated with the ticket
     * @param viewerName the name of the viewer who purchased the ticket
     * @param auditoriumName the name of the auditorium where the show is held
     * @param price the price of the ticket
     * @param seat the seat number assigned to the ticket
     */
    public Ticket(Integer id,String showName, String viewerName, String auditoriumName, int price, int seat) {
        this.id = id;
        this.showName = showName;
        this.viewerName = viewerName;
        this.auditoriumName = auditoriumName;
        this.price = price;
        this.seat = seat;
    }

    public String getShowName() {
        return showName;
    }
    public String getViewerName() {
        return viewerName;
    }
    public String getAuditoriumName() {
        return auditoriumName;
    }
    public int getPrice() {
        return price;
    }
    public int getSeat() {
        return seat;
    }

    /**
     * Provides a string representation of the Ticket object, detailing
     * ID, show name, viewer name, auditorium name, price, and seat.
     * @return a string describing the ticket's attributes
     */
    @Override
    public String toString() {
        return "\nTicket{" +
                "id=" + id +
                ", showName='" + showName + '\'' +
                ", viewerName='" + viewerName + '\'' +
                ", auditoriumName='" + auditoriumName + '\'' +
                ", price=" + price +
                ", seat=" + seat +
                '}';
    }

    /**
     * Returns the unique ID of the ticket.
     * @return the ticket ID
     */
    @Override
    public Integer getID() {
        return this.id;
    }

    /**
     * This is used for changing the IdCounter to be the next available id (Used for DB and File Repo)
     * @param startingNumber - the number where the variable should start (to have unique ID for every auditorium)
     * */
    public void setIdCounter(Integer startingNumber) {
        idCounter = startingNumber;
    }
}
