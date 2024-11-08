package Domain;

public class Seat implements HasID{
    private int id;
    private int row;
    private int seat;

    public Seat(int id, int row, int seat) {
        this.id = id;
        this.row = row;
        this.seat = seat;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    @Override
    public Integer getID() {
        return this.id;
    }
}
