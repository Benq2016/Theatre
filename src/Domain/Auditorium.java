package Domain;

import java.util.Arrays;

public class Auditorium implements HasID{
    private static Integer idCounter = 0; /**Static variable for automatic ID incrementation*/
    private Integer id; /**Unique identifier*/
    private String name; /**Name of */
    private int capacity; /**Capacity of the auditorium*/
    private int rows; /**How many rows*/
    public int cols; /**How many columns*/
    private boolean[][] seatPlace; /**A boolean matrix for the availability of the seat*/

    public Auditorium(String name, int rows, int cols) {
        this.id = ++idCounter;
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
    /**Constructor with stored ID from DB or File*/
    public Auditorium(int id,String name, int rows, int cols) {
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

//    /**
//     * Sets the name of the auditorium.
//     *
//     * @param name the new name of the auditorium
//     */
//    public void setName(String name) {
//        this.name = name;
//    }

    /**
     * Gets the seating capacity of the auditorium.
     *
     * @return the seating capacity of the auditorium
     */
    public int getCapacity() {
        return capacity;
    }
//
//    public void setCapacity(int rows, int cols) {
//        this.capacity = rows * cols;
//        this.rows = rows;
//        this.cols = cols;
//    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public boolean[][] getSeatPlace() {
        return seatPlace;
    }

    /**
     * Returns a string representation of the Auditorium object.
     *
     * @return a string containing the id, name, capacity, rows, and columns of the auditorium
     */
//    @Override
//    public String toString() {
//        return "Auditorium{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", capacity=" + capacity +
//                ", rows=" + rows +
//                ", cols=" + cols;
//    }

    /**
     * Provides a detailed view of the auditorium layout, showing seat availability.
     * Displays each seat as "00x" for available seats or "---" for unavailable seats.
     *
     * @return a string containing the auditorium layout, with seat numbers and availability
     */
    public String toString() {
        String result = "Auditorium{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", rows=" + rows +
                ", cols=" + cols +
                ", seats= \n";

        int cnt = 1;
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                if (cnt < 10)
                    result += (seatPlace[i-1][j-1]) ? "|00" + cnt : "|---";
                else if (cnt < 100)
                    result += (seatPlace[i-1][j-1]) ? "|0" + cnt : "|---";
                else
                    result += (seatPlace[i-1][j-1]) ? "|" + cnt : "|---";
                cnt++;

            }
            result += "\n";
        }

        result += "}";
        return result;
    }

    /**
     * Gets the unique identifier (ID) of the auditorium.
     *
     * @return the unique identifier of the auditorium
     */
    @Override
    public Integer getID() {
        return this.id;
    }

    /**
     * This is used for changing the IdCounter to be the next available id (Used for DB and File Repo)
     * @param startingNumber - the number where the variable should start (to have unique ID for every auditorium)
     * */
    public static void setIdCounter(Integer startingNumber) {
        idCounter = startingNumber;
    }
}
