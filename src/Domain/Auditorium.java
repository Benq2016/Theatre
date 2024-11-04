package src.Domain;

public class Auditorium implements HasID{
    private Integer id;
    private String name;
    private int capacity;
    private boolean[][] seatPlace;

    public Auditorium(Integer id,String name, int capacity, int rows, int cols) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.seatPlace = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                seatPlace[i][j] = true;
            }
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean[][] getSeatPlace() {
        return seatPlace;
    }

    @Override
    public Integer getID() {
        return this.id;
    }
}
