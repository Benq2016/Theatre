package Domain;

import java.util.Arrays;

/**
 * The Auditorium class represents an auditorium with rows, columns, and seating capacity.
 * It includes methods for accessing auditorium details, viewing the seating layout,
 * and managing seating availability.
 */

public class Auditorium implements HasID{

    private Integer id; /**Unique identifier*/
    private String name; /**Name of */
    private int capacity; /**Capacity of the auditorium*/
    private int rows; /**How many rows*/
    public int cols; /**How many columns*/
    private boolean[][] seatPlace; /**A boolean matrix for the availability of the seat*/

    /**
     * Constructs a new Auditorium with the specified id, name, rows, and columns.
     * Initializes the seating layout, marking all seats as available.
     *
     * @param id     the unique identifier of the auditorium
     * @param name   the name of the auditorium
     * @param rows   the number of rows of seats in the auditorium
     * @param cols   the number of columns of seats in the auditorium
     *
     * seatPlace - initially all seats are available
     */
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

    /**
     * Gets the name of the auditorium.
     *
     * @return the name of the auditorium
     */
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

//    /**
//     * Gets the seating capacity of the auditorium.
//     *
//     * @return the seating capacity of the auditorium
//     */
//    public int getCapacity() {
//        return capacity;
//    }
//
//    public void setCapacity(int rows, int cols) {
//        this.capacity = rows * cols;
//        this.rows = rows;
//        this.cols = cols;
//    }

    /**
     * Gets the number of rows in the auditorium.
     *
     * @return the number of rows in the auditorium
     */
    public int getRows() {
        return rows;
    }

    /**
     * Gets the number of columns in the auditorium.
     *
     * @return the number of columns in the auditorium
     */
    public int getCols() {
        return cols;
    }

    /**
     * Gets the seating layout of the auditorium, where {@code true} represents an available seat.
     *
     * @return a 2D array representing the seating layout
     */
    public boolean[][] getSeatPlace() {
        return seatPlace;
    }

    /**
     * Returns a string representation of the Auditorium object.
     *
     * @return a string containing the id, name, capacity, rows, and columns of the auditorium
     */
    @Override
    public String toString() {
        return "Auditorium{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", rows=" + rows +
                ", cols=" + cols;
    }

    /**
     * Provides a detailed view of the auditorium layout, showing seat availability.
     * Displays each seat as "00x" for available seats or "---" for unavailable seats.
     *
     * @return a string containing the auditorium layout, with seat numbers and availability
     */
    public String viewAuditoriumWithoutLayout(){
        String result = "Auditorium{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", rows=" + rows +
                ", cols=" + cols +
                ", seats= \n";

        int cnt = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (cnt < 10)
                    result += (seatPlace[i][j]) ? "|00" + cnt : "|---";
                else if (cnt < 100)
                    result += (seatPlace[i][j]) ? "|0" + cnt : "|---";
                else
                    result += (seatPlace[i][j]) ? "|" + cnt : "|---";
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
}
