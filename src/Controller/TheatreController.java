package Controller;

import Domain.*;
import Service.TheatreService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Controller class for managing theatre-related operations.
 * This class acts as an intermediary between the service layer and external interfaces (e.g., UI).
 */
public class TheatreController {
    private final TheatreService theatreService;

    /**
     * Constructor for TheatreController.
     *
     * @param theatreService The service instance handling theatre operations.
     */
    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    /**
     * Logs in a user using their email.
     *
     * @param eMail The email of the user.
     * @return A session token if login is successful.
     */
    public String login(EMail eMail) {
        return theatreService.login(eMail);
    }

    /**
     * Creates a new viewer account.
     *
     * @param name The name of the viewer.
     * @param age The age of the viewer.
     * @param eMail The email address of the viewer.
     * @return True if the account is successfully created; otherwise, false.
     */
    public boolean createViewerAccount(String name, int age, EMail eMail) {
        return theatreService.createViewerAccount(name, age, eMail);
    }

    /**
     * Updates an existing viewer account.
     *
     * @param id The unique ID of the viewer.
     * @param name The updated name of the viewer.
     * @param age The updated age of the viewer.
     * @param eMail The updated email address of the viewer.
     * @return True if the account is successfully updated; otherwise, false.
     */
    public boolean manageViewerAccount(Integer id, String name, int age, EMail eMail) {
        return theatreService.manageViewerAccount(id, name, age, eMail);
    }

    /**
     * Deletes a viewer account.
     *
     * @param id The unique ID of the viewer.
     * @return True if the account is successfully deleted; otherwise, false.
     */
    public boolean deleteViewerAccount(Integer id) {
        return theatreService.deleteViewerAccount(id);
    }

    /**
     * Creates a new admin account.
     *
     * @param id The unique ID of the admin.
     * @param name The name of the admin.
     * @param age The age of the admin.
     * @param eMail The email address of the admin.
     * @return True if the account is successfully created; otherwise, false.
     */
    public boolean createAdminAccount(Integer id, String name, int age, EMail eMail) {
        return theatreService.createAdminAccount(id, name, age, eMail);
    }

    /**
     * Updates an existing admin account.
     *
     * @param id The unique ID of the admin.
     * @param name The updated name of the admin.
     * @param age The updated age of the admin.
     * @param eMail The updated email address of the admin.
     * @return True if the account is successfully updated; otherwise, false.
     */
    public boolean manageAdminAccount(Integer id, String name, int age, EMail eMail) {
        return theatreService.manageAdminAccount(id, name, age, eMail);
    }

    /**
     * Deletes an admin account.
     *
     * @param id The unique ID of the admin.
     * @return True if the account is successfully deleted; otherwise, false.
     */
    public boolean deleteAdminAccount(Integer id) {
        return theatreService.deleteAdminAccount(id);
    }

    /**
     * Creates a new actor account.
     *
     * @param name The name of the actor.
     * @param age The age of the actor.
     * @param eMail The email address of the actor.
     * @param salary The salary of the actor.
     * @return True if the account is successfully created; otherwise, false.
     */
    public boolean createActorAccount(String name, int age, EMail eMail, int salary) {
        return theatreService.createActorAccount(name, age, eMail, salary);
    }

    /**
     * Updates an existing actor account.
     *
     * @param id The unique ID of the actor.
     * @param name The updated name of the actor.
     * @param age The updated age of the actor.
     * @param eMail The updated email address of the actor.
     * @return True if the account is successfully updated; otherwise, false.
     */
    public boolean manageActorAccount(Integer id, String name, int age, EMail eMail) {
        return theatreService.manageActorAccount(id, name, age, eMail);
    }

    /**
     * Deletes an actor account.
     *
     * @param id The unique ID of the actor.
     * @return True if the account is successfully deleted; otherwise, false.
     */
    public boolean deleteActorAccount(Integer id) {
        return theatreService.deleteActorAccount(id);
    }

    /**
     * Changes the salary of an actor.
     *
     * @param id The unique ID of the actor.
     * @param salary The new salary value.
     * @return True if the salary is successfully changed; otherwise, false.
     */
    public boolean changeSalary(Integer id, int salary) {
        return theatreService.changeSalary(id, salary);
    }

    /**
     * Creates a new auditorium.
     *
     * @param name The name of the auditorium.
     * @param rows The number of rows in the auditorium.
     * @param cols The number of columns in the auditorium.
     * @return True if the auditorium is successfully created; otherwise, false.
     */
    public boolean createAuditorium(String name, int rows, int cols) {
        return theatreService.createAuditorium(name, rows, cols);
    }

    /**
     * Deletes an auditorium.
     *
     * @param id The unique ID of the auditorium.
     * @return True if the auditorium is successfully deleted; otherwise, false.
     */
    public boolean deleteAuditorium(Integer id) {
        return theatreService.deleteAuditorium(id);
    }

    /**
     * Creates a new show.

     * @param title The title of the show.
     * @param date The date of the show.
     * @param auditoriumID The ID of the auditorium where the show takes place.
     * @param roles A map of actors to their roles in the show.
     * @param price The ticket price for the show.
     * @return True if the show is successfully created; otherwise, false.
     */
    public boolean createShow(String title, Date date, Integer auditoriumID, Map<Actor, String> roles, int price) {
        return theatreService.createShow(title, date, auditoriumID, roles, price);
    }

