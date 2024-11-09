package ControllerService;

import Domain.*;

import java.util.Map;
import java.util.List;

public class TheatreController {
    private final TheatreService theatreService;

    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    public Actor getActor(Integer actorID) {
        return theatreService.getActor(actorID);
    }

    public void ceoHireActor(Integer actorID, String name, int age, EMail actorEmail, int salary){
        theatreService.hireActor(actorID,name,age,actorEmail,salary);
    }

    public void ceoFireActor(Integer actorID){
        theatreService.fireActor(actorID);
    }

    public void ceoChangeSalary(Integer actorID, int newSalary){
        theatreService.changeActorSalary(actorID, newSalary);
    }

    public void ceoCreateShow(Integer showID, String name, Auditorium auditorium, Map<Actor, String> roles, String date) {
        theatreService.createShow(showID, name, auditorium, roles, date);
    }

    public void ceoDeleteShow(Integer showID){
        theatreService.deleteShow(showID);
    }

    public int login(EMail eMail){
       return theatreService.login(eMail);
    }

    public boolean createAccount(Integer id, String name, int age, EMail eMail){
        return theatreService.createViewerAccount(id, name, age, eMail);
    }

    //////////VIEWER OPTIONS//////////
    public List<Show> viewShows(){
        return theatreService.getAllShows();
    }

    public Map<Show, Auditorium> viewShow(String showTitle) {
        return theatreService.getShow(showTitle);
    }

    public void createOrder(Integer id, String date, int showID, EMail eMail, List<Seat> seats, List<Ticket> tickets){
        theatreService.createOrder(id, date, showID, eMail, seats, tickets);
    }
}
