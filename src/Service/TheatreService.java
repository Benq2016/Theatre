package Service;

import Domain.*;
import Exceptions.EntityNotFoundException;
import Exceptions.ValidationException;
import Repository.AdminDBRepository;

import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Service class that handles operations related to the theatre system,
 * including managing actors, viewers, shows, orders, and auditoriums.
 */
public class TheatreService {
    private final AdminService adminService;
    private final ActorService actorService;
    private final AuditoriumService auditoriumService;
    private final ShowService showService;
    private final ViewerService viewerService;
    private final OrderService orderService;


    /**
     * Constructs a TheatreService instance with dependencies for managing various services.
     * @param adminService The service for managing admin-related operations.
     * @param actorService The service for managing actor-related operations.
     * @param auditoriumService The service for managing auditorium-related operations.
     * @param showService The service for managing show-related operations.
     * @param viewerService The service for managing viewer-related operations.
     * @param orderService The service for managing order-related operations.
     */
    public TheatreService(AdminService adminService, ActorService actorService, AuditoriumService auditoriumService, ShowService showService, ViewerService viewerService, OrderService orderService) {
        this.adminService = adminService;
        this.actorService = actorService;
        this.auditoriumService = auditoriumService;
        this.showService = showService;
        this.viewerService = viewerService;
        this.orderService = orderService;
    }

    /**
     * View the account details for an actor, viewer, or admin based on their email.
     * @param eMail The email of the user to find.
     * @return The person (Actor, Viewer, or Admin) associated with the provided email.
     */
    public Person viewAccount(EMail eMail){
        List<Actor> actors = actorService.getAllActors();
        List<Viewer> viewers = viewerService.getAllViewers();
        List<Admin> admins = adminService.getAllAdmins();

        for (Actor actor : actors)
            if (actor.getEmail().equals(eMail))
                return actor;
        for (Viewer viewer : viewers)
            if (viewer.getEmail().equals(eMail))
                return viewer;
        for (Admin admin : admins)
            if (admin.getEmail().equals(eMail))
                return admin;
        return null;
    }

    /**
     * View all shows that an actor is part of based on their ID.
     * @param id The unique ID of the actor.
     * @return A list of shows the actor is part of.
     */
    public List<Show> viewActorShows(Integer id) {
        List<Show> allShows = showService.getAllShows();
        List<Show> myShows = new ArrayList<>();

        for (Show show : allShows)
            if (show.getRoles().containsKey(actorService.getActor(id)))
                myShows.add(show);

        return myShows;
    }

    /**
     * View all orders made by a viewer based on their ID.
     * @param id The unique ID of the viewer.
     * @return A list of orders made by the viewer.
     */
    public List<Order> viewViewerOrders(Integer id) {
        List<Order> orders = orderService.getAllOrders();
        List<Order> myOrders = new ArrayList<>();

        for (Order order : orders)
            if (order.getViewerID().equals(id))
                myOrders.add(order);

        return myOrders;
    }

    /**
     * Log in using an email and determine if the account is an Actor, Admin, or Viewer.
     * @param eMail The email to log in with.
     * @return A string indicating the role: "1" for Actor, "2" for Admin, "3" for Viewer, "0" for invalid login.
     */
    public String login(EMail eMail) {
        List<Actor> actors = actorService.getAllActors();
        List<Admin> admins = adminService.getAllAdmins();
        List<Viewer> viewers = viewerService.getAllViewers();
        for (Actor actor : actors)
            if (actor.getEmail().equals(eMail))
                return "1";

        for (Admin admin : admins)
            if (admin.getEmail().equals(eMail))
                return "2";

        for (Viewer viewer : viewers)
            if (viewer.getEmail().equals(eMail))
                return "3";
        return "0";
    }


    ////////////////*** CREATE *** UPDATE *** DELETE ***////////////////


