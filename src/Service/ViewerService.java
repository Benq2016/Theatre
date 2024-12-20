package Service;

import Exceptions.UserExistenceException;
import Repository.Repository;
import Domain.*;

import javax.swing.text.View;
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
            if (viewer.getEmail().getEmailAddress().equals(eMail.getEmailAddress()))
                return viewer.getID();
        return null;
    }

    /**
     * Creates a new viewer and adds them to the repository.
     * @param name The name of the viewer.
     * @param age The age of the viewer.
     * @param eMail The email address of the viewer.
     * @return The newly created Viewer object.
     * @throws UserExistenceException If a viewer with the same email address already exists.
     */
    public Viewer createViewer(String name, int age, EMail eMail) {
//        if (getViewer(getViewerID(eMail)) != null)
//            throw new UserExistenceException("Email address already exists!");
        Viewer viewer = new Viewer(name, age, eMail);
        viewerRepository.create(viewer);
        return viewer;
    }

    /**
     * Updates the details of an existing viewer.
     * @param id The unique ID of the viewer to update.
     * @param name The new name of the viewer.
     * @param age The new age of the viewer.
     * @param eMail The new email address of the viewer.
     * @return The updated Viewer object.
     * @throws UserExistenceException If a viewer with the same email address already exists.
     */
    public Viewer updateViewer(Integer id, String name, int age, EMail eMail) {
//        if (getViewer(getViewerID(eMail)) != null)
//            throw new UserExistenceException("Email address already exists!");
        Viewer viewer = getViewer(id);
        viewer.setName(name);
        viewer.setAge(age);
        viewer.setEmail(eMail);
        viewerRepository.update(viewer);
        return viewer;
    }

    /**
     * Deletes a viewer by their unique ID.
     * @param id The unique ID of the viewer to delete.
     * @throws UserExistenceException If no viewer with the specified ID exists.
     */
    public void deleteViewer(Integer id) {
        if (getViewer(id) == null)
            throw new UserExistenceException("Viewer does not exist!");
        viewerRepository.delete(id);
    }
}