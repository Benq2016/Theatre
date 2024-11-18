package Repository;

import Domain.Order;
import Domain.Ticket;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OrderFileRepository extends FileRepository<Order> {
    public OrderFileRepository(String filePath) {
        super(filePath);
    }

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

    @Override
    protected Order deserialize(String data) {
        // Split the serialized string into parts
        String[] objectParts = data.split(",");

        if (objectParts.length < 7) {
            throw new IllegalArgumentException("Invalid data format for Order deserialization");
        }

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
