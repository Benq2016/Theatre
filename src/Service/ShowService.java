package Service;

import Exceptions.EntityNotFoundException;
import Repository.Repository;
import Domain.*;

import java.util.*;
import java.time.ZoneId;
import java.util.stream.Collectors;

/**
 * Service class to manage show-related operations.
 */
public class ShowService {
    private final Repository<Show> showRepository;

    /**
     * Constructs a ShowService with the specified repository.
     * @param showRepository The repository for managing Show objects.
     */
    public ShowService(Repository<Show> showRepository) {
        this.showRepository = showRepository;
    }

    /**
     * Retrieves a show by its unique ID.
     * @param id The unique ID of the show.
     * @return The Show object if found; otherwise, null.
     */
    public Show getShow(Integer id) {
        return showRepository.getByID(id);
    }

    /**
     * Retrieves all shows.
     * @return A list of all Show objects.
     */
    public List<Show> getAllShows() {
        return showRepository.getAll();
    }

    /**
     * Retrieves all shows sorted by their date.
     * @return A list of Show objects sorted by date.
     */
    public List<Show> getShowsSorted() {
        List<Show> shows = showRepository.getAll();
        List<Show> mutableShows = new ArrayList<>(shows);

        mutableShows.sort(Comparator.comparing(show -> show.getDate().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate()));
        return mutableShows;
    }

    /**
     * Retrieves all shows that are scheduled after the current date.
     * @return A list of Show objects that have a date after the current date.
     */
    public List<Show> getShowsFiltered() {
        Date today = new Date();
        List<Show> shows = showRepository.getAll();
        return shows.stream().filter(show -> show.getDate().after(today)).collect(Collectors.toList());
    }

    /**
     * Creates a new show.
     * @param show The Show object to be created.
     */
    public void createShow(Show show) {
        showRepository.create(show);
    }

    /**
     * Deletes a show by its unique ID.
     * @param id The unique ID of the show to delete.
     * @throws EntityNotFoundException If no show with the specified ID exists.
     */
    public void deleteShow(Integer id) {
        if (getShow(id) == null)
            throw new EntityNotFoundException("Show does not exist!");
        showRepository.delete(id);
    }
}