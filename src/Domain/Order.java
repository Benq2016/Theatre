package Domain;

import java.time.LocalDateTime;
import java.util.List;

public class Order implements HasID{
    private int id;
    private LocalDateTime date;
    private int viewerID;
    private int showID;
    private List<Ticket> tickets;
    private List<Integer> seats;

    public Order(int id, LocalDateTime date, int viewerID, int showID, List<Integer> seats, List<Ticket> tickets) {
        this.id = id;
        this.date = date;
        this.viewerID = viewerID;
        this.showID = showID;
        this.seats = seats;
        this.tickets = tickets;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getViewerID() {
        return viewerID;
    }

    public int getShowID() {
        return showID;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public List<Integer> getSeats() {
        return seats;
    }

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

    @Override
    public Integer getID() {
        return id;
    }
}
