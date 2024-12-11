package Service;

import Exceptions.EntityNotFoundException;
import Exceptions.ValidationException;
import Repository.Repository;
import Domain.*;

import java.net.Inet4Address;
import java.util.List;

/**
 * Service class to manage auditorium-related operations.
 */
public class AuditoriumService {
    private final Repository<Auditorium> auditoriumRepository;

    /**
     * Constructs an AuditoriumService with the specified repository.
     * @param auditoriumRepository The repository for managing Auditorium objects.
     */
    public AuditoriumService(Repository<Auditorium> auditoriumRepository) {
        this.auditoriumRepository = auditoriumRepository;
    }

    /**
     * Retrieves an auditorium by its unique ID.
     * @param id The unique ID of the auditorium.
     * @return The Auditorium object if found; otherwise, null.
     */
    public Auditorium getAuditorium(Integer id) {
        return auditoriumRepository.getByID(id);
    }

    /**
     * Retrieves an auditorium by its name.
     * @param name The name of the auditorium to retrieve.
     * @return The Auditorium object with the specified name, or {@code null} if no auditorium is found.
     */
    public Auditorium getAuditoriumByName(String name) {
        List<Auditorium> auditoriums = auditoriumRepository.getAll();
        for (Auditorium auditorium : auditoriums) {
            if (auditorium.getName().equals(name))
                return auditorium;
        }
        return null;
    }

    /**
     * Retrieves all auditoriums.
     * @return A list of all Auditorium objects.
     */
    public List<Auditorium> getAllAuditoriums() {
        return auditoriumRepository.getAll();
    }

    /**
     * Creates a new auditorium and adds it to the repository.
     * @param name The name of the auditorium.
     * @param rows The number of rows in the auditorium.
     * @param cols The number of columns in the auditorium.
     * @return The newly created Auditorium object.
     */
    public Auditorium createAuditorium(String name, int rows, int cols) {
//        if (getAuditoriumByName(name) != null)
//            throw new ValidationException("Auditorium with this name already exists!");
        Auditorium auditorium = new Auditorium(name, rows, cols);
        auditoriumRepository.create(auditorium);
        return auditorium;
    }

    /**
     * Deletes an auditorium by its unique ID.
     * @param id The unique ID of the auditorium to delete.
     * @throws EntityNotFoundException If no auditorium with the specified ID exists.
     */
    public void deleteAuditorium(Integer id) {
        if (getAuditorium(id) == null)
            throw new EntityNotFoundException("Auditorium not found!");
        auditoriumRepository.delete(id);
    }
}