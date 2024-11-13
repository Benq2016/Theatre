package ControllerService;

import Domain.*;

import java.util.Map;
import java.util.List;

/**
 * The TheatreController class serves as a controller for theatre-related operations,
 * interacting with the TheatreService to manage users(Actors, Viewers, Ceos), shows and orders.
 */
public class TheatreController {
    private final TheatreService theatreService;

    /**
     * Creates a TheatreController instance with a specified TheatreService.
     * @param theatreService the service to be used by this controller
     */
    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    ////////GENERAL OPTIONS////////
    /**
     * Logs in a user by email and returns their role.
     * @param eMail the email of the user trying to log in
     * @return an integer representing the role (1 - Actor, 2 - CEO, 3 - Viewer, 0 - if the email does not exist)
     */
    public int login(EMail eMail) {
        return theatreService.loginAndGiveBackRole(eMail);
    }

    /**
     * Creates a Viewer account.
     * @param id the ID of the viewer
     * @param name the name of the viewer
     * @param age the age of the viewer
     * @param eMail the email of the viewer
     * @return true if the account was created successfully, false otherwise
     */
    public boolean createViewerAccount(Integer id, String name, int age, EMail eMail) {
        return theatreService.createViewerAccount(id, name, age, eMail);
    }

    /**
     * Creates a CEO account.
     * @param id the ID of the CEO
     * @param name the name of the CEO
     * @param age the age of the CEO
     * @param eMail the email of the CEO
     * @return true if the account was created successfully, false otherwise
     */
    public boolean createCeoAccount(Integer id, String name, int age, EMail eMail) {
        return theatreService.createCeoAccount(id, name, age, eMail);
    }

    //////////VIEWER OPTIONS//////////
    /**
     * Retrieves a list of all available Shows.
     * @return a list of all Shows
     */
    public List<Show> viewShows() {
        return theatreService.getAllShows();
    }

    /**
     * Retrieves details of a specific Show, including its Auditorium.
     * @param showTitle the title of the Show
     * @return a map containing the show and its corresponding auditorium
     */
    public Map<Show, Auditorium> viewShow(String showTitle) {
        return theatreService.getShow(showTitle);
    }

    /**
     * Creates an order for a specific Viewer.
     * @param id the ID of the Order
     * @param showID the ID of the Show for which the Order is placed
     * @param eMail the Email of the Viewer placing the Order
     * @param seats a list of seat numbers for the Order
     * @return true if the Order was created successfully, false otherwise
     */
    public boolean createOrder(Integer id, int showID, EMail eMail, List<Integer> seats) {
        return theatreService.createOrder(id, showID, eMail, seats);
    }

    /**
     * Retrieves a list of orders for a specific Viewer.
     * @param eMail the Email of the Viewer
     * @return a list of Orders associated with the Viewer
     */
    public List<Order> viewMyOrders(EMail eMail) {
        return theatreService.showMyOrders(eMail);
    }

    /**
     * Retrieves account details for a user by Email.
     * @param eMail the Email of the user
     * @return a Person object representing the user (Actor, Viewer, or CEO), or null if not found
     */
    public Person viewAccount(EMail eMail) {
        return theatreService.getAccount(eMail);
    }

    /**
     * Manages viewer account details.
     * @param name the new name of the viewer
     * @param age the new age of the viewer
     * @param currentEmail the current email of the viewer
     * @param newEmail the new email of the viewer
     * @return true if the email was changed to a Viewer, false if only the name and age were changed
     */
    public boolean manageViewerAccount(String name, int age, EMail currentEmail, EMail newEmail) {
        return theatreService.manageViewerAccount(name, age, currentEmail, newEmail);
    }

    //////////CEO OPTIONS//////////
    /**
     * Hires a new actor.
     * @param actorID the ID of the actor
     * @param name the name of the actor
     * @param age the age of the actor
     * @param actorEmail the email of the actor
     * @param salary the salary of the actor
     * @return true if the account was created successfully, false otherwise
     */
    public boolean ceoHireActor(Integer actorID, String name, int age, EMail actorEmail, int salary) {
        return theatreService.hireActor(actorID, name, age, actorEmail, salary);
    }

