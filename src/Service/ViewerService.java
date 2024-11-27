package Service;

import Repository.Repository;
import Domain.*;
import java.util.List;

/**
 * Service class to manage viewer-related operations.
 */
public class ViewerService {
    private final Repository<Viewer> viewerRepository;

    /**
     * Constructs a ViewerService with the specified repository.
     * @param viewerRepository The repository for managing Viewer objects.
     */
    public ViewerService(Repository<Viewer> viewerRepository) {
        this.viewerRepository = viewerRepository;
    }

    /**
     * Retrieves a viewer by their unique ID.
     * @param id The unique ID of the viewer.
     * @return The Viewer object if found; otherwise, null.
     */
    public Viewer getViewer(Integer id) {
        return viewerRepository.getByID(id);
    }

    /**
     * Retrieves all viewers.
     * @return A list of all Viewer objects.
     */
    public List<Viewer> getAllViewers() {
        return viewerRepository.getAll();
    }

    /**
     * Retrieves the ID of a viewer based on their email.
     * @param eMail The email of the viewer.
     * @return The unique ID of the viewer if found; otherwise, null.
     */
    public Integer getViewerID(EMail eMail) {
        for (Viewer viewer : getAllViewers())
            if (viewer.getEmail().equals(eMail))
                return viewer.getID();
        return null;
    }

    /**
     * Creates a new viewer account.
     * @param name  The name of the viewer.
     * @param age   The age of the viewer.
     * @param eMail The email of the viewer.
     * @return true if the viewer was successfully created; false if the viewer already exists.
     */
    public boolean createViewer(String name, int age, EMail eMail) {

        Viewer newViewer = new Viewer(name, age, eMail);
        viewerRepository.create(newViewer);
        return true;
    }

    /**
     * Updates an existing viewer's information.
     * @param id    The unique ID of the viewer.
     * @param name  The new name of the viewer.
     * @param age   The new age of the viewer.
     * @param eMail The new email of the viewer.
     * @return true if the viewer was successfully updated; false if the viewer does not exist.
     */
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

    /**
     * Deletes a viewer account by their unique ID.
     * @param id The unique ID of the viewer to delete.
     * @return true if the viewer was successfully deleted; false if the viewer does not exist.
     */
    public boolean deleteViewer(Integer id) {
        if (getViewer(id) == null)
            return false;
        viewerRepository.delete(id);
        return true;
    }
}
