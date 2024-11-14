package Service;

import Repository.Repository;
import Domain.*;

import java.net.Inet4Address;
import java.util.List;

public class AuditoriumService {
    private final Repository<Auditorium> auditoriumRepository;

    public AuditoriumService(Repository<Auditorium> auditoriumRepository) {
        this.auditoriumRepository = auditoriumRepository;
    }

    public Auditorium getAuditorium(Integer id) {
        return auditoriumRepository.getByID(id);
    }

    public List<Auditorium> getAllAuditoriums() {
        return auditoriumRepository.getAll();
    }

    public boolean createAuditorium(Integer id, String name, int rows, int cols) {
        if (getAuditorium(id) != null)
            return false;
        Auditorium auditorium = new Auditorium(id, name, rows, cols);
        auditoriumRepository.create(auditorium);
        return true;
    }

    public boolean deleteAuditorium(Integer id) {
        if (getAuditorium(id) == null)
            return false;
        auditoriumRepository.delete(id);
        return true;
    }
}
