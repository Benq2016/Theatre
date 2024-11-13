package ControllerService;

import Domain.*;
import RepositoryPackage.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.time.LocalDateTime;

public class TheatreService {
    private final Repository<Ceo> ceoRepository;
    private final Repository<Actor> actorRepository;
    private final Repository<Auditorium> auditoriumRepository;
    private final Repository<Show> showRepository;
    private final Repository<Viewer> viewerRepository;
    private final Repository<Order> orderRepository;


    /**
     * Constructs a TheatreService instance with the provided repositories for managing
     * Ceo, Actor, Auditorium, Show, Viewer, and Order entities.
     * @param ceoRepository the repository for managing CEO entities
     * @param actorRepository the repository for managing Actor entities
     * @param auditoriumRepository the repository for managing Auditorium entities
     * @param showRepository the repository for managing Show entities
     * @param viewerRepository the repository for managing Viewer entities
     * @param orderRepository the repository for managing Order entities
     */
    public TheatreService(Repository<Ceo> ceoRepository, Repository<Actor> actorRepository,
                          Repository<Auditorium> auditoriumRepository, Repository<Show> showRepository,
                          Repository<Viewer> viewerRepository, Repository<Order> orderRepository) {
        this.ceoRepository = ceoRepository;
        this.actorRepository = actorRepository;
        this.auditoriumRepository = auditoriumRepository;
        this.showRepository = showRepository;
        this.viewerRepository = viewerRepository;
        this.orderRepository = orderRepository;
    }

    ////////////////////////////////ACTOR////////////////////////////////
//    protected String getActorName(Integer actorID){
//        return actorRepository.getByID(actorID).getName();
//    }
//
//    protected int getActorAge(Integer actorID){
//        return actorRepository.getByID(actorID).getAge();
//    }
//
//    protected EMail getActorEmail(Integer actorID){
//        return actorRepository.getByID(actorID).getEmail();
//    }

    /**
     * Retrieves an Actor by their unique ID.
     * @param actorID the ID of the actor
     * @return the Actor with the specified ID, or null if not found
     */
    protected Actor getActor(Integer actorID){
        return actorRepository.getByID(actorID);
    }

    /**
     * Retrieves an Actor by their email.
     * @param email the email of the actor
     * @return the Actor with the specified email, or null if not found
     */
    protected Actor getActor(EMail email){
        List<Actor> actors = actorRepository.getAll();
        for (Actor actor : actors)
            if (actor.getEmail().equals(email))
                return actor;
        return null;
    }

    /**
     * Retrieves a list of all actors.
     * @return a list of all Actor entities
     */
    protected List<Actor> getAllActors(){
        return actorRepository.getAll();
    }

//    protected int getActorSalary(Integer actorID){
//        return actorRepository.getByID(actorID).getSalary();
//    }

    /**
     * Updates the salary of an actor.
     * @param actorID the ID of the actor
     * @param newSalary the new salary amount
     */
    protected void changeActorSalary(Integer actorID, Integer newSalary){
        Actor actor = actorRepository.getByID(actorID);
        actor.setSalary(newSalary);
        actorRepository.update(actor);
    }

    /**
     * Retrieves a list of all shows in which the actor, identified by their email, has to appear.
     * @param eMail the email of the actor
     * @return a list of shows where the actor appears
     */
    protected List<Show> showMyShows(EMail eMail){
        List<Show> myShows = new ArrayList<>();
        List<Show> allShows = showRepository.getAll();
        List<Actor> actors = actorRepository.getAll();
        int actorID = 0;
        for (Actor actor : actors)
            if (actor.getEmail().equals(eMail)) {
                actorID = actor.getID();
                break;
            }

        for (Show show : allShows)
            if (show.getRoles().containsKey(getActor(actorID)))
                myShows.add(show);

        return myShows;
    }

    /**
     * Updates the name and age of an actor, optionally changing their email.
     * @param name the new name of the actor
     * @param age the new age of the actor
     * @param currentEmail the current email of the actor
     * @param newEmail the new email of the actor
     * @return true if the email was changed, false if only name and age were updated
     */
    protected boolean manageActorAccount(String name, int age, EMail currentEmail, EMail newEmail){
        Actor actor = getActor(currentEmail);
        actor.setName(name);
        actor.setAge(age);

        if (!actor.getEmail().equals(newEmail)) {
            actor.setEmail(newEmail);
            actorRepository.update(actor);
            return true;
        }
        actorRepository.update(actor);
        return false;
    }