    /**
     * Creates a viewer account.
     * @param name The name of the viewer.
     * @param age The age of the viewer.
     * @param eMail The email address of the viewer.
     * @return The newly created Viewer object.
     * */
    public Viewer createViewerAccount(String name, int age, EMail eMail) {
        return viewerService.createViewer(name, age, eMail);
    }

    /**
     * Updates a viewer's account information.
     * @param id The unique ID of the viewer.
     * @param name The updated name of the viewer.
     * @param age The updated age of the viewer.
     * @param eMail The updated email address of the viewer.
     * @return The updated Viewer object.
     */
    public Viewer manageViewerAccount(Integer id, String name, int age, EMail eMail) {
        return viewerService.updateViewer(id, name, age, eMail);
    }

    /**
     * Deletes a viewer account.
     * @param id The unique ID of the viewer to delete.
     */
    public void deleteViewerAccount(Integer id) {
        viewerService.deleteViewer(id);
    }

    /**
     * Creates an admin account.
     * @param id The unique ID of the admin.
     * @param name The name of the admin.
     * @param age The age of the admin.
     * @param eMail The email address of the admin.
     * @return The newly created Admin object.
     */
    public Admin createAdminAccount(Integer id, String name, int age, EMail eMail) {
        return adminService.createAdmin(id, name, age, eMail);
    }

    /**
     * Updates an admin's account information.
     * @param id The unique ID of the admin.
     * @param name The updated name of the admin.
     * @param age The updated age of the admin.
     * @param eMail The updated email address of the admin.
     * @return The updated Admin object.
     */
    public Admin manageAdminAccount(Integer id, String name, int age, EMail eMail) {
        return adminService.updateAdmin(id, name, age, eMail);
    }

    /**
     * Deletes an admin account.
     * @param id The unique ID of the admin to delete.
     */
    public void deleteAdminAccount(Integer id) {
        adminService.deleteAdmin(id);
    }

    /**
     * Creates an actor account.
     * @param name The name of the actor.
     * @param age The age of the actor.
     * @param eMail The email address of the actor.
     * @param salary The salary of the actor.
     * @return The newly created Actor object.
     */
    public Actor createActorAccount(String name, int age, EMail eMail, int salary) {
        return actorService.createActor(name, age, eMail, salary);
    }

    /**
     * Updates an actor's account information.
     * @param id The unique ID of the actor.
     * @param name The updated name of the actor.
     * @param age The updated age of the actor.
     * @param eMail The updated email address of the actor.
     * @return The updated Actor object.
     */
    public Actor manageActorAccount(Integer id, String name, int age, EMail eMail) {
        return actorService.updateActor(id, name, age, eMail);
    }

    /**
     * Deletes an actor account.
     * @param id The unique ID of the actor to delete.
     */
    public void deleteActorAccount(Integer id) {
        actorService.deleteActor(id);
    }

    /**
     * Changes the salary of an actor.
     * @param id The unique ID of the actor.
     * @param salary The new salary of the actor.
     */
    public void changeSalary(Integer id, int salary) {
        actorService.changeSalary(id, salary);
    }

    /**
     * Creates a new auditorium.
     * @param name The name of the auditorium.
     * @param rows The number of rows in the auditorium.
     * @param cols The number of columns in the auditorium.
     * @return The newly created Auditorium object.
     */
    public Auditorium createAuditorium(String name, int rows, int cols) {
        return auditoriumService.createAuditorium(name, rows, cols);
    }

    /**
     * Deletes an auditorium.
     * @param id The unique ID of the auditorium to delete.
     */
    public void deleteAuditorium(Integer id) {
        auditoriumService.deleteAuditorium(id);
    }

