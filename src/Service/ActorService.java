package Service;

import Repository.Repository;
import Domain.*;
import java.util.List;

/**
 * Service class to manage actor-related operations.
 */
public class ActorService {
    private final Repository<Actor> actorRepository;

    /**
     * Constructs an ActorService with the specified repository.
     * @param actorRepository The repository for managing Actor objects.
     */
    public ActorService(Repository<Actor> actorRepository) {
        this.actorRepository = actorRepository;
    }

    /**
     * Retrieves an actor by their unique ID.
     * @param id The unique ID of the actor.
     * @return The Actor object if found; otherwise, null.
     */
    public Actor getActor(Integer id) {
        return actorRepository.getByID(id);
    }

    /**
     * Retrieves all actors.
     * @return A list of all Actor objects.
     */
    public List<Actor> getAllActors() {
        return actorRepository.getAll();
    }

    /**
     * Retrieves the ID of an actor by their email.
     * @param eMail The email address of the actor.
     * @return The ID of the actor if found; otherwise, null.
     */
    public Integer getActorID(EMail eMail) {
        for (Actor actor : getAllActors())
            if (actor.getEmail().equals(eMail))
                return actor.getID();
        return null;
    }

    /**
     * Creates a new actor.

     * @param name The name of the actor.
     * @param age The age of the actor.
     * @param eMail The email address of the actor.
     * @param salary The salary of the actor.
     * @return True if the actor is successfully created; otherwise, false if an actor with the given ID already exists.
     */
    public boolean createActor(String name, int age, EMail eMail, int salary) {

        Actor newActor =  new Actor(name, age, eMail, salary);
        actorRepository.create(newActor);
        return true;
    }

    /**
     * Updates an existing actor's information.
     * @param id The unique ID of the actor to update.
     * @param name The new name of the actor.
     * @param age The new age of the actor.
     * @param eMail The new email address of the actor.
     * @return True if the actor is successfully updated; otherwise, false if an actor with the new email already exists.
     */
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

    /**
     * Deletes an actor by their unique ID.
     * @param id The unique ID of the actor to delete.
     * @return True if the actor is successfully deleted; otherwise, false if the actor does not exist.
     */
    public boolean deleteActor(Integer id) {
        if (getActor(id) == null)
            return false;
        actorRepository.delete(id);
        return true;
    }

    /**
     * Changes the salary of an actor.
     * @param id The unique ID of the actor whose salary is to be changed.
     * @param newSalary The new salary for the actor.
     * @return True if the salary is successfully updated; otherwise, false if the actor does not exist.
     */
    public boolean changeSalary(Integer id, int newSalary) {
        if (getActor(id) == null)
            return false;
        Actor actor = getActor(id);
        actor.setSalary(newSalary);
        actorRepository.update(actor);
        return true;
    }
}