    /**
     * Fires an actor by ID.
     * @param actorID the ID of the actor to be fired
     */
    public void ceoFireActor(Integer actorID) {
        theatreService.fireActor(actorID);
    }

    /**
     * Changes the salary of an actor.
     * @param actorID the ID of the actor
     * @param newSalary the new salary for the actor
     */
    public void ceoChangeSalary(Integer actorID, int newSalary) {
        theatreService.changeActorSalary(actorID, newSalary);
    }

    /**
     * Retrieves a list of all actors.
     * @return a list of all actors
     */
    public List<Actor> viewAllActors(){
        return theatreService.getAllActors();
    }

    /**
     * Retrieves an actor by ID.
     * @param actorID the ID of the actor
     * @return the Actor object with the specified ID
     */
    public Actor viewActor(Integer actorID) {
        return theatreService.getActor(actorID);
    }

    /**
     * Retrieves a list of all auditoriums.
     * @return a list of all auditoriums
     */
    public List<Auditorium> viewAllAuditoriums(){
        return theatreService.getAllAuditoriums();
    }

//    public List<Auditorium> viewAllAuditoriumsWithoutLayout(){
//        return theatreService.getAllAuditoriums();
//    }

    /**
     * Retrieves an auditorium by ID.
     * @param auditoriumID the ID of the auditorium
     * @return the Auditorium object with the specified ID
     */
    public Auditorium viewAuditorium(Integer auditoriumID) {
        return theatreService.getAuditorium(auditoriumID);
    }

    /**
     * Retrieves the auditorium assigned to a specific show.
     * @param showId the ID of the show
     * @return the Auditorium object for the specified show
     */
    public Auditorium getAuditoriumByShowId(Integer showId){
        return theatreService.getAuditoriumByShowID(showId);
    }

    /**
     * Creates a new show.
     * @param showID the ID of the show
     * @param title the title of the show
     * @param auditorium the auditorium for the show
     * @param roles a map of actors and their roles in the show
     * @param date the date of the show
     * @return true if the show was created successfully, false otherwise
     */
    public boolean createShow(Integer showID, String title, Auditorium auditorium, Map<Actor, String> roles, String date) {
        return theatreService.createShow(showID, title, auditorium, roles, date);
    }

    /**
     * Deletes a show by ID.
     * @param showID the ID of the show to be deleted
     */
    public void ceoDeleteShow(Integer showID) {
        theatreService.deleteShow(showID);
    }

    /**
     * Creates a new auditorium.
     * @param id the ID of the auditorium
     * @param name the name of the auditorium
     * @param rows the number of rows in the auditorium
     * @param cols the number of columns in the auditorium
     * @return true if the auditorium was created successfully, false otherwise
     */
    public boolean createAuditorium(Integer id,String name, int rows, int cols){
        return theatreService.createAuditorium(id, name, rows, cols);
    }

    /**
     * Deletes an auditorium by ID.
     * @param id the ID of the auditorium to be deleted
     */
    public void deleteAuditorium(Integer id){
        theatreService.deleteAuditorium(id);
    }

    /**
     * Manages CEO account details.
     * @param name the new name of the CEO
     * @param age the new age of the CEO
     * @param currentEmail the current email of the CEO
     * @param newEmail the new email of the CEO
     * @return true if the email was changed to a Viewer, false if only the name and age were changed
     */
    public boolean manageCeoAccount(String name, int age, EMail currentEmail, EMail newEmail){
        return theatreService.manageCeoAccount(name, age, currentEmail, newEmail);
    }


    //////////ACTOR//////////
    /**
     * Retrieves a list of all shows in which the specified actor is scheduled to appear.
     * @param eMail the email of the actor whose shows are to be retrieved
     * @return a list of shows where the actor has to appear
     */
    public List<Show> viewMyShows(EMail eMail) {
        return theatreService.showMyShows(eMail);
    }

    /**
     * Updates the account details for an actor.
     * @param name the new name of the actor
     * @param age the new age of the actor
     * @param currentEmail the current email of the actor
     * @param newEmail the new email of the actor
     * @return true if the email was changed successfully, false if only the name and age were updated
     */
    public boolean manageActorAccount(String name, int age, EMail currentEmail, EMail newEmail){
        return theatreService.manageActorAccount(name, age, currentEmail, newEmail);
    }
}