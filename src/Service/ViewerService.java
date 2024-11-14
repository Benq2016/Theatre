package Service;

import Repository.Repository;
import Domain.*;
import java.util.List;

public class ViewerService {
    private final Repository<Viewer> viewerRepository;

    public ViewerService(Repository<Viewer> viewerRepository) {
        this.viewerRepository = viewerRepository;
    }

    public Viewer getViewer(Integer id) {
        return viewerRepository.getByID(id);
    }

    public List<Viewer> getAllViewers() {
        return viewerRepository.getAll();
    }

    public Integer getViewerID(EMail eMail) {
        for (Viewer viewer : getAllViewers())
            if (viewer.getEmail().equals(eMail))
                return viewer.getID();
        return null;
    }

    public boolean createViewer(Integer id, String name, int age, EMail eMail) {
        if (getViewer(id) != null)
            return false;
        Viewer newViewer = new Viewer(id, name, age, eMail);
        viewerRepository.create(newViewer);
        return true;
    }

    public boolean updateViewer(Integer id, String name, int age, EMail eMail) {
        if (getViewer(getViewerID(eMail)) != null)
            return false;
        Viewer viewer = getViewer(id);
        viewer.setName(name);
        viewer.setAge(age);
        viewer.setEmail(eMail);
        viewerRepository.update(viewer);
        return true;
    }

    public boolean deleteViewer(Integer id) {
        if (getViewer(id) == null)
            return false;
        viewerRepository.delete(id);
        return true;
    }
}
