package ControllerService;

import Domain.*;

import java.util.Map;
import java.util.List;

public class TheatreController {
    private final TheatreService theatreService;

    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    public int login(EMail eMail) {
        return theatreService.loginAndGiveBackRole(eMail);
    }

    public boolean createViewerAccount(Integer id, String name, int age, EMail eMail) {
        return theatreService.createViewerAccount(id, name, age, eMail);
    }

    public boolean createCeoAccount(Integer id, String name, int age, EMail eMail) {
        return theatreService.createCeoAccount(id, name, age, eMail);
    }

    //////////VIEWER OPTIONS//////////
    public List<Show> viewShows() {
        return theatreService.getAllShows();
    }

    public Map<Show, Auditorium> viewShow(String showTitle) {
        return theatreService.getShow(showTitle);
    }

    public void createOrder(Integer id, int showID, EMail eMail, List<Integer> seats) {
        theatreService.createOrder(id, showID, eMail, seats);
    }

    public List<Order> viewMyOrders(EMail eMail) {
        return theatreService.showMyOrders(eMail);
    }

    public List<Ticket> viewOrderTickets(Integer orderID) {
        return theatreService.getOrderTickets(orderID);
    }

    public Person viewAccount(EMail eMail) {
        return theatreService.getAccount(eMail);
    }

    //// TRUE - EMail changes too, YOU HAVE TO BE LOGGED OUT!!, FALSE - ONLY NAME and AGE are changed ////
    public boolean manageViewerAccount(String name, int age, EMail currentEmail, EMail newEmail) {
        return theatreService.manageViewerAccount(name, age, currentEmail, newEmail);
    }

    //////////CEO OPTIONS//////////
    public void ceoHireActor(Integer actorID, String name, int age, EMail actorEmail, int salary) {
        theatreService.hireActor(actorID, name, age, actorEmail, salary);
    }

    public void ceoFireActor(Integer actorID) {
        theatreService.fireActor(actorID);
    }

    public void ceoChangeSalary(Integer actorID, int newSalary) {
        theatreService.changeActorSalary(actorID, newSalary);
    }

    public List<Actor> viewAllActors(){
        return theatreService.getAllActors();
    }

    public Actor viewActor(Integer actorID) {
        return theatreService.getActor(actorID);
    }

    public List<Auditorium> viewAllAuditoriums(){
        return theatreService.getAllAuditoriums();
    }

//    public List<Auditorium> viewAllAuditoriumsWithoutLayout(){
//        return theatreService.getAllAuditoriums();
//    }

    public Auditorium viewAuditorium(Integer auditoriumID) {
        return theatreService.getAuditorium(auditoriumID);
    }

    public void createShow(Integer showID, String title, Auditorium auditorium, Map<Actor, String> roles, String date) {
        theatreService.createShow(showID, title, auditorium, roles, date);
    }

    public void ceoDeleteShow(Integer showID) {
        theatreService.deleteShow(showID);
    }

    public void createAuditorium(Integer id,String name, int rows, int cols){
        theatreService.createAuditorium(id, name, rows, cols);
    }

    public void deleteAuditorium(Integer id){
        theatreService.deleteAuditorium(id);
    }

    public boolean manageCeoAccount(String name, int age, EMail currentEmail, EMail newEmail){
        return theatreService.manageCeoAccount(name, age, currentEmail, newEmail);
    }

    public List<Show> viewMyShows(EMail eMail) {
        return theatreService.showMyShows(eMail);
    }

    public boolean manageActorAccount(String name, int age, EMail currentEmail, EMail newEmail){
        return theatreService.manageActorAccount(name, age, currentEmail, newEmail);
    }
}