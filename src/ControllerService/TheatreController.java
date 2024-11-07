package ControllerService;

import Domain.EMail;

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
        theatreService.adjustSalary(actorID, newSalary);
    }
}