    ////////////////////////////////SHOW////////////////////////////////
    /**
     * Retrieves the title of a show based on its ID.
     * @param showID the ID of the show
     * @return the title of the show
     */
    protected String getShowTitle(Integer showID){
        return showRepository.getByID(showID).getTitle();
    }

//    protected void changeShowTitle(Integer showID, String newTitle){
//        Show show = showRepository.getByID(showID);
//        show.setTitle(newTitle);
//        showRepository.update(show);
//    }
//
//    protected Map<Actor, String> getShowActors(Integer showID){
//        return showRepository.getByID(showID).getRoles();
//    }
//
//    protected void modifyShowActors(Integer showID, Map<Actor, String> roles){
//        Show Show = showRepository.getByID(showID);
//        Show.setRoles(roles);
//        showRepository.update(Show);
//    }

    /**
     * Retrieves the auditorium assigned to a show based on its ID.
     * @param showID the ID of the show
     * @return the auditorium where the show will be played
     */
    protected Auditorium getShowAuditorium(Integer showID){
        return showRepository.getByID(showID).getAudit();
    }

//    protected void changeShowAuditorium(Integer showID, Auditorium auditorium){
//        Show Show = showRepository.getByID(showID);
//        Show.setAudit(auditorium);
//        showRepository.update(Show);
//    }
//
//    protected String getShowDate(Integer showID){
//        return showRepository.getByID(showID).getDate();
//    }
//
//    protected void changeShowDate(Integer showID, String date){
//        Show Show = showRepository.getByID(showID);
//        Show.setDate(date);
//        showRepository.update(Show);
//    }

    /**
     * Retrieves a map of shows to their auditoriums based on the show title.
     * @param showTitle the title of the show
     * @return a map of shows to auditoriums where the show will be played
     */
    protected Map<Show, Auditorium> getShow(String showTitle){
        List<Show> shows = showRepository.getAll();
        List<Auditorium> audits= auditoriumRepository.getAll();
        Map<Show, Auditorium> showMap = new HashMap<>();
        for (Show show : shows)
            if (show.getTitle().equals(showTitle))
                for (Auditorium auditorium : audits)
                    if (auditorium.equals(show.getAudit()))
                        showMap.put(show, auditorium);
        return showMap;
    }

//    protected Show getShow(Integer showID){
//        return showRepository.getByID(showID);
//    }

    /**
     * Retrieves a list of all shows.
     * @return a list of all Show entities
     */
    protected List<Show> getAllShows(){
        return showRepository.getAll();
    }



    ////////////////////////////////AUDITORIUM////////////////////////////////
//    protected String getAuditName(Integer auditID){
//        return auditoriumRepository.getByID(auditID).getName();
//    }
//
//    protected void changeAuditName(Integer auditID, String newName){
//        Auditorium auditorium = auditoriumRepository.getByID(auditID);
//        auditorium.setName(newName);
//        auditoriumRepository.update(auditorium);
//    }
//
//    protected int getAuditCapacity(Integer auditID){
//        return auditoriumRepository.getByID(auditID).getCapacity();
//    }
//
//    protected void changeAuditCapacity(Integer auditID, int rows, int cols){
//        Auditorium auditorium = auditoriumRepository.getByID(auditID);
//        auditorium.setCapacity(rows, cols);
//        auditoriumRepository.update(auditorium);
//    }

    /**
     * Retrieves an auditorium based on its ID.
     * @param auditID the ID of the auditorium
     * @return the Auditorium entity with the specified ID
     */
    protected Auditorium getAuditorium(Integer auditID){
        return auditoriumRepository.getByID(auditID);
    }

    /**
     * Retrieves the auditorium based on the show ID.
     * @param showID the ID of the show
     * @return the auditorium in which the show will be played
     */
    protected Auditorium getAuditoriumByShowID(Integer showID){
        List<Show> shows = showRepository.getAll();
        Auditorium showsAuditorium = auditoriumRepository.getByID(1); // this is the default
        for (Show show : shows){
            if (show.getID().equals(showID))
                showsAuditorium = show.getAudit();
        }
        return showsAuditorium;
    }

    /**
     * Retrieves a list of all auditoriums.
     * @return a list of all Auditorium entities
     */
    protected List<Auditorium> getAllAuditoriums(){
        return auditoriumRepository.getAll();
    }



