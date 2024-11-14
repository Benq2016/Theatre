package Service;

import Repository.Repository;
import Domain.*;
import java.util.List;
import java.util.Map;

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