    /**
     * Creates a new show and adds it to the repository.
     * @param title The title of the show.
     * @param date The date and time of the show.
     * @param auditoriumID The ID of the auditorium where the show will be held.
     * @param roles A map of actors and their corresponding roles in the show.
     * @param price The ticket price for the show.
     * @return The newly created Show object.
     * @throws EntityNotFoundException If the auditorium with the specified ID does not exist.
     */
    public Show createShow(String title, Date date, Integer auditoriumID, Map<Actor, String> roles, int price) {

        if (auditoriumService.getAuditorium(auditoriumID) == null)
            throw new EntityNotFoundException("Auditorium does not exist!");

        Auditorium auditorium = auditoriumService.getAuditorium(auditoriumID);
        Show show = new Show(title, date, auditorium, roles, price);
        showService.createShow(show);
        return show;
    }

    /**
     * Deletes a show by its unique ID.
     * @param id The unique ID of the show to delete.
     */
    public void deleteShow(Integer id) {
        showService.deleteShow(id);
    }

    /**
     * Creates a new order for a specified viewer and show.
     * @param viewerID The unique ID of the viewer placing the order.
     * @param showID The unique ID of the show for which the order is being placed.
     * @param seats A list of seat numbers the viewer wants to book.
     * @return The newly created Order object.
     * @throws EntityNotFoundException If the viewer or show with the specified IDs does not exist.
     * @throws ValidationException If the specified seats are already occupied.
     */
    public Order createOrder(Integer viewerID, int showID, List<Integer> seats) {
        List<Integer> simplifiedSeats = new ArrayList<>();
        for (int seat : seats)
            if (!simplifiedSeats.contains(seat))
                simplifiedSeats.add(seat);

        if (viewerService.getViewer(viewerID) == null)
            throw new EntityNotFoundException("Viewer does not exist!");

        if (showService.getShow(showID) == null)
            throw new EntityNotFoundException("Show does not exist!");

        LocalDate date = LocalDateTime.now().toLocalDate();

        int price = showService.getShow(showID).getPrice();
        int totalPrice = price * simplifiedSeats.size();

        List<Ticket> tickets = createTickets(viewerID, showID, simplifiedSeats, price);

        if(!occupySeats(showID, simplifiedSeats))
            throw new ValidationException("Seats were already occupied!");

        Order order = new Order(date, viewerID, showID, simplifiedSeats, tickets, totalPrice);
        orderService.createOrder(order);
        return order;
    }

    /**
     * Creates tickets for the given order with viewer and show details.
     * @param viewerID the unique identifier of the viewer
     * @param showID the unique identifier of the show
     * @param seats a list of seat numbers for the order
     * @param price the price of a single ticket
     * @return a list of tickets created for the given order
     */
    public List<Ticket> createTickets(Integer viewerID, Integer showID, List<Integer> seats, int price) {
        String viewerName = viewerService.getViewer(viewerID).getName();
        String showTitle = showService.getShow(showID).getTitle();
        String auditoriumName = showService.getShow(showID).getAudit().getName();
        int ticketID = 1;

        List<Ticket> tickets = new ArrayList<>();
        for (Integer seat : seats) {
            Ticket ticket = new Ticket(ticketID, showTitle, viewerName, auditoriumName, price, seat);
            ticketID++;
            tickets.add(ticket);
        }
        return tickets;
    }

    /**
     * Occupies the specified seats for a given show by updating the seat availability in the auditorium.
     * @param showID the unique identifier of the show for which the seats should be occupied
     * @param seats a list of seat numbers to be occupied
     * @return true if the seats were successfully occupied, false if any seat is unavailable
     */
    public boolean occupySeats(Integer showID, List<Integer> seats) {
        Auditorium auditorium = showService.getShow(showID).getAudit();
        auditorium = getAuditoriumWithOccupiedSeatsExcluded(showID,auditorium.getID());
        if (!checkSeats(auditorium, seats))
            return false;

        for (Integer seat : seats) {
            int row = (seat - 1) / auditorium.getCols();
            int col = (seat - 1) % auditorium.getCols();

            if (row >= 0 && row < auditorium.getRows() && col >= 0 && col < auditorium.getCols())
                auditorium.getSeatPlace()[row][col] = false;
        }
        return true;
    }

