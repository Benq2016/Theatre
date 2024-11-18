package Repository;

import Domain.Admin;
import Domain.EMail;

public class AdminFileRepository extends FileRepository<Admin> {


    public AdminFileRepository(String filePath) {
        super(filePath);
    }

    @Override
    protected String serialize(Admin obj) {
        return obj.getID() + "," + obj.getName() + "," + obj.getAge() + "," +obj.getEmail();
    }

    @Override
    protected Admin deserialize(String data) {
        String[] objectParts = data.split(",");
        return new Admin(Integer.parseInt(objectParts[0]), objectParts[1], Integer.parseInt(objectParts[2]),
                new EMail(objectParts[3], objectParts[4]));
    }
}
