package ControllerService;

import Domain.*;

import java.util.Map;
import java.util.List;

public class TheatreController {
    private final TheatreService theatreService;

    /**creates a Service instance, which will later be used to access the Service*/
    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    ////////GENERAL OPTIONS////////
    /**returns a number which can be 1-Actor, 2-Ceo, 3-Viewer or 0-if the Email does not exist*/
    public int login(EMail eMail) {
        return theatreService.loginAndGiveBackRole(eMail);
    }

    /**returns true if the Account was created and false if it hit an error*/
    public boolean createViewerAccount(Integer id, String name, int age, EMail eMail) {
        return theatreService.createViewerAccount(id, name, age, eMail);
    }

    /**returns true if the Account was created and false if it hit an error*/
    public boolean createCeoAccount(Integer id, String name, int age, EMail eMail) {
        return theatreService.createCeoAccount(id, name, age, eMail);
    }

    //////////VIEWER OPTIONS//////////
    /**returns a list of all Shows available*/
    public List<Show> viewShows() {
        return theatreService.getAllShows();
    }

    /**returns a map where for each Show the auditorium will also be shown*/
    public Map<Show, Auditorium> viewShow(String showTitle) {
        return theatreService.getShow(showTitle);
    }

    /**returns true if the Order was created and false if it hit an error*/
    public boolean createOrder(Integer id, int showID, EMail eMail, List<Integer> seats) {
        return theatreService.createOrder(id, showID, eMail, seats);
    }

    /**returns a list of Orders for a specific Viewer*/
    public List<Order> viewMyOrders(EMail eMail) {
        return theatreService.showMyOrders(eMail);
    }

    /**returns an object Person(Actor, Viewer or Ceo) if it found the email, null if it did not*/
    public Person viewAccount(EMail eMail) {
        return theatreService.getAccount(eMail);
    }

    /**returns true if the Email also was changed to a Viewer, false if only the name and age where changed*/
    public boolean manageViewerAccount(String name, int age, EMail currentEmail, EMail newEmail) {
        return theatreService.manageViewerAccount(name, age, currentEmail, newEmail);
    }

    //////////CEO OPTIONS//////////
    /**returns true if the Account was created and false if it hit an error*/
    public boolean ceoHireActor(Integer actorID, String name, int age, EMail actorEmail, int salary) {
        return theatreService.hireActor(actorID, name, age, actorEmail, salary);
    }

    /**deletes an Actor account based on its ID*/
    public void ceoFireActor(Integer actorID) {
        theatreService.fireActor(actorID);
    }

    /**changes the salary of an Actor*/
    public void ceoChangeSalary(Integer actorID, int newSalary) {
        theatreService.changeActorSalary(actorID, newSalary);
    }

    /**returns a list of all Actors*/
    public List<Actor> viewAllActors(){
        return theatreService.getAllActors();
    }

    /**returns one Actor with the ID given*/
    public Actor viewActor(Integer actorID) {
        return theatreService.getActor(actorID);
    }

    /**returns a list of all Auditoriums*/
    public List<Auditorium> viewAllAuditoriums(){
        return theatreService.getAllAuditoriums();
    }

//    public List<Auditorium> viewAllAuditoriumsWithoutLayout(){
//        return theatreService.getAllAuditoriums();
//    }

    /**returns one Auditorium with the ID given*/
    public Auditorium viewAuditorium(Integer auditoriumID) {
        return theatreService.getAuditorium(auditoriumID);
    }

    /**returns one Auditorium based on the Show to which it was selected*/
    public Auditorium getAuditoriumByShowId(Integer showId){
        return theatreService.getAuditoriumByShowID(showId);
    }

    /**returns true if the Show was created and false if it hit an error*/
    public boolean createShow(Integer showID, String title, Auditorium auditorium, Map<Actor, String> roles, String date) {
        return theatreService.createShow(showID, title, auditorium, roles, date);
    }

    /**deletes a Show based on its ID*/
    public void ceoDeleteShow(Integer showID) {
        theatreService.deleteShow(showID);
    }

    /**returns true if the Auditorium was created and false if it hit an error*/
    public boolean createAuditorium(Integer id,String name, int rows, int cols){
        return theatreService.createAuditorium(id, name, rows, cols);
    }

    /**deletes an Auditorium based on its ID*/
    public void deleteAuditorium(Integer id){
        theatreService.deleteAuditorium(id);
    }

    /**returns true if the Email also was changed to a Viewer, false if only the name and age where changed*/
    public boolean manageCeoAccount(String name, int age, EMail currentEmail, EMail newEmail){
        return theatreService.manageCeoAccount(name, age, currentEmail, newEmail);
    }


    //////////ACTOR//////////
    /**returns a list of all Show where the Actor has to appear*/
    public List<Show> viewMyShows(EMail eMail) {
        return theatreService.showMyShows(eMail);
    }

    /**returns true if the Email also was changed to a Viewer, false if only the name and age where changed*/
    public boolean manageActorAccount(String name, int age, EMail currentEmail, EMail newEmail){
        return theatreService.manageActorAccount(name, age, currentEmail, newEmail);
    }
}