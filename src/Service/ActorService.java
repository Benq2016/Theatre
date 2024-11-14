package Service;

import Repository.Repository;
import Domain.*;
import java.util.List;

public class ActorService {
    private final Repository<Actor> actorRepository;

    public ActorService(Repository<Actor> actorRepository) {
        this.actorRepository = actorRepository;
    }

    public Actor getActor(Integer id) {
        return actorRepository.getByID(id);
    }

    public List<Actor> getAllActors() {
        return actorRepository.getAll();
    }

    public Integer getActorID(EMail eMail) {
        for (Actor actor : getAllActors())
            if (actor.getEmail().equals(eMail))
                return actor.getID();
        return null;
    }

    public boolean createActor(Integer id, String name, int age, EMail eMail, int salary) {
        if (getActor(id) != null)
            return false;
        Actor newActor =  new Actor(id, name, age, eMail, salary);
        actorRepository.create(newActor);
        return true;
    }

    public boolean updateActor(Integer id, String name, int age, EMail eMail) {
        if (getActor(getActorID(eMail)) != null)
            return false;
        Actor actor = getActor(id);
        actor.setName(name);
        actor.setAge(age);
        actor.setEmail(eMail);
        actorRepository.update(actor);
        return true;
    }

    public boolean deleteActor(Integer id) {
        if (getActor(id) == null)
            return false;
        actorRepository.delete(id);
        return true;
    }

    public boolean changeSalary(Integer id, int newSalary) {
        if (getActor(id) == null)
            return false;
        Actor actor = getActor(id);
        actor.setSalary(newSalary);
        actorRepository.update(actor);
        return true;
    }
}
