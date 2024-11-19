package Repository;

import Domain.Order;
import Domain.Ticket;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A repository for managing Order objects, which are stored in a file.
 * The CRUD operations are handled by the abstract class.
 * It serializes and deserializes Order objects to and from a file.
 */
public class OrderFileRepository extends FileRepository<Order> {
    /**
     * Constructs an OrderFileRepository with the specified file path.
     *
     * @param filePath the path to the file used for storing order data
     */
    public OrderFileRepository(String filePath) {
        super(filePath);
    }
    /**
     * Serializes an Order object into a string representation.
     * The string format is: <id>,<date>,<viewerID>,<showID>,<seatsSerialized>,<ticketsSerialized>,<totalPrice>.
     * - seatsSerialized is a pipe-separated list of seat numbers.
     * - ticketsSerialized is a pipe-separated list of ticket details, each formatted as <ticketID>:<showName>:<viewerName>:<auditoriumName>:<price>:<seat>.
     *
     * @param obj the Order object to serialize
     * @return a string representation of the Order object
     */
    @Override
    protected String serialize(Order obj) {
        String seatsSerialized = obj.getSeats().stream()
                .map(String::valueOf)
                .collect(Collectors.joining("|"));

        String ticketsSerialized = obj.getTickets().stream()
                .map(ticket -> ticket.getID() + ":" +
                        ticket.getShowName() + ":" +
                        ticket.getViewerName() + ":" +
                        ticket.getAuditoriumName() + ":" +
                        ticket.getPrice() + ":" +
                        ticket.getSeat())
                .collect(Collectors.joining("|"));

        return obj.getID() + "," + obj.getDate() + "," + obj.getViewerID() + "," +
                obj.getShowID() + "," + seatsSerialized + "," + ticketsSerialized + "," +
                obj.getTotalPrice();
    }

    /**
     * Deserializes a string into an Order object.
     * The expected string format is: <id>,<date>,<viewerID>,<showID>,<seatsSerialized>,<ticketsSerialized>,<totalPrice>.
     * - seatsSerialized is a pipe-separated list of seat numbers.
     * - ticketsSerialized is a pipe-separated list of ticket details, each formatted as <ticketID>:<showName>:<viewerName>:<auditoriumName>:<price>:<seat>.
     *
     * @param data the string data to deserialize
     * @return the Order object constructed from the string data
     */
    @Override
    protected Order deserialize(String data) {
        // Split the serialized string into parts
        String[] objectParts = data.split(",");


        // Parse primitive fields
        int id = Integer.parseInt(objectParts[0]);
        LocalDate date = LocalDate.parse(objectParts[1]);
        int viewerID = Integer.parseInt(objectParts[2]);
        int showID = Integer.parseInt(objectParts[3]);

        // Parse seats as List<Integer>
        List<Integer> seats = Arrays.stream(objectParts[4].split("\\|"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        // Parse tickets as List<Ticket>
        List<Ticket> tickets = Arrays.stream(objectParts[5].split("\\|"))
                .map(ticketStr -> {
                    String[] ticketParts = ticketStr.split(":");
                    int ticketID = Integer.parseInt(ticketParts[0]);
                    String showName = ticketParts[1];
                    String viewerName = ticketParts[2];
                    String auditoriumName = ticketParts[3];
                    int price = Integer.parseInt(ticketParts[4]);
                    int seat = Integer.parseInt(ticketParts[5]);
                    return new Ticket(ticketID, showName, viewerName, auditoriumName, price, seat);
                })
                .collect(Collectors.toList());

        // Parse the total price
        int totalPrice = Integer.parseInt(objectParts[6]);

        // Create and return the Order object
        return new Order(id, date, viewerID, showID, seats, tickets, totalPrice);
    }
}
