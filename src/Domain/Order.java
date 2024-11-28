package Domain;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents an order made by a viewer for a show, containing details such as
 * the date of the order, the viewer who made it, the show associated with the order,
 * the tickets purchased, and the seats reserved.
 * This class implements the HasID interface, allowing each order to have a unique identifier.
 */
public class Order implements HasID{
    private static Integer idCounter = 0;
    private final int id;
    private LocalDate date;
    private int viewerID;
    private int showID;
    private List<Ticket> tickets;
    private List<Integer> seats;
    private int totalPrice;

    /**
     * Constructs a new Order with the specified details.
     * @param date the date and time when the order was placed
     * @param viewerID the ID of the viewer who placed the order
     * @param showID the ID of the show associated with the order
     * @param seats the list of seat numbers reserved in the order
     * @param tickets the list of tickets purchased in the order
     */
    public Order(LocalDate date, int viewerID, int showID, List<Integer> seats, List<Ticket> tickets, int totalPrice) {
        this.id = ++idCounter;
        this.date = date;
        this.viewerID = viewerID;
        this.showID = showID;
        this.seats = seats;
        this.tickets = tickets;
        this.totalPrice = totalPrice;
    }

    /**
     * Constructs a new Order with the specified details and a stored ID.
     * @param id the stored ID from a DB or File
     * @param date the date and time when the order was placed
     * @param viewerID the ID of the viewer who placed the order
     * @param showID the ID of the show associated with the order
     * @param seats the list of seat numbers reserved in the order
     * @param tickets the list of tickets purchased in the order
     */
    public Order(Integer id,LocalDate date, int viewerID, int showID, List<Integer> seats, List<Ticket> tickets, int totalPrice) {
        this.id = id;
        this.date = date;
        this.viewerID = viewerID;
        this.showID = showID;
        this.seats = seats;
        this.tickets = tickets;
        this.totalPrice = totalPrice;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    /**
     * Returns the date and time when the order was placed.
     * @return the date and time of the order
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the ID of the viewer who placed the order.
     * @return the viewer's ID
     */
    public Integer getViewerID() {
        return viewerID;
    }

    public int getShowID() {
        return showID;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public List<Integer> getSeats() {
        return seats;
    }

    /**
     * Returns a string representation of the order, including the ID, date,
     * associated show ID, list of tickets, and reserved seats.
     * @return a string representation of the order
     */
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", showID=" + showID +
                ", tickets=" + tickets +
                ", seats=" + seats +
                '}';
    }

    /**
     * Returns the unique identifier of the order.
     * @return the ID of the order
     */
    @Override
    public Integer getID() {
        return id;
    }

    /**
     * This is used for changing the IdCounter to be the next available id (Used for DB and File Repo)
     * @param startingNumber - the number where the variable should start (to have unique ID for every auditorium)
     * */
    public void setIdCounter(Integer startingNumber) {
        idCounter = startingNumber;
    }

}