    ////////////////////////////////CEO////////////////////////////////
    /**
     * Creates a new CEO account if it does not already exist.
     * @param id the ID of the CEO
     * @param name the name of the CEO
     * @param age the age of the CEO
     * @param eMail the email of the CEO
     * @return true if the CEO account was created, false if it already exists
     */
    protected boolean createCeoAccount(Integer id, String name, int age, EMail eMail){
        Ceo newCeo = new Ceo(id, name, age, eMail);
        List<Ceo> ceos = ceoRepository.getAll();
        for (Ceo ceo : ceos)
            if (ceo.getEmail().equals(eMail) || ceo.getID().equals(id))
                return false;

        ceoRepository.create(newCeo);
        return true;
    }

//    protected String getCeoName(Integer ceoID){
//        return ceoRepository.getByID(ceoID).getName();
//    }
//
//    protected int getCeoAge(Integer ceoID){
//        return ceoRepository.getByID(ceoID).getAge();
//    }
//
//    protected EMail getCeoEmail(Integer ceoID){
//        return ceoRepository.getByID(ceoID).getEmail();
//    }

    /**
     * Hires a new actor if they do not already exist.
     * @param actorID the ID of the actor
     * @param name the name of the actor
     * @param age the age of the actor
     * @param actorEmail the email of the actor
     * @param salary the salary of the actor
     * @return true if the actor was hired, false if they already exist
     */
    protected boolean hireActor(Integer actorID, String name, int age, EMail actorEmail, int salary){
        List<Actor> actors = actorRepository.getAll();
        for (Actor actor : actors)
            if (actor.getID().equals(actorID) || actor.getEmail().equals(actorEmail))
                return false;

        Actor actor = new Actor(actorID, name, age, actorEmail, salary);
        actorRepository.create(actor);
        return true;
    }

    /**
     * Deletes an actor from the repository.
     * @param actorID the ID of the actor to delete
     */
    protected void fireActor(Integer actorID){
        actorRepository.delete(actorID);
    }

    /**
     * Creates a new show if the specified show ID does not already exist.
     * @param showID the unique ID for the show
     * @param name the name of the show
     * @param auditorium the auditorium where the show will be performed
     * @param roles a map linking each actor to their role in the show
     * @param date the date of the show
     * @return true if the show was successfully created, false if a show with the specified ID already exists
     */
    protected boolean createShow(Integer showID, String name, Auditorium auditorium, Map<Actor, String> roles, String date){
        List<Show> shows = showRepository.getAll();
        for (Show show : shows)
            if (show.getID().equals(showID))
                return false;

        Show Show = new Show(showID, name, auditorium, roles, date);
        showRepository.create(Show);
        return true;
    }

    /**
     * Deletes a show from the repository.
     * @param showID the ID of the show to delete
     */
    protected void deleteShow(Integer showID){
        showRepository.delete(showID);
    }

    /**
     * Creates a new auditorium if an auditorium with the specified ID does not already exist.
     * @param id the unique ID of the auditorium
     * @param name the name of the auditorium
     * @param rows the number of rows in the auditorium seating arrangement
     * @param cols the number of columns in the auditorium seating arrangement
     * @return true if the auditorium was created, false if an auditorium with the specified ID already exists
     */
    protected boolean createAuditorium(Integer id,String name, int rows, int cols){
        List<Auditorium> auditoriums = auditoriumRepository.getAll();
        for (Auditorium auditorium : auditoriums)
            if(auditorium.getID().equals(id))
                return false;
        Auditorium auditorium = new Auditorium(id, name, rows, cols);
        auditoriumRepository.create(auditorium);
        return true;
    }

    /**
     * Deletes an auditorium from the repository.
     * @param id the ID of the auditorium to delete
     */
    protected void deleteAuditorium(Integer id){
        auditoriumRepository.delete(id);
    }

    /**
     * Retrieves a CEO based on their unique ID.
     * @param ceoID the ID of the CEO
     * @return the CEO object if found, or null if not found
     */
    protected Ceo getCeo(Integer ceoID){
        return ceoRepository.getByID(ceoID);
    }

    /**
     * Retrieves a CEO based on their email address.
     * @param eMail the email address of the CEO
     * @return the CEO object if found, or null if not found
     */
    protected Ceo getCeo(EMail eMail){
        List<Ceo> ceos = ceoRepository.getAll();
        for (Ceo ceo : ceos)
            if (ceo.getEmail().equals(eMail))
                return ceo;
        return null;
    }

//    protected List<Ceo> getAllCeos(){
//        return ceoRepository.getAll();
//    }

    /**
     * Changes the details of a CEO (name, age, and optionally email). If the email is changed, returns true.
     * @param name the new name of the CEO
     * @param age the new age of the CEO
     * @param currentEmail the CEO's current email address
     * @param newEmail the new email address, if changing
     * @return true if the email was updated, false if only name and age were updated
     */
    protected boolean manageCeoAccount(String name, int age, EMail currentEmail, EMail newEmail) {
        Ceo ceo = getCeo(currentEmail);
        ceo.setName(name);
        ceo.setAge(age);

        if (!ceo.getEmail().equals(newEmail)) {
            ceo.setEmail(newEmail);
            ceoRepository.update(ceo);
            return true;
        }
        ceoRepository.update(ceo);
        return false;
    }



