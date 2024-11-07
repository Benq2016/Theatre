import Domain.*;
import RepositoryPackage.Repository;

import java.util.List;

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

    public void hireActor(Integer actorID, String name, int age, EMail actorEmail, int salary){
        Actor newActor = new Actor(actorID, name, age, actorEmail, salary);
        actorRepository.create(newActor);
    }

    public void fireActor(Integer actorID){
        actorRepository.delete(actorID);
    }

}
