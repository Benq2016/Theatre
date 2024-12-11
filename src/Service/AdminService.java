package Service;

import Exceptions.UserExistenceException;
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
    public Integer getAdminID(EMail eMail) {
        for (Admin admin : getAllAdmins())
            if (admin.getEmail().getEmailAddress().equals(eMail.getEmailAddress()))
                return admin.getID();
        return null;
    }

    /**
     * Creates a new admin and adds it to the repository.
     * @param id The unique ID of the admin.
     * @param name The name of the admin.
     * @param age The age of the admin.
     * @param eMail The email address of the admin.
     * @return The newly created Admin object.
     * @throws UserExistenceException If an admin with the same ID already exists.
     */
    public Admin createAdmin(Integer id, String name, int age, EMail eMail) {
//        if (getAdmin(getAdminID(eMail)) != null)
//            throw new UserExistenceException("Email address already exists!");
        Admin admin = new Admin(id, name, age, eMail);
        adminRepository.create(admin);
        return admin;
    }

    /**
     * Updates the details of an existing admin.
     *
     * @param id The unique ID of the admin to update.
     * @param name The new name of the admin.
     * @param age The new age of the admin.
     * @param eMail The new email address of the admin.
     * @return The updated Admin object.
     * @throws UserExistenceException If an admin with the same email address already exists.
     */
    public Admin updateAdmin(Integer id, String name, int age, EMail eMail) {
        if (getAdmin(getAdminID(eMail)) != null)
            throw new UserExistenceException("Email address already exists!");
        Admin admin = getAdmin(id);
        admin.setName(name);
        admin.setAge(age);
        admin.setEmail(eMail);
        adminRepository.update(admin);
        return admin;
    }

    /**
     * Deletes an admin by their unique ID.
     * @param id The unique ID of the admin to delete.
     * @throws UserExistenceException If no admin with the specified ID exists.
     */
    public void deleteAdmin(Integer id) {
        if (getAdmin(id) == null)
            throw new UserExistenceException("Admin does not exist!");
        adminRepository.delete(id);
    }
}