    /**
     * Checks if the specified seats are available in the given auditorium.
     * @param auditorium the auditorium where the seats are located
     * @param seats a list of seat numbers to check for availability
     * @return true if all seats are available, false if any seat is unavailable
     */
    public boolean checkSeats(Auditorium auditorium, List<Integer> seats) {
        int cols = auditorium.getCols();

        for (int seat : seats) {
            if (seat < 1 || seat > cols * auditorium.getRows())
                return false;

            int row = (seat - 1) / cols;
            int col = (seat - 1) % cols;
            if (!auditorium.getSeatPlace()[row][col])
                return false;
        }
        return true;
    }

    /**
     * Deletes an order by its unique ID and releases the associated seats.
     * @param id The unique ID of the order to delete.
     * @return The total price of the deleted order.
     * @throws EntityNotFoundException If no order with the specified ID exists or if the Show was already played.
     */
    public int deleteOrder(Integer id) {
        Order order = orderService.getOrder(id);
        if (order == null)
            throw new EntityNotFoundException("Order does not exist!");

        Show show = showService.getShow(order.getShowID());

        List<Show> futureShows = getShowsFiltered();

        int ok = 0;
        for (Show show2 : futureShows) {
            if (show.equals(show2))
                ok++;
        }

        if (ok == 0)
            throw new EntityNotFoundException("Action unavailable for shows which were already played!");

        Auditorium auditorium = show.getAudit();

        List<Integer> seats = order.getSeats();

        releaseSeats(auditorium, seats);

        orderService.deleteOrder(id);

        return order.getTotalPrice();
    }

    /**
     * Releases the specified seats in the given auditorium, making them available again.
     * @param auditorium the auditorium where the seats are located
     * @param seats a list of seat numbers to be released
     */
    public void releaseSeats(Auditorium auditorium, List<Integer> seats) {
        int cols = auditorium.getCols();

        for (int seat : seats) {
            int row = (seat - 1) / cols;
            int col = (seat - 1) % cols;
            if (row >= 0 && row < auditorium.getRows() && col >= 0 && col < auditorium.getCols())
                auditorium.getSeatPlace()[row][col] = true;
        }
    }


    ////////////////*** VIEW ***////////////////
    /**
     * Retrieves all the actors in the system.
     * @return a list of all actors
     */
    public List<Actor> getActors() {
        return actorService.getAllActors();
    }

    /**
     * Retrieves a specific actor by their unique identifier.
     * @param id the unique identifier of the actor to retrieve
     * @return the actor with the given id, or null if no actor exists with that id
     */
    public Actor getActor(Integer id) {
        return actorService.getActor(id);
    }

    /**
     * Retrieves all the viewers in the system.
     * @return a list of all viewers
     */
    public List<Viewer> getViewers() {
        return viewerService.getAllViewers();
    }

    /**
     * Retrieves a specific viewer by their unique identifier.
     * @param id the unique identifier of the viewer to retrieve
     * @return the viewer with the given id, or null if no viewer exists with that id
     */
    public Viewer getViewer(Integer id) {
        return viewerService.getViewer(id);
    }

    /**
     * Retrieves all the admins in the system.
     * @return a list of all admins
     */
    public List<Admin> getAdmins() {
        return adminService.getAllAdmins();
    }

    /**
     * Retrieves a specific admin by their unique identifier.
     * @param id the unique identifier of the admin to retrieve
     * @return the admin with the given id, or null if no admin exists with that id
     */
    public Admin getAdmin(Integer id) {
        return adminService.getAdmin(id);
    }

    /**
     * Retrieves all the auditoriums in the system.
     * @return a list of all auditoriums
     */
    public List<Auditorium> getAuditoriums() {
        return auditoriumService.getAllAuditoriums();
    }