    ////////////////////////////////VIEWER////////////////////////////////
    /**
     * Retrieves the name of a viewer based on their unique ID.
     * @param viewerID the ID of the viewer
     * @return the name of the viewer
     */
    protected String getViewerName(Integer viewerID){
        return viewerRepository.getByID(viewerID).getName();
    }

    /**
     * Retrieves the unique ID of a viewer based on their email address.
     * @param eMail the email address of the viewer
     * @return the viewer's unique ID, or 0 if the viewer is not found
     */
    protected int getViewerID(EMail eMail){
        List<Viewer> viewers = viewerRepository.getAll();
        for (Viewer viewer : viewers)
            if (viewer.getEmail().equals(eMail))
                return viewer.getID();
        return 0;
    }

//    protected  void changeViewerName(Integer viewerID, String newName){
//        Viewer viewer = viewerRepository.getByID(viewerID);
//        viewer.setName(newName);
//        viewerRepository.update(viewer);
//    }
//
//    protected int getViewerAge(Integer viewerID){
//        return viewerRepository.getByID(viewerID).getAge();
//    }
//
//    protected void changeViewerAge(Integer viewerID, Integer newAge){
//        Viewer viewer = viewerRepository.getByID(viewerID);
//        viewer.setAge(newAge);
//        viewerRepository.update(viewer);
//    }
//
//    protected EMail getViewerEmail(Integer viewerID){
//        return viewerRepository.getByID(viewerID).getEmail();
//    }
//
//    protected void changeViewerEmail(Integer viewerID, EMail newEmail){
//        Viewer viewer = viewerRepository.getByID(viewerID);
//        viewer.setEmail(newEmail);
//        viewerRepository.update(viewer);
//    }

    /**
     * Retrieves a Viewer object based on their unique ID.
     * @param viewerID the ID of the viewer
     * @return the Viewer object, or null if not found
     */
    protected Viewer getViewer(Integer viewerID){
        return viewerRepository.getByID(viewerID);
    }

    /**
     * Retrieves a Viewer object based on their email address.
     * @param eMail the email address of the viewer
     * @return the Viewer object, or null if not found
     */
    protected Viewer getViewer(EMail eMail){
        List<Viewer> viewers = viewerRepository.getAll();
        for (Viewer viewer : viewers)
            if (viewer.getEmail().equals(eMail))
                return viewer;
        return null;
    }

    /**
     * Retrieves a Person (Actor, Viewer, or CEO) based on their email address.
     * @param eMail the email address of the person
     * @return the Person object if found, or null if not found
     */
    protected Person getAccount(EMail eMail){
        List<Viewer> viewers = viewerRepository.getAll();
        List<Ceo> ceos = ceoRepository.getAll();
        List<Actor> actors = actorRepository.getAll();
        for (Viewer viewer : viewers)
            if(viewer.getEmail().equals(eMail))
                return viewer;
        for (Ceo ceo : ceos)
            if(ceo.getEmail().equals(eMail))
                return ceo;
        for (Actor actor : actors)
            if(actor.getEmail().equals(eMail))
                return actor;
        return null;
    }

//    protected List<Viewer> getAllViewers(){
//        return viewerRepository.getAll();
//    }

