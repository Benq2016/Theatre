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

    /**returns one Actor based on ID*/
    protected Actor getActor(Integer actorID){
        return actorRepository.getByID(actorID);
    }

    /**returns one Actor based on Email*/
    protected Actor getActor(EMail email){
        List<Actor> actors = actorRepository.getAll();
        for (Actor actor : actors)
            if (actor.getEmail().equals(email))
                return actor;
        return null;
    }

    /**returns a list of all Actors*/
    protected List<Actor> getAllActors(){
        return actorRepository.getAll();
    }

//    protected int getActorSalary(Integer actorID){
//        return actorRepository.getByID(actorID).getSalary();
//    }

    /**changes salary of an Actor*/
    protected void changeActorSalary(Integer actorID, Integer newSalary){
        Actor actor = actorRepository.getByID(actorID);
        actor.setSalary(newSalary);
        actorRepository.update(actor);
    }

    /**loads Actors and Show repositories, identifies the actorID using the Email and searches for the specific ID in the Show list*/
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

    /**changes the name, age of an Actor and returns false, else if the Email is changed too returns true*/
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
    /**returns the Show title based on an ID**/
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

    /**returns an Auditorium assigned to a Show based on its ID*/
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

    /**returns a Show-Auditorium pair for a specific Show based on its title(to see in what Auditoriums a Show will be played)*/
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

    /**returns a list of all Shows*/
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

    /**returns an Auditorium based on an ID*/
    protected Auditorium getAuditorium(Integer auditID){
        return auditoriumRepository.getByID(auditID);
    }

    /**returns an Auditorium based on the Show which will be played in it*/
    protected Auditorium getAuditoriumByShowID(Integer showID){
        List<Show> shows = showRepository.getAll();
        Auditorium showsAuditorium = auditoriumRepository.getByID(1); // this is the default
        for (Show show : shows){
            if (show.getID().equals(showID))
                showsAuditorium = show.getAudit();
        }
        return showsAuditorium;
    }

    /**returns a list of all Auditoriums*/
    protected List<Auditorium> getAllAuditoriums(){
        return auditoriumRepository.getAll();
    }



    ////////////////////////////////CEO////////////////////////////////
    /**returns true if the new Ceo Account was created and false if it already exists and cannot be created*/
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

    /**returns true if a new Actor was hired and false if it already exists and cannot be created*/
    protected boolean hireActor(Integer actorID, String name, int age, EMail actorEmail, int salary){
        List<Actor> actors = actorRepository.getAll();
        for (Actor actor : actors)
            if (actor.getID().equals(actorID) || actor.getEmail().equals(actorEmail))
                return false;

        Actor actor = new Actor(actorID, name, age, actorEmail, salary);
        actorRepository.create(actor);
        return true;
    }

    /**deletes an Actor from the repository*/
    protected void fireActor(Integer actorID){
        actorRepository.delete(actorID);
    }

    /**returns true if a new Show was created and false if it already exists and cannot be created*/
    protected boolean createShow(Integer showID, String name, Auditorium auditorium, Map<Actor, String> roles, String date){
        List<Show> shows = showRepository.getAll();
        for (Show show : shows)
            if (show.getID().equals(showID))
                return false;

        Show Show = new Show(showID, name, auditorium, roles, date);
        showRepository.create(Show);
        return true;
    }

    /**deletes a Show from the repository*/
    protected void deleteShow(Integer showID){
        showRepository.delete(showID);
    }

    /**returns true if a new Auditorium was created and false if it already exists and cannot be created*/
    protected boolean createAuditorium(Integer id,String name, int rows, int cols){
        List<Auditorium> auditoriums = auditoriumRepository.getAll();
        for (Auditorium auditorium : auditoriums)
            if(auditorium.getID().equals(id))
                return false;
        Auditorium auditorium = new Auditorium(id, name, rows, cols);
        auditoriumRepository.create(auditorium);
        return true;
    }

    /**deletes an Auditorium from the repository*/
    protected void deleteAuditorium(Integer id){
        auditoriumRepository.delete(id);
    }

    /**returns one Ceo based on its ID*/
    protected Ceo getCeo(Integer ceoID){
        return ceoRepository.getByID(ceoID);
    }

    /**returns one Ceo based on its Email*/
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

    /**changes the name, age of a Ceo and returns false, else if the Email is changed too returns true*/
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
    /**returns a Viewers name based on its ID*/
    protected String getViewerName(Integer viewerID){
        return viewerRepository.getByID(viewerID).getName();
    }

    /**returns a Viewers ID based on its Email*/
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

    /**returns one Viewer based on its ID*/
    protected Viewer getViewer(Integer viewerID){
        return viewerRepository.getByID(viewerID);
    }

    /**returns one Viewer based on its Email*/
    protected Viewer getViewer(EMail eMail){
        List<Viewer> viewers = viewerRepository.getAll();
        for (Viewer viewer : viewers)
            if (viewer.getEmail().equals(eMail))
                return viewer;
        return null;
    }

    /**returns a Person(Actor, Viewer, Ceo) based on its Email*/
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

    /**returns true if the Order was created and false if it already exists OR if the seats are already occupied and cannot be created*/
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

    /**returns true if the seat is free, false if its occupied*/
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

    /**returns a list of Orders for a Viewer based on its Email*/
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

    /**changes the name, age of a Viewer and returns false, else if the Email is changed too returns true*/
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
    /**returns a number which can be 1-Actor, 2-Ceo, 3-Viewer or 0-if the Email does not exist*/
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

    /**returns true if the Viewer was created and false if it hit an error*/
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