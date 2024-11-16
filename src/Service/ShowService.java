package Service;

import Repository.Repository;
import Domain.*;

import java.util.*;
import java.time.ZoneId;
import java.util.stream.Collectors;

public class ShowService {
    private final Repository<Show> showRepository;

    public ShowService(Repository<Show> showRepository) {
        this.showRepository = showRepository;
    }

    public Show getShow(Integer id) {
        return showRepository.getByID(id);
    }

    public List<Show> getAllShows() {
        return showRepository.getAll();
    }

    public List<Show> getShowsSorted() {
        List<Show> shows = showRepository.getAll();
        List<Show> mutableShows = new ArrayList<>(shows);

        mutableShows.sort(Comparator.comparing(show -> show.getDate().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate()));
        return mutableShows;
    }

    public List<Show> getShowsFiltered() {
        Date today = new Date();
        List<Show> shows = showRepository.getAll();
        return shows.stream().filter(show -> show.getDate().after(today)).collect(Collectors.toList());
    }

    public void createShow(Show show) {
        showRepository.create(show);
    }

    public boolean deleteShow(Integer id) {
        if (getShow(id) == null)
            return false;
        showRepository.delete(id);
        return true;
    }

}
