package Service;

import Repository.Repository;
import Domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.time.ZoneId;

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
