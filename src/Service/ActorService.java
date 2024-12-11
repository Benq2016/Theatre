package Service;

import Exceptions.UserExistenceException;
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
            if (actor.getEmail().getEmailAddress().equals(eMail.getEmailAddress()))
                return actor.getID();
        return null;
    }

    /**
     * Creates a new actor and adds it to the repository.
     * @param name The name of the actor.
     * @param age The age of the actor.
     * @param eMail The email address of the actor.
     * @param salary The salary of the actor.
     * @return The newly created Actor object.
     * @throws UserExistenceException If an actor with the same email address already exists.
     */
    public Actor createActor(String name, int age, EMail eMail, int salary) {
//        if (getActor(getActorID(eMail.)) != null)
//            throw new UserExistenceException("Email address already exists!");
        Actor newActor =  new Actor(name, age, eMail, salary);
        actorRepository.create(newActor);
        return newActor;
    }

    /**
     * Updates the details of an existing actor.
     * @param id The unique ID of the actor to update.
     * @param name The new name of the actor.
     * @param age The new age of the actor.
     * @param eMail The new email address of the actor.
     * @return The updated Actor object.
     * @throws UserExistenceException If an actor with the same email address already exists.
     */
    public Actor updateActor(Integer id, String name, int age, EMail eMail) {
        if (getActor(getActorID(eMail)) != null)
            throw new UserExistenceException("Email address already exists!");
        Actor actor = getActor(id);
        actor.setName(name);
        actor.setAge(age);
        actor.setEmail(eMail);
        actorRepository.update(actor);
        return actor;
    }

    /**
     * Deletes an actor by their unique ID.
     * @param id The unique ID of the actor to delete.
     * @throws UserExistenceException If no actor with the specified ID exists.
     */
    public void deleteActor(Integer id) {
        if (getActor(id) == null)
            throw new UserExistenceException("Actor does not exist!");
        actorRepository.delete(id);
    }

    /**
     * Changes the salary of an existing actor.
     * @param id The unique ID of the actor.
     * @param newSalary The new salary amount.
     * @throws UserExistenceException If no actor with the specified ID exists.
     */
    public void changeSalary(Integer id, int newSalary) {
        if (getActor(id) == null)
            throw new UserExistenceException("Actor does not exist!");
        Actor actor = getActor(id);
        actor.setSalary(newSalary);
        actorRepository.update(actor);
    }
}