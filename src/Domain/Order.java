package Domain;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents an order made by a viewer for a show, containing details such as
 * the date of the order, the viewer who made it, the show associated with the order,
 * the tickets purchased, and the seats reserved.
 * This class implements the HasID interface, allowing each order to have a unique identifier.
 */
public class Order implements HasID{
    private final int id;
    private LocalDateTime date;
    private int viewerID;
    private int showID;
    private List<Ticket> tickets;
    private List<Integer> seats;

    /**
     * Constructs a new Order with the specified details.
     * @param id the unique identifier for the order
     * @param date the date and time when the order was placed
     * @param viewerID the ID of the viewer who placed the order
     * @param showID the ID of the show associated with the order
     * @param seats the list of seat numbers reserved in the order
     * @param tickets the list of tickets purchased in the order
     */
    public Order(int id, LocalDateTime date, int viewerID, int showID, List<Integer> seats, List<Ticket> tickets) {
        this.id = id;
        this.date = date;
        this.viewerID = viewerID;
        this.showID = showID;
        this.seats = seats;
        this.tickets = tickets;
    }

    /**
     * Returns the date and time when the order was placed.
     * @return the date and time of the order
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Returns the ID of the viewer who placed the order.
     * @return the viewer's ID
     */
    public int getViewerID() {
        return viewerID;
    }

//    public int getShowID() {
//        return showID;
//    }
//
//    public List<Ticket> getTickets() {
//        return tickets;
//    }
//
//    public List<Integer> getSeats() {
//        return seats;
//    }

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
}
