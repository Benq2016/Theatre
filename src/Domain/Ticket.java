package Domain;

public class Ticket implements HasID{
    private int id;
    private String showName;
    private String viewerName;
    private String auditoriumName;
    private int price;
    private Seat seat;

    public Ticket(int id, String showName, String viewerName, String auditoriumName, int price, Seat seat) {
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

    public Seat getSeat() {
        return seat;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", showName='" + showName + '\'' +
                ", viewerName='" + viewerName + '\'' +
                ", auditoriumName='" + auditoriumName + '\'' +
                ", price=" + price +
                ", seat=" + seat +
                '}';
    }

    @Override
    public Integer getID() {
        return this.id;
    }
}
