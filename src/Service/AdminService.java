package Service;

import Repository.Repository;
import Domain.*;
import java.util.List;

/**
 * Service class to manage admin-related operations.
 */
public class AdminService {
    private final Repository<Admin> adminRepository;

    /**
     * Constructs an AdminService with the specified repository.
     * @param adminInMemoryRepository The repository for managing Admin objects.
     */
    public AdminService(Repository<Admin> adminInMemoryRepository) {
        this.adminRepository = adminInMemoryRepository;
    }

    /**
     * Retrieves an admin by their unique ID.
     * @param id The unique ID of the admin.
     * @return The Admin object if found; otherwise, null.
     */
    public Admin getAdmin(Integer id) {
        return adminRepository.getByID(id);
    }

    /**
     * Retrieves all admins.
     * @return A list of all Admin objects.
     */
    public List<Admin> getAllAdmins() {
        return adminRepository.getAll();
    }

    /**
     * Retrieves the ID of an admin by their email.
     * @param eMail The email address of the admin.
     * @return The ID of the admin if found; otherwise, null.
     */
    public Integer getActorID(EMail eMail) {
        for (Admin admin : getAllAdmins())
            if (admin.getEmail().equals(eMail))
                return admin.getID();
        return null;
    }

    /**
     * Creates a new admin.
     * @param id The unique ID of the admin.
     * @param name The name of the admin.
     * @param age The age of the admin.
     * @param eMail The email address of the admin.
     * @return True if the admin is successfully created; otherwise, false if an admin with the given ID already exists.
     */
    public boolean createAdmin(Integer id, String name, int age, EMail eMail) {
        if (getAdmin(id) != null)
            return false;
        Admin admin = new Admin(id, name, age, eMail);
        adminRepository.create(admin);
        return true;
    }

    /**
     * Updates an existing admin's information.
     * @param id The unique ID of the admin to update.
     * @param name The new name of the admin.
     * @param age The new age of the admin.
     * @param eMail The new email address of the admin.
     * @return True if the admin is successfully updated; otherwise, false if an admin with the new email already exists.
     */
    public boolean updateAdmin(Integer id, String name, int age, EMail eMail) {
        if (getAdmin(getActorID(eMail)) != null)
            return false;
        Admin admin = getAdmin(id);
        admin.setName(name);
        admin.setAge(age);
        admin.setEmail(eMail);
        adminRepository.update(admin);
        return true;
    }

    /**
     * Deletes an admin by their unique ID.
     * @param id The unique ID of the admin to delete.
     * @return True if the admin is successfully deleted; otherwise, false if the admin does not exist.
     */
    public boolean deleteAdmin(Integer id) {
        if (getAdmin(id) == null)
            return false;
        adminRepository.delete(id);
        return true;
    }
}
