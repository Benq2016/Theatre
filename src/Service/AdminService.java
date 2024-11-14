package Service;

import Repository.Repository;
import Domain.*;
import java.util.List;

public class AdminService {
    private final Repository<Admin> adminRepository;

    public AdminService(Repository<Admin> adminInMemoryRepository) {
        this.adminRepository = adminInMemoryRepository;
    }

    public Admin getAdmin(Integer id) {
        return adminRepository.getByID(id);
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.getAll();
    }

    public Integer getActorID(EMail eMail) {
        for (Admin admin : getAllAdmins())
            if (admin.getEmail().equals(eMail))
                return admin.getID();
        return null;
    }

    public boolean createAdmin(Integer id, String name, int age, EMail eMail) {
        if (getAdmin(id) != null)
            return false;
        Admin admin = new Admin(id, name, age, eMail);
        adminRepository.create(admin);
        return true;
    }

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

    public boolean deleteAdmin(Integer id) {
        if (getAdmin(id) == null)
            return false;
        adminRepository.delete(id);
        return true;
    }
}