    /**
     * Retrieves a specific auditorium by its unique identifier.
     * @param id the unique identifier of the auditorium to retrieve
     * @return the auditorium with the given id, or null if no auditorium exists with that id
     */
    public Auditorium getAuditorium(Integer id) {
        return auditoriumService.getAuditorium(id);
    }

    /**
     * Retrieves the auditorium associated with a specific show.
     * @param id the unique identifier of the show
     * @return the auditorium where the show is being held, or null if no show exists with the given id
     */
    public Auditorium getAuditoriumByShow(Integer id) {
        List<Show> shows = showService.getAllShows();
        for (Show show : shows)
            if (show.getID().equals(id))
                return show.getAudit();
        return null;
    }

    /**
     * Retrieves all the shows in the system.
     * @return a list of all shows
     */
    public List<Show> getShows() {
        return showService.getAllShows();
    }

    /**
     * Retrieves a specific show by its unique identifier.
     * @param id the unique identifier of the show to retrieve
     * @return the show with the given id, or null if no show exists with that id
     */
    public Show getShow(Integer id) {
        return showService.getShow(id);
    }

    /**
     * Retrieves all the orders in the system.
     * @return a list of all orders
     */
    public List<Order> getOrders() {
        return orderService.getAllOrders();
    }

    /**
     * Retrieves a specific order by its unique identifier.
     * @param id the unique identifier of the order to retrieve
     * @return the order with the given id, or null if no order exists with that id
     */
    public Order getOrder(Integer id) {
        return orderService.getOrder(id);
    }

    /**
     * Retrieves and sorts all orders made by a specific viewer by the date of the order.
     * @param id the unique identifier of the viewer
     * @return a list of the viewer's orders sorted by date
     */
    public List<Order> getOrdersSorted(Integer id) {
        List<Order> orders = orderService.getAllOrders();
        List<Order> myOrders = new ArrayList<>();

        for (Order order : orders)
            if (order.getViewerID().equals(id))
                myOrders.add(order);

        myOrders.sort(Comparator.comparing(Order::getDate));

        return myOrders;
    }

    /**
     * Retrieves and filters all orders made by a specific viewer that are for shows in the future.
     * @param id the unique identifier of the viewer
     * @return a list of the viewer's future orders
     */
    public List<Order> getOrdersFiltered(Integer id) {
        List<Order> orders = orderService.getAllOrders();
        List<Order> myOrders = new ArrayList<>();
        Date today = new Date();

        for (Order order : orders)
            if (order.getViewerID().equals(id))
                myOrders.add(order);

        myOrders = myOrders.stream().filter(order -> {
                    Show show = showService.getShow(order.getShowID());
                    return show.getDate().after(today);}).collect(Collectors.toList());

        return myOrders;
    }

    /**
     * Retrieves and sorts all shows based on specific sorting criteria (e.g., date, price).
     * @return a list of shows sorted according to the defined criteria
     */
    public List<Show> getShowsSorted() {
        return showService.getShowsSorted();
    }

    /**
     * Retrieves and filters all shows based on specific filtering criteria (e.g., date, availability).
     * @return a list of shows that match the defined filtering criteria
     */
    public List<Show> getShowsFiltered() {
        return showService.getShowsFiltered();
    }

    public Auditorium getAuditoriumWithOccupiedSeatsExcluded(Integer showId,Integer auditoriumId) {
        Auditorium auditorium = auditoriumService.getAuditorium(auditoriumId);
        List<Order> allOrders = orderService.getAllOrders();
        Set<Integer> occupiedSeats = new HashSet<>();
        for (Order order : allOrders) {
            Show show = showService.getShow(order.getShowID());
            if(show.getAudit().getID().equals(auditoriumId) && show.getID().equals(showId)){
                List<Ticket> tickets = order.getTickets();
                for (Ticket ticket : tickets) {
                    occupiedSeats.add(ticket.getSeat());
                }
            }
        }
        return new Auditorium(auditorium.getName(),auditorium.getRows(),auditorium.getCols(),occupiedSeats);
    }

}