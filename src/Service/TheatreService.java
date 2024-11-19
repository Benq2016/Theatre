package Service;

import Domain.*;

import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;

public class TheatreService {
    private final AdminService adminService;
    private final ActorService actorService;
    private final AuditoriumService auditoriumService;
    private final ShowService showService;
    private final ViewerService viewerService;
    private final OrderService orderService;


    public TheatreService(AdminService adminService, ActorService actorService, AuditoriumService auditoriumService, ShowService showService, ViewerService viewerService, OrderService orderService) {
        this.adminService = adminService;
        this.actorService = actorService;
        this.auditoriumService = auditoriumService;
        this.showService = showService;
        this.viewerService = viewerService;
        this.orderService = orderService;
    }


    ////////////////////////////////ACTOR////////////////////////////////
//    public String getActorName(Integer actorID){
//        return actorRepository.getByID(actorID).getName();
//    }
//
//    public int getActorAge(Integer actorID){
//        return actorRepository.getByID(actorID).getAge();
//    }
//
//    public EMail getActorEmail(Integer actorID){
//        return actorRepository.getByID(actorID).getEmail();
//    }
//
//    public int getActorSalary(Integer actorID){
//        return actorRepository.getByID(actorID).getSalary();
//    }
//
//
    ////////////////////////////SHOW////////////////////////////////
//    public void changeShowTitle(Integer showID, String newTitle){
//        Show show = showRepository.getByID(showID);
//        show.setTitle(newTitle);
//        showRepository.update(show);
//    }
//
//    public Map<Actor, String> getShowActors(Integer showID){
//        return showRepository.getByID(showID).getRoles();
//    }
//
//    public void modifyShowActors(Integer showID, Map<Actor, String> roles){
//        Show Show = showRepository.getByID(showID);
//        Show.setRoles(roles);
//        showRepository.update(Show);
//    }
//
//    public void changeShowAuditorium(Integer showID, Auditorium auditorium){
//        Show Show = showRepository.getByID(showID);
//        Show.setAudit(auditorium);
//        showRepository.update(Show);
//    }
//
//    public String getShowDate(Integer showID){
//        return showRepository.getByID(showID).getDate();
//    }
//
//    public void changeShowDate(Integer showID, String date){
//        Show Show = showRepository.getByID(showID);
//        Show.setDate(date);
//        showRepository.update(Show);
//    }

//    /**
//     * Retrieves a map of shows to their auditoriums based on the show title.
//     * @param showTitle the title of the show
//     * @return a map of shows to auditoriums where the show will be played
//     */
//    public Map<Show, Auditorium> getShow(String showTitle){
//        List<Show> shows = showRepository.getAll();
//        List<Auditorium> audits= auditoriumRepository.getAll();
//        Map<Show, Auditorium> showMap = new HashMap<>();
//        for (Show show : shows)
//            if (show.getTitle().equals(showTitle))
//                for (Auditorium auditorium : audits)
//                    if (auditorium.equals(show.getAudit()))
//                        showMap.put(show, auditorium);
//        return showMap;
//    }


    ////////////////////////////////AUDITORIUM////////////////////////////////
//    public String getAuditName(Integer auditID){
//        return auditoriumRepository.getByID(auditID).getName();
//    }
//
//    public void changeAuditName(Integer auditID, String newName){
//        Auditorium auditorium = auditoriumRepository.getByID(auditID);
//        auditorium.setName(newName);
//        auditoriumRepository.update(auditorium);
//    }
//
//    public int getAuditCapacity(Integer auditID){
//        return auditoriumRepository.getByID(auditID).getCapacity();
//    }
//
//    public void changeAuditCapacity(Integer auditID, int rows, int cols){
//        Auditorium auditorium = auditoriumRepository.getByID(auditID);
//        auditorium.setCapacity(rows, cols);
//        auditoriumRepository.update(auditorium);
//    }


    ////////////////////////////////CEO////////////////////////////////
//    public String getCeoName(Integer ceoID){
//        return ceoRepository.getByID(ceoID).getName();
//    }
//
//    public int getCeoAge(Integer ceoID){
//        return ceoRepository.getByID(ceoID).getAge();
//    }
//
//    public EMail getCeoEmail(Integer ceoID){
//        return ceoRepository.getByID(ceoID).getEmail();
//    }


    ////////////////////////////////VIEWER////////////////////////////////
//    public int getViewerAge(Integer viewerID){
//        return viewerRepository.getByID(viewerID).getAge();
//    }
//
//    public EMail getViewerEmail(Integer viewerID){
//        return viewerRepository.getByID(viewerID).getEmail();
//    }





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

    public List<Show> viewActorShows(Integer id) {
        List<Show> allShows = showService.getAllShows();
        List<Show> myShows = new ArrayList<>();

        for (Show show : allShows)
            if (show.getRoles().containsKey(actorService.getActor(id)))
                myShows.add(show);

        return myShows;
    }

    public List<Order> viewViewerOrders(Integer id) {
        List<Order> orders = orderService.getAllOrders();
        List<Order> myOrders = new ArrayList<>();

        for (Order order : orders)
            if (order.getViewerID().equals(id))
                myOrders.add(order);

        return myOrders;
    }

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

    public boolean createViewerAccount(Integer id, String name, int age, EMail eMail){
        return viewerService.createViewer(id, name, age, eMail);
    }

    public boolean manageViewerAccount(Integer id, String name, int age, EMail eMail) {
        return viewerService.updateViewer(id, name, age, eMail);
    }

    public boolean deleteViewerAccount(Integer id) {
        return viewerService.deleteViewer(id);
    }

    public boolean createAdminAccount(Integer id, String name, int age, EMail eMail){
        return adminService.createAdmin(id, name, age, eMail);
    }

    public boolean manageAdminAccount(Integer id, String name, int age, EMail eMail) {
        return adminService.updateAdmin(id, name, age, eMail);
    }

    public boolean deleteAdminAccount(Integer id) {
        return adminService.deleteAdmin(id);
    }

    public boolean createActorAccount(Integer id, String name, int age, EMail eMail, int salary){
        return actorService.createActor(id, name, age, eMail, salary);
    }

    public boolean manageActorAccount(Integer id, String name, int age, EMail eMail) {
        return actorService.updateActor(id, name, age, eMail);
    }

    public boolean deleteActorAccount(Integer id) {
        return actorService.deleteActor(id);
    }

    public boolean changeSalary(Integer id, int salary) {
        return actorService.changeSalary(id, salary);
    }

    public boolean createAuditorium(Integer id, String name, int rows, int cols){
        return auditoriumService.createAuditorium(id, name, rows, cols);
    }

    public boolean deleteAuditorium(Integer id) {
        return auditoriumService.deleteAuditorium(id);
    }

    public boolean createShow(Integer id, String title, Date date, Integer auditoriumID, Map<Actor, String> roles, int price) {
        if (showService.getShow(id) != null)
            return false;

        if (auditoriumService.getAuditorium(auditoriumID) == null)
            return false;

        Auditorium auditorium = auditoriumService.getAuditorium(auditoriumID);
        Show show = new Show(id, title, date, auditorium, roles, price);
        showService.createShow(show);
        return true;
    }

    public boolean deleteShow(Integer id) {
        return showService.deleteShow(id);
    }

    public boolean createOrder(Integer id, Integer viewerID, int showID, List<Integer> seats) {
        if (orderService.getOrder(id) != null)
            return false;

        if (viewerService.getViewer(viewerID) == null)
            return false;

        if (showService.getShow(showID) == null)
            return false;

        LocalDate date = LocalDateTime.now().toLocalDate();

        int price = showService.getShow(showID).getPrice();
        int totalPrice = price * seats.size();

        List<Ticket> tickets = createTickets(viewerID, showID, seats, price);

        if(!occupySeats(showID, seats))
            return false;

        Order order = new Order(id, date, viewerID, showID, seats, tickets, totalPrice);
        orderService.createOrder(order);
        return true;
    }

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

    public boolean occupySeats(Integer showID, List<Integer> seats) {
        Auditorium auditorium = showService.getShow(showID).getAudit();
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

    public int deleteOrder(Integer id) {
        Order order = orderService.getOrder(id);
        if (order == null)
            return 0;

        Show show = showService.getShow(order.getShowID());

        Auditorium auditorium = show.getAudit();

        List<Integer> seats = order.getSeats();

        releaseSeats(auditorium, seats);

        orderService.deleteOrder(id);

        return order.getTotalPrice();
    }

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
    public List<Actor> getActors() {
        return actorService.getAllActors();
    }

    public Actor getActor(Integer id) {
        return actorService.getActor(id);
    }

    public List<Viewer> getViewers() {
        return viewerService.getAllViewers();
    }

    public Viewer getViewer(Integer id) {
        return viewerService.getViewer(id);
    }

    public List<Admin> getAdmins() {
        return adminService.getAllAdmins();
    }

    public Admin getAdmin(Integer id) {
        return adminService.getAdmin(id);
    }

    public List<Auditorium> getAuditoriums() {
        return auditoriumService.getAllAuditoriums();
    }

    public Auditorium getAuditorium(Integer id) {
        return auditoriumService.getAuditorium(id);
    }

    public Auditorium getAuditoriumByShow(Integer id) {
        List<Show> shows = showService.getAllShows();
        for (Show show : shows)
            if (show.getID().equals(id))
                return show.getAudit();
        return null;
    }

    public List<Show> getShows() {
        return showService.getAllShows();
    }

    public Show getShow(Integer id) {
        return showService.getShow(id);
    }

    public List<Order> getOrders() {
        return orderService.getAllOrders();
    }

    public Order getOrder(Integer id) {
        return orderService.getOrder(id);
    }

    public List<Order> getOrdersSorted() {
        return orderService.getOrdersSorted();
    }

    public List<Order> getOrdersFiltered() {
        return orderService.getOrdersFiltered();
    }

    public List<Show> getShowsSorted() {
        return showService.getShowsSorted();
    }

    public List<Show> getShowsFiltered() {
        return showService.getShowsFiltered();
    }
}
