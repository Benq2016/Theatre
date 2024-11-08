package Domain;

import java.util.List;

public class Order implements HasID{
    private int id;
    private String date;
    private int showID;
    private List<Ticket> tickets;
    private List<Seat> seats;

    public Order(int id, String date, int showID, List<Seat> seats, List<Ticket> tickets) {
        this.id = id;
        this.date = date;
        this.showID = showID;
        this.seats = seats;
        this.tickets = tickets;
    }

    public String getDate() {
        return date;
    }

    public int getShowID() {
        return showID;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public List<Seat> getSeats() {
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
