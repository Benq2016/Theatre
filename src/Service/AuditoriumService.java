package Service;

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
     * Retrieves all auditoriums.
     * @return A list of all Auditorium objects.
     */
    public List<Auditorium> getAllAuditoriums() {
        return auditoriumRepository.getAll();
    }

    /**
     * Creates a new auditorium.
     * @param id The unique ID of the auditorium.
     * @param name The name of the auditorium.
     * @param rows The number of rows in the auditorium.
     * @param cols The number of columns in the auditorium.
     * @return True if the auditorium is successfully created; otherwise, false if an auditorium with the given ID already exists.
     */
    public boolean createAuditorium(Integer id, String name, int rows, int cols) {
        if (getAuditorium(id) != null)
            return false;
        Auditorium auditorium = new Auditorium(id, name, rows, cols);
        auditoriumRepository.create(auditorium);
        return true;
    }

    /**
     * Deletes an auditorium by its unique ID.
     * @param id The unique ID of the auditorium to delete.
     * @return True if the auditorium is successfully deleted; otherwise, false if the auditorium does not exist.
     */
    public boolean deleteAuditorium(Integer id) {
        if (getAuditorium(id) == null)
            return false;
        auditoriumRepository.delete(id);
        return true;
    }
}
