package ControllerService;

import Domain.*;
import RepositoryPackage.Repository;

public class TheatreService {
    private final Repository<Ceo> ceoRepository;
    private final Repository<Actor> actorRepository;
    private final Repository<Auditorium> auditoriumRepository;
    private final Repository<Show> showRepository;
    private final Repository<Viewer> viewerRepository;

    public TheatreService(Repository<Ceo> ceoRepository, Repository<Actor> actorRepository,
                          Repository<Auditorium> auditoriumRepository, Repository<Show> showRepository,
                          Repository<Viewer> viewerRepository) {
        this.ceoRepository = ceoRepository;
        this.actorRepository = actorRepository;
        this.auditoriumRepository = auditoriumRepository;
        this.showRepository = showRepository;
        this.viewerRepository = viewerRepository;
    }

    protected void hireActor(Integer actorID, String name, int age, EMail actorEmail, int salary){
        Actor newActor = new Actor(actorID, name, age, actorEmail, salary);
        actorRepository.create(newActor);
    }

    protected void fireActor(Integer actorID){
        actorRepository.delete(actorID);
    }

    protected void adjustSalary(Integer actorID, int newSalary){
        Actor tempActor = actorRepository.getByID(actorID);
        tempActor.setSalary(newSalary);
        actorRepository.update(tempActor);
    }

}
