package Repository;

import Domain.Order;
import Domain.Ticket;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDBRepository implements Repository<Order> {

    private final String url = "jdbc:sqlserver://localhost:1433;databaseName=Theatre_MAP_Project;encrypt=true;trustServerCertificate=true";
    private final String userName = "SystemAdmin";
    private final String password = "0000";

    @Override
    public void create(Order obj) {
        if (exists(obj.getID()))
            return;

        String orderSql = "INSERT INTO [Order](ID, date, viewerID, showID, totalPrice) VALUES (?, ?, ?, ?, ?)";
        String ticketSql =  "INSERT INTO Ticket (ID, ShowName, ViewerName, AuditoriumName, Price," +
                " Seat, OrderID) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, userName, password)) {
            connection.setAutoCommit(false); // again for both updates to happen

            try (PreparedStatement psOrder = connection.prepareStatement(orderSql)) {
                psOrder.setInt(1, obj.getID());
                psOrder.setDate(2, java.sql.Date.valueOf(obj.getDate())); //for the Date object - complex:)
                psOrder.setInt(3, obj.getViewerID());
                psOrder.setInt(4, obj.getShowID());
                psOrder.setInt(5, obj.getTotalPrice());

                psOrder.executeUpdate();

                // Prepare the ticket insert statement
                try (PreparedStatement psTicket = connection.prepareStatement(ticketSql)) {
                    for (int i = 0; i < obj.getTickets().size(); i++) {
                        Ticket ticket = obj.getTickets().get(i);

                        psTicket.setInt(1, ticket.getID());
                        psTicket.setString(2, ticket.getShowName());
                        psTicket.setString(3, ticket.getViewerName());
                        psTicket.setString(4, ticket.getAuditoriumName());
                        psTicket.setInt(5, ticket.getPrice());
                        psTicket.setInt(6, ticket.getSeat());
                        psTicket.setInt(7, obj.getID());

                        psTicket.addBatch();
                    }

                    if (obj.getTickets().isEmpty()) {
                        System.out.println("No tickets to insert for the order.");
                    } else {
                        psTicket.executeBatch();
                    }
                }

                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Error creating order and inserting tickets. Transaction rolled back.");
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database.");
        }

    }

    @Override
    public void delete(Integer id){
        String deleteOrderSql = "DELETE FROM Order WHERE ID = ?";
        String deleteTicketSql = "DELETE FROM Ticket WHERE ID = ?";
        try (Connection connection = DriverManager.getConnection(url,userName,password)){
            connection.setAutoCommit(false);

            try (PreparedStatement psOrder = connection.prepareStatement(deleteOrderSql);
                 PreparedStatement psTicket = connection.prepareStatement(deleteTicketSql)) {
                psTicket.setInt(1,id);
                psTicket.executeUpdate();

                psOrder.setInt(1,id);
                psOrder.executeUpdate();

                connection.commit();
            }catch (SQLException e) {
            connection.rollback();
            System.out.println("Error deleting order and inserting tickets. Transaction rolled back.");
            }
        }catch (SQLException e) {
            System.out.println("Error connecting to the database.");
        }
    }

    @Override
    public void update(Order obj) {
        //Not cannot update an order -> no implementation
    }

    @Override
    public Order getByID(Integer id) {
        String orderSql = "SELECT * FROM [Order] WHERE ID = ?";
        String ticketSql = "SELECT * FROM Ticket WHERE OrderID = ?";

        Order order = null;
        List<Integer> seats = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, userName, password)) {
            try (PreparedStatement psOrder = connection.prepareStatement(orderSql)) {
                psOrder.setInt(1, id);
                try (ResultSet rsOrder = psOrder.executeQuery()) {
                    if (rsOrder.next()) {
                        int orderId = rsOrder.getInt("ID");
                        LocalDate orderDate = rsOrder.getDate("date").toLocalDate();
                        int viewerID = rsOrder.getInt("viewerID");
                        int showID = rsOrder.getInt("showID");
                        int totalPrice = rsOrder.getInt("totalPrice");

                        List<Ticket> tickets = new ArrayList<>();
                        try (PreparedStatement psTicket = connection.prepareStatement(ticketSql)) {
                            psTicket.setInt(1, orderId);
                            try (ResultSet rsTicket = psTicket.executeQuery()) {
                                while (rsTicket.next()) {
                                    int ticketId = rsTicket.getInt("ID");
                                    String showName = rsTicket.getString("ShowName");
                                    String viewerName = rsTicket.getString("ViewerName");
                                    String auditoriumName = rsTicket.getString("AuditoriumName");
                                    int price = rsTicket.getInt("Price");
                                    int seat = rsTicket.getInt("Seat");
                                    seats.add(seat);
                                    tickets.add(new Ticket(ticketId, showName, viewerName, auditoriumName, price, seat));
                                }
                            }
                        }

                        order = new Order(orderId,orderDate,viewerID,showID,seats,tickets,totalPrice);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching order details.");
        }

        return order;
    }

    @Override
    public List<Order> getAll() {
        String orderSql = "SELECT * FROM [Order]";
        String ticketSql = "SELECT * FROM Ticket WHERE OrderID = ?";

        List<Order> orders = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url,userName,password)) {
            try (PreparedStatement psOrder = connection.prepareStatement(orderSql)) {
                ResultSet rsOrder = psOrder.executeQuery();
                while (rsOrder.next()) {
                    int orderId = rsOrder.getInt("ID");
                    LocalDate orderDate = rsOrder.getDate("date").toLocalDate();
                    int viewerID = rsOrder.getInt("viewerID");
                    int showID = rsOrder.getInt("showID");
                    int totalPrice = rsOrder.getInt("totalPrice");
                    List<Ticket> tickets = new ArrayList<>();
                    List<Integer> seats = new ArrayList<>();
                    //Tickets:
                    try (PreparedStatement psTicket = connection.prepareStatement(ticketSql)) {
                        psTicket.setInt(1, orderId);
                        try (ResultSet rsTicket = psTicket.executeQuery()) {
                            while (rsTicket.next()) {
                                int ticketId = rsTicket.getInt("ID");
                                String showName = rsTicket.getString("showName");
                                String viewerName = rsTicket.getString("viewerName");
                                String auditoriumName = rsTicket.getString("auditoriumName");
                                int price = rsTicket.getInt("price");
                                int seat = rsTicket.getInt("seat");

                                seats.add(seat);
                                tickets.add(new Ticket(ticketId, showName, viewerName, auditoriumName, price, seat));
                            }
                        }
                    }
                    orders.add(new Order(orderId,orderDate,viewerID,showID,seats,tickets,totalPrice));
                }
            }

        }catch (SQLException e) {
            System.out.println("Error fetching order details.");
        }
        return orders;
    }


    private boolean exists(int orderId) {
        String orderSql = "SELECT 1 FROM [Order] WHERE ID = ?";

        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement psOrder = connection.prepareStatement(orderSql)) {

            psOrder.setInt(1, orderId);

            try (ResultSet rs = psOrder.executeQuery()) {
                return rs.next(); //if exists -> true
            }

        } catch (SQLException e) {
            System.out.println("Error checking existence of order.");
            e.printStackTrace();
        }

        return false;
    }
}