    /**
     * Creates a new order for a viewer if the order ID does not already exist and if the selected seats are available.
     * @param id the unique ID for the order
     * @param showID the ID of the show
     * @param eMail the email address of the viewer making the order
     * @param seats the list of seats to be reserved
     * @return true if the order was created, false if the order ID exists or seats are unavailable
     */
    protected boolean createOrder(Integer id, int showID, EMail eMail, List<Integer> seats){
        List<Order> orders = orderRepository.getAll();
        for (Order order : orders)
            if (order.getID().equals(id))
                return false;

        int lower = 10000;
        int upper = 99999;
        int totalPrice= 50*seats.size();
        LocalDateTime currentTime = LocalDateTime.now();
        List<Viewer> viewers = viewerRepository.getAll();
        int viewerID = 0;
        for (Viewer viewer : viewers) {
            if (viewer.getEmail().equals(eMail)) {
                viewerID = viewer.getID();
            }
        }

        boolean ok = false;
        List<Show> shows = showRepository.getAll();
        for (Show show : shows)
            if (show.getID().equals(showID)) {
                ok = true;
                break;
            }
        if (!ok)
            return false;

        String viewerName = getViewerName(viewerID);
        String showTitle = getShowTitle(showID);
        Auditorium audit = getShowAuditorium(showID);
        String auditName = getShowAuditorium(showID).getName();

        if(!checkIfSeatFree(showID, seats))
            return false;

        List<Ticket> tickets = new ArrayList<>();
        for (int seat : seats) {
            Random random = new Random();
            int ticketID = random.nextInt(upper - lower + 1) + lower;
            Ticket ticket = new Ticket(ticketID, showTitle, viewerName, auditName, totalPrice/seats.size(), seat);
            tickets.add(ticket);

            int row = seat / audit.getCols();
            int col = seat % audit.getCols();
            if (row >= 0 && row < audit.getRows() && col >= 0 && col < audit.getCols())
                audit.getSeatPlace()[row][col] = false;
        }
        Order order = new Order(id, currentTime, viewerID, showID, seats, tickets);
        orderRepository.create(order);
        return true;
    }

    /**
     * Checks if the specified seats are free in the auditorium for a given show.
     * @param showID the ID of the show
     * @param seats the list of seat numbers to check
     * @return true if all seats are free, false if any seat is occupied
     */
    protected boolean checkIfSeatFree(int showID, List<Integer> seats) {
        Auditorium auditorium = getShowAuditorium(showID);

        int cols = auditorium.getCols();

        for (int seat : seats) {
            int row = seat / cols;
            int col = seat % cols;

            if (!auditorium.getSeatPlace()[row][col])
                return false;
        }
        return true;
    }

    /**
     * Retrieves a list of all orders for a viewer based on their email address.
     * @param eMail the email address of the viewer
     * @return a list of Order objects for the specified viewer; an empty list if no orders are found
     */
    protected List<Order> showMyOrders(EMail eMail){
        int viewerID = getViewerID(eMail);
        List<Order> myOrders = new ArrayList<>();
        List<Order> orders = orderRepository.getAll();
        for (Order order : orders)
            if (order.getViewerID() == viewerID)
                myOrders.add(order);
        return myOrders;
    }

//    protected void deleteOrder(Integer orderID){
//        orderRepository.delete(orderID);
//    }

    /**
     * Updates a viewer's name and age and optionally their email.
     * If the email is changed, the method returns true; otherwise, false.
     * @param name the new name of the viewer
     * @param age the new age of the viewer
     * @param currentEmail the viewer's current email
     * @param newEmail the viewer's new email
     * @return true if the email was updated, false if only name and age were updated
     */
    protected boolean manageViewerAccount(String name, int age, EMail currentEmail, EMail newEmail) {
        Viewer viewer = getViewer(currentEmail);
        viewer.setName(name);
        viewer.setAge(age);

        if (!viewer.getEmail().equals(newEmail)) {
            viewer.setEmail(newEmail);
            viewerRepository.update(viewer);
            return true;
        }
        viewerRepository.update(viewer);
        return false;
    }



    ////////////////////////////////EMAIL////////////////////////////////
    /**
     * Determines the role associated with the provided email.
     * @param eMail the email to check
     * @return an integer representing the role (1-Actor, 2-CEO, 3-Viewer, or 0 if email does not exist)
     */
    protected int loginAndGiveBackRole(EMail eMail) {
        List<Actor> actors = actorRepository.getAll();
        List<Ceo> ceos = ceoRepository.getAll();
        List<Viewer> viewers = viewerRepository.getAll();
        for (Actor actor : actors)
            if (actor.getEmail().equals(eMail))
                return 1;

        for (Ceo ceo : ceos)
            if (ceo.getEmail().equals(eMail))
                return 2;

        for (Viewer viewer : viewers)
            if (viewer.getEmail().equals(eMail))
                return 3;
        return 0;
    }

    /**
     * Creates a new Viewer account, if it does not already exist.
     * @param id the unique ID for the viewer
     * @param name the name of the viewer
     * @param age the age of the viewer
     * @param eMail the email address of the viewer
     * @return true if the viewer account was created, false if the viewer already exists
     */
    protected boolean createViewerAccount(Integer id, String name, int age, EMail eMail){
        Viewer newViewer = new Viewer(id, name, age, eMail);
        List<Viewer> viewers = viewerRepository.getAll();
        for (Viewer viewer: viewers)
            if (viewer.getEmail().equals(eMail) || viewer.getID().equals(id))
                return false;
        viewerRepository.create(newViewer);
        return true;
    }
}