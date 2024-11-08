package ControllerService;

import Domain.*;
import RepositoryPackage.Repository;

import java.util.List;
import java.util.Map;

public class TheatreService {
    private final Repository<Ceo> ceoRepository;
    private final Repository<Actor> actorRepository;
    private final Repository<Auditorium> auditoriumRepository;
    private final Repository<Show> showRepository;
    private final Repository<Viewer> viewerRepository;
    private final Repository<Ticket> ticketRepository;
    private final Repository<Order> orderRepository;
    private final Repository<Seat> seatRepository;


    public TheatreService(Repository<Ceo> ceoRepository, Repository<Actor> actorRepository,
                          Repository<Auditorium> auditoriumRepository, Repository<Show> showRepository,
                          Repository<Viewer> viewerRepository, Repository<Ticket> ticketRepository, Repository<Order> orderRepository, Repository<Seat> seatRepository) {
        this.ceoRepository = ceoRepository;
        this.actorRepository = actorRepository;
        this.auditoriumRepository = auditoriumRepository;
        this.showRepository = showRepository;
        this.viewerRepository = viewerRepository;
        this.ticketRepository = ticketRepository;
        this.orderRepository = orderRepository;
        this.seatRepository = seatRepository;
    }

    ////////////////////////////////ACTOR////////////////////////////////
    protected String getActorName(Integer actorID){
        return actorRepository.getByID(actorID).getName();
    }

    protected void changeActorName(Integer actorID, String newName){
        Actor actor = actorRepository.getByID(actorID);
        actor.setName(newName);
        actorRepository.update(actor);
    }

    protected int getActorAge(Integer actorID){
        return actorRepository.getByID(actorID).getAge();
    }

    protected void changeActorAge(Integer actorID, int newAge){
        Actor actor = actorRepository.getByID(actorID);
        actor.setAge(newAge);
        actorRepository.update(actor);
    }

    protected EMail getActorEmail(Integer actorID){
        return actorRepository.getByID(actorID).getEmail();
    }

    protected void changeActorEmail(Integer actorID, EMail newEmail){
        Actor actor = actorRepository.getByID(actorID);
        actor.setEmail(newEmail);
        actorRepository.update(actor);
    }

    protected Actor getActor(Integer actorID){
        return actorRepository.getByID(actorID);
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

    protected Show getShow(Integer showID){
        return showRepository.getByID(showID);
    }

    protected List<Show> getAllShows(){
        return showRepository.getAll();
    }



    ////////////////////////////////AUDITORIUM////////////////////////////////
    protected String getAuditName(Integer showID){
        return auditoriumRepository.getByID(showID).getName();
    }

    protected void changeAuditName(Integer showID, String newName){
        Auditorium auditorium = auditoriumRepository.getByID(showID);
        auditorium.setName(newName);
        auditoriumRepository.update(auditorium);
    }

    protected int getAuditCapacity(Integer showID){
        return auditoriumRepository.getByID(showID).getCapacity();
    }

    protected void changeAuditCapacity(Integer showID, int rows, int cols){
        Auditorium auditorium = auditoriumRepository.getByID(showID);
        auditorium.setCapacity(rows, cols);
        auditoriumRepository.update(auditorium);
    }



    ////////////////////////////////CEO////////////////////////////////
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

    protected List<Ceo> getAllCeos(){
        return ceoRepository.getAll();
    }



    ////////////////////////////////VIEWER////////////////////////////////
    protected String getViewerName(Integer viewerID){
        return viewerRepository.getByID(viewerID).getName();
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

    protected List<Viewer> getAllViewers(){
        return viewerRepository.getAll();
    }

    protected void createOrder(int id, String date, int showID, List<Seat> seats, List<Ticket> tickets){
        Order order = new Order(id, date, showID, seats, tickets);
        orderRepository.create(order);
    }

    protected void deleteOrder(Integer orderID){
        orderRepository.delete(orderID);
    }



    ////////////////////////////////ORDER////////////////////////////////
    protected String getOrderDate(Integer orderID){
        return orderRepository.getByID(orderID).getDate();
    }

    protected int getOrderShowID(Integer orderID){
        return orderRepository.getByID(orderID).getShowID();
    }

    protected List<Ticket>  getOrderTickets(Integer orderID){
        return orderRepository.getByID(orderID).getTickets();
    }

    protected List<Seat> getOrderSeats(Integer orderID){
        return orderRepository.getByID(orderID).getSeats();
    }

    protected Order getOrder(Integer orderID){
        return orderRepository.getByID(orderID);
    }

    protected List<Order> getAllOrders(){
        return orderRepository.getAll();
    }



    ////////////////////////////////SEAT////////////////////////////////
    protected int getSeatRows(Integer seatID){
        return seatRepository.getByID(seatID).getRow();
    }

    protected int getSeatCols(Integer seatID){
        return seatRepository.getByID(seatID).getSeat();
    }

    protected void setSeatRow(Integer seatID, int row){
        Seat seat = seatRepository.getByID(seatID);
        seat.setRow(row);
        seatRepository.update(seat);
    }

    protected void setSeatCols(Integer seatID, int col){
        Seat seat = seatRepository.getByID(seatID);
        seat.setSeat(col);
        seatRepository.update(seat);
    }



    ////////////////////////////////TICKET////////////////////////////////
    protected String getTicketShowName(Integer ticketID){
        return ticketRepository.getByID(ticketID).getShowName();
    }

    protected String getTicketViewerName(Integer ticketID){
        return ticketRepository.getByID(ticketID).getViewerName();
    }

    protected String getTicketAuditoriumName(Integer ticketID){
        return ticketRepository.getByID(ticketID).getAuditoriumName();
    }

    protected int getTicketPrice(Integer ticketID){
        return ticketRepository.getByID(ticketID).getPrice();
    }

    protected Seat getTicketSeat(Integer ticketID){
        return ticketRepository.getByID(ticketID).getSeat();
    }

    protected Ticket getTicket(Integer ticketID){
        return ticketRepository.getByID(ticketID);
    }

    protected List<Ticket> getAllTickets(){
        return ticketRepository.getAll();
    }
}