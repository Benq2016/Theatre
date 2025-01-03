package Domain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    public Auditorium(String name, int rows, int cols, Set<Integer> occupiedSeats) {
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

        for (Integer seatNumber : occupiedSeats) {
            int row = (seatNumber - 1) / cols;
            int col = (seatNumber - 1) % cols;
            if (row >= 0 && row < rows && col >= 0 && col < cols) {
                seatPlace[row][col] = false;
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
     * Provides a detailed view of the auditorium layout, showing seat availability.
     * Displays each seat as "00x" for available seats or "---" for unavailable seats.
     *
     * @return a string containing the auditorium layout, with seat numbers and availability
     */
    public String toString() {
        String result =
                "Auditorium with id=" + id +
                ", name='" + name + '\'' +
                ", seat layout= \n";

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

    /** Override equals() to compare Auditorium objects based on their fields */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Check if the objects are the same instance
        if (obj == null || getClass() != obj.getClass()) return false; // Ensure the same type

        Auditorium that = (Auditorium) obj;

        return id.equals(that.id) &&
                capacity == that.capacity &&
                rows == that.rows &&
                cols == that.cols &&
                Objects.equals(name, that.name) &&
                Arrays.deepEquals(seatPlace, that.seatPlace); // Compare the 2D seatPlace array
    }
}
