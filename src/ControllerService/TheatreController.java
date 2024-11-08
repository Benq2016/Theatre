package ControllerService;

import Domain.Actor;
import Domain.Auditorium;
import Domain.EMail;

import java.util.Map;

public class TheatreController {
    private final TheatreService theatreService;

    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
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
}
