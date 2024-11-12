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
    protected String getActorName(Integer actorID){
        return actorRepository.getByID(actorID).getName();
    }

    protected int getActorAge(Integer actorID){
        return actorRepository.getByID(actorID).getAge();
    }

    protected EMail getActorEmail(Integer actorID){
        return actorRepository.getByID(actorID).getEmail();
    }

    protected Actor getActor(Integer actorID){
        return actorRepository.getByID(actorID);
    }

    protected Actor getActor(EMail email){
        List<Actor> actors = actorRepository.getAll();
        for (Actor actor : actors)
            if (actor.getEmail().equals(email))
                return actor;
        return null;
    }

    protected List<Actor> getAllActors(){
        return actorRepository.getAll();
    }

    protected int getActorSalary(Integer actorID){
        return actorRepository.getByID(actorID).getSalary();
    }

    protected void changeActorSalary(Integer actorID, Integer newSalary){
        Actor actor = actorRepository.getByID(actorID);
        actor.setSalary(newSalary);
        actorRepository.update(actor);
    }

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
    protected String getShowTitle(Integer showID){
        return showRepository.getByID(showID).getTitle();
    }

    protected void changeShowTitle(Integer showID, String newTitle){
        Show show = showRepository.getByID(showID);
        show.setTitle(newTitle);
        showRepository.update(show);
    }

    protected Map<Actor, String> getShowActors(Integer showID){
        return showRepository.getByID(showID).getRoles();
    }

    protected void modifyShowActors(Integer showID, Map<Actor, String> roles){
        Show Show = showRepository.getByID(showID);
        Show.setRoles(roles);
        showRepository.update(Show);
    }

    protected Auditorium getShowAuditorium(Integer showID){
        return showRepository.getByID(showID).getAudit();
    }

    protected void changeShowAuditorium(Integer showID, Auditorium auditorium){
        Show Show = showRepository.getByID(showID);
        Show.setAudit(auditorium);
        showRepository.update(Show);
    }

    protected String getShowDate(Integer showID){
        return showRepository.getByID(showID).getDate();
    }

    protected void changeShowDate(Integer showID, String date){
        Show Show = showRepository.getByID(showID);
        Show.setDate(date);
        showRepository.update(Show);
    }

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

    protected Show getShow(Integer showID){
        return showRepository.getByID(showID);
    }

    protected List<Show> getAllShows(){
        return showRepository.getAll();
    }



    ////////////////////////////////AUDITORIUM////////////////////////////////
    protected String getAuditName(Integer auditID){
        return auditoriumRepository.getByID(auditID).getName();
    }

    protected void changeAuditName(Integer auditID, String newName){
        Auditorium auditorium = auditoriumRepository.getByID(auditID);
        auditorium.setName(newName);
        auditoriumRepository.update(auditorium);
    }

    protected int getAuditCapacity(Integer auditID){
        return auditoriumRepository.getByID(auditID).getCapacity();
    }

    protected void changeAuditCapacity(Integer auditID, int rows, int cols){
        Auditorium auditorium = auditoriumRepository.getByID(auditID);
        auditorium.setCapacity(rows, cols);
        auditoriumRepository.update(auditorium);
    }

    protected Auditorium getAuditorium(Integer auditID){
        return auditoriumRepository.getByID(auditID);
    }

    protected List<Auditorium> getAllAuditoriums(){
        return auditoriumRepository.getAll();
    }



    ////////////////////////////////CEO////////////////////////////////
    protected boolean createCeoAccount(Integer id, String name, int age, EMail eMail){
        Ceo newCeo = new Ceo(id, name, age, eMail);
        List<Ceo> ceos = ceoRepository.getAll();
        for (Ceo ceo : ceos)
            if (ceo.getEmail().equals(eMail) || ceo.getID().equals(id))
                return false;

        ceoRepository.create(newCeo);
        return true;
    }

    protected String getCeoName(Integer ceoID){
        return ceoRepository.getByID(ceoID).getName();
    }

    protected void changeCeoName(Integer ceoID, String newName){
        Ceo ceo = ceoRepository.getByID(ceoID);
        ceo.setName(newName);
        ceoRepository.update(ceo);
    }

    protected int getCeoAge(Integer ceoID){
        return ceoRepository.getByID(ceoID).getAge();
    }

    protected void changeCeoAge(Integer ceoID, Integer newAge){
        Ceo ceo = ceoRepository.getByID(ceoID);
        ceo.setAge(newAge);
        ceoRepository.update(ceo);
    }

    protected EMail getCeoEmail(Integer ceoID){
        return ceoRepository.getByID(ceoID).getEmail();
    }

    protected void changeCeoEmail(Integer ceoID, EMail newEmail){
        Ceo ceo = ceoRepository.getByID(ceoID);
        ceo.setEmail(newEmail);
        ceoRepository.update(ceo);
    }

    protected void hireActor(Integer actorID, String name, int age, EMail actorEmail, int salary){
        Actor actor = new Actor(actorID, name, age, actorEmail, salary);
        actorRepository.create(actor);
    }

    protected void fireActor(Integer actorID){
        actorRepository.delete(actorID);
    }

    protected void createShow(Integer showID, String name, Auditorium auditorium, Map<Actor, String> roles, String date){
        Show Show = new Show(showID, name, auditorium, roles, date);
        showRepository.create(Show);
    }

    protected void deleteShow(Integer showID){
        showRepository.delete(showID);
    }

    protected void createAuditorium(Integer id,String name, int rows, int cols){
        Auditorium auditorium = new Auditorium(id, name, rows, cols);
        auditoriumRepository.create(auditorium);
    }

    protected void deleteAuditorium(Integer id){
        auditoriumRepository.delete(id);
    }

    protected Ceo getCeo(Integer ceoID){
        return ceoRepository.getByID(ceoID);
    }

    protected Ceo getCeo(EMail eMail){
        List<Ceo> ceos = ceoRepository.getAll();
        for (Ceo ceo : ceos)
            if (ceo.getEmail().equals(eMail))
                return ceo;
        return null;
    }

    protected List<Ceo> getAllCeos(){
        return ceoRepository.getAll();
    }

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
    protected String getViewerName(Integer viewerID){
        return viewerRepository.getByID(viewerID).getName();
    }

    protected int getViewerID(EMail eMail){
        List<Viewer> viewers = viewerRepository.getAll();
        for (Viewer viewer : viewers)
            if (viewer.getEmail().equals(eMail))
                return viewer.getID();
        return 0;
    }

    protected  void changeViewerName(Integer viewerID, String newName){
        Viewer viewer = viewerRepository.getByID(viewerID);
        viewer.setName(newName);
        viewerRepository.update(viewer);
    }

    protected int getViewerAge(Integer viewerID){
        return viewerRepository.getByID(viewerID).getAge();
    }

    protected void changeViewerAge(Integer viewerID, Integer newAge){
        Viewer viewer = viewerRepository.getByID(viewerID);
        viewer.setAge(newAge);
        viewerRepository.update(viewer);
    }

    protected EMail getViewerEmail(Integer viewerID){
        return viewerRepository.getByID(viewerID).getEmail();
    }

    protected void changeViewerEmail(Integer viewerID, EMail newEmail){
        Viewer viewer = viewerRepository.getByID(viewerID);
        viewer.setEmail(newEmail);
        viewerRepository.update(viewer);
    }

    protected Viewer getViewer(Integer viewerID){
        return viewerRepository.getByID(viewerID);
    }

    protected Viewer getViewer(EMail eMail){
        List<Viewer> viewers = viewerRepository.getAll();
        for (Viewer viewer : viewers)
            if (viewer.getEmail().equals(eMail))
                return viewer;
        return null;
    }

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

    protected List<Viewer> getAllViewers(){
        return viewerRepository.getAll();
    }

    protected void createOrder(Integer id, int showID, EMail eMail, List<Integer> seats, int totalPrice){
        int lower = 10000;
        int upper = 99999;
        LocalDateTime currentTime = LocalDateTime.now();
        List<Viewer> viewers = viewerRepository.getAll();
        int viewerID = 0;
        for (Viewer viewer : viewers) {
            if (viewer.getEmail().equals(eMail)) {
                viewerID = viewer.getID();
            }
        }

        String viewerName = getViewerName(viewerID);
        String showTitle = getShowTitle(showID);
        Auditorium audit = getShowAuditorium(showID);
        String auditName = getShowAuditorium(showID).getName();

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
    }

    protected List<Order> showMyOrders(EMail eMail){
        int viewerID = getViewerID(eMail);
        List<Order> myOrders = new ArrayList<>();
        List<Order> orders = orderRepository.getAll();
        for (Order order : orders)
            if (order.getViewerID() == viewerID)
                myOrders.add(order);
        return myOrders;
    }

    protected void deleteOrder(Integer orderID){
        orderRepository.delete(orderID);
    }

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



    ////////////////////////////////ORDER////////////////////////////////
    protected LocalDateTime getOrderDate(Integer orderID){
        return orderRepository.getByID(orderID).getDate();
    }

    protected int getOrderViewerID(Integer orderID){
        return orderRepository.getByID(orderID).getViewerID();
    }

    protected int getOrderShowID(Integer orderID){
        return orderRepository.getByID(orderID).getShowID();
    }

    protected List<Ticket>  getOrderTickets(Integer orderID){
        return orderRepository.getByID(orderID).getTickets();
    }

    protected List<Integer> getOrderSeats(Integer orderID){
        return orderRepository.getByID(orderID).getSeats();
    }

    protected Order getOrder(Integer orderID){
        return orderRepository.getByID(orderID);
    }

    protected List<Order> getAllOrders(){
        return orderRepository.getAll();
    }



    ////////////////////////////////EMAIL////////////////////////////////
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