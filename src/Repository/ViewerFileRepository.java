package Repository;

import Domain.Viewer;
import Domain.EMail;

public class ViewerFileRepository extends FileRepository<Viewer> {


    public ViewerFileRepository(String filePath) {
        super(filePath);
    }

    @Override
    protected String serialize(Viewer obj) {
        return obj.getID() + "," + obj.getName() + "," + obj.getAge() + "," +
                obj.getEmail().getEmailAddress()+ "," + obj.getEmail().getPassword();
    }

    @Override
    protected Viewer deserialize(String data) {
        String[] objectParts = data.split(",");


        int id = Integer.parseInt(objectParts[0]);
        String name = objectParts[1];
        int age = Integer.parseInt(objectParts[2]);
        String emailAddress = objectParts[3];
        String emailPassword = objectParts[4];

        EMail email = new EMail(emailAddress, emailPassword);
        return new Viewer(id, name, age, email);
    }
}