    /**
     * Deletes a show.
     *
     * @param id The unique ID of the show.
     * @return True if the show is successfully deleted; otherwise, false.
     */
    public boolean deleteShow(Integer id) {
        return theatreService.deleteShow(id);
    }

    /**
     * Creates a new order for tickets.
     *
     * @param viewerID The ID of the viewer placing the order.
     * @param showID The ID of the show for which tickets are ordered.
     * @param seats The list of seat numbers to be reserved.
     * @return True if the order is successfully created; otherwise, false.
     */
    public boolean createOrder(Integer viewerID, Integer showID, List<Integer> seats) {
        return theatreService.createOrder(viewerID, showID, seats);
    }

    /**
     * Deletes an existing order.
     *
     * @param id The unique ID of the order to be deleted.
     * @return The total refund amount for the deleted order, or -1 if deletion fails.
     */
    public int deleteOrder(Integer id) {
        return theatreService.deleteOrder(id);
    }


    ////////////////*** VIEW ***////////////////

    /**
     * Views account details for a specific email.
     *
     * @param eMail The email address associated with the account.
     * @return The Person object representing the account details.
     */
    public Person viewAccount(EMail eMail) {
        return theatreService.viewAccount(eMail);
    }

    /**
     * Views the list of shows in which a specific actor is performing.
     *
     * @param id The unique ID of the actor.
     * @return A list of Show objects associated with the actor.
     */
    public List<Show> viewActorShows(Integer id) {
        return theatreService.viewActorShows(id);
    }

    /**
     * Views all orders placed by a specific viewer.
     *
     * @param id The unique ID of the viewer.
     * @return A list of Order objects associated with the viewer.
     */
    public List<Order> viewViewerOrders(Integer id) {
        return theatreService.viewViewerOrders(id);
    }

    /**
     * Views all actors.
     *
     * @return A list of Actor objects.
     */
    public List<Actor> viewActors() {
        return theatreService.getActors();
    }

    /**
     * Views details of a specific actor.
     *
     * @param id The unique ID of the actor.
     * @return The Actor object representing the actor details.
     */
    public Actor viewActor(Integer id) {
        return theatreService.getActor(id);
    }

    /**
     * Views all viewers.
     *
     * @return A list of Viewer objects.
     */
    public List<Viewer> viewViewers() {
        return theatreService.getViewers();
    }

    /**
     * Views details of a specific viewer.
     *
     * @param id The unique ID of the viewer.
     * @return The Viewer object representing the viewer details.
     */
    public Viewer viewViewer(Integer id) {
        return theatreService.getViewer(id);
    }

    /**
     * Views all admins.
     *
     * @return A list of Admin objects.
     */
    public List<Admin> viewAdmins() {
        return theatreService.getAdmins();
    }

    /**
     * Views details of a specific admin.
     *
     * @param id The unique ID of the admin.
     * @return The Admin object representing the admin details.
     */
    public Admin viewAdmin(Integer id) {
        return theatreService.getAdmin(id);
    }

    /**
     * Views all auditoriums.
     *
     * @return A list of Auditorium objects.
     */
    public List<Auditorium> viewAuditoriums() {
        return theatreService.getAuditoriums();
    }

    /**
     * Views details of a specific auditorium.
     *
     * @param id The unique ID of the auditorium.
     * @return The Auditorium object representing the auditorium details.
     */
    public Auditorium viewAuditorium(Integer id) {
        return theatreService.getAuditorium(id);
    }

    /**
     * Gets the auditorium associated with a specific show.
     *
     * @param id The unique ID of the show.
     * @return The Auditorium object associated with the show.
     */
    public Auditorium getAuditoriumByShow(Integer id) {
        return theatreService.getAuditoriumByShow(id);
    }

    /**
     * Views all shows.
     *
     * @return A list of Show objects.
     */
    public List<Show> viewShows() {
        return theatreService.getShows();
    }

    /**
     * Views all shows sorted by date or other criteria.
     *
     * @return A sorted list of Show objects.
     */
    public List<Show> viewShowsSorted() {
        return theatreService.getShowsSorted();
    }

    /**
     * Views shows that meet specific filtering criteria.
     *
     * @return A filtered list of Show objects.
     */
    public List<Show> viewShowsFiltered() {
        return theatreService.getShowsFiltered();
    }

    /**
     * Views details of a specific show.
     *
     * @param id The unique ID of the show.
     * @return The Show object representing the show details.
     */
    public Show viewShow(Integer id) {
        return theatreService.getShow(id);
    }

    /**
     * Views all orders.
     *
     * @return A list of Order objects.
     */
    public List<Order> viewOrders() {
        return theatreService.getOrders();
    }

    /**
     * Views orders sorted by specific criteria.
     *
     * @param id The ID to filter or sort orders for (e.g., viewer or show ID).
     * @return A sorted list of Order objects.
     */
    public List<Order> viewOrdersSorted(Integer id) {
        return theatreService.getOrdersSorted(id);
    }

    /**
     * Views orders filtered by specific criteria (e.g., viewer or show details).
     *
     * @param id The ID to filter orders for (e.g., viewer ID).
     * @return A filtered list of Order objects.
     */
    public List<Order> viewOrdersFiltered(Integer id) {
        return theatreService.getOrdersFiltered(id);
    }

    /**
     * Views details of a specific order.
     *
     * @param id The unique ID of the order.
     * @return The Order object representing the order details.
     */
    public Order viewOrder(Integer id) {
        return theatreService.getOrder(id);
    }
}