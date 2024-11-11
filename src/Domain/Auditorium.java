package Domain;

import java.util.Arrays;

public class Auditorium implements HasID{
    private Integer id;
    private String name;
    private int capacity;
    private int rows;
    public int cols;
    private boolean[][] seatPlace;

    public Auditorium(Integer id,String name, int rows, int cols) {
        this.id = id;
        this.name = name;
        this.capacity = rows * cols;
        this.rows = rows;
        this.cols = cols;
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

    public void setCapacity(int rows, int cols) {
        this.capacity = rows * cols;
        this.rows = rows;
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public boolean[][] getSeatPlace() {
        return seatPlace;
    }

    @Override
    public String toString() {
        String result = "Auditorium{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", rows=" + rows +
                ", cols=" + cols ;
//                ", seats= \n";
//        int cnt = 1;
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                if (cnt < 10)
//                    result += (seatPlace[i][j]) ? "|00" + cnt : "|---";
//                else if (cnt < 100)
//                    result += (seatPlace[i][j]) ? "|0" + cnt : "|---";
//                else
//                    result += (seatPlace[i][j]) ? "|" + cnt : "|---";
//                cnt++;
//
//            }
//            result += "\n";
//        }

//        result += "}";
        return result;
    }

    public String viewAuditoriumWithoutLayout(){
        String result = "Auditorium{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", rows=" + rows +
                ", cols=" + cols +
                ", seats= \n";
        return result;
    }

    @Override
    public Integer getID() {
        return this.id;
    }
}
