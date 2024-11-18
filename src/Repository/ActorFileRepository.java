package Repository;

import Domain.Actor;
import Domain.EMail;

public class ActorFileRepository extends FileRepository<Actor> {

    public ActorFileRepository(String filePath) {
        super(filePath);
    }

    @Override
    protected String serialize(Actor obj) {
        return obj.getID() + "," + obj.getName() + "," + obj.getAge() + "," +
                obj.getEmail().getEmailAddress()+ "," + obj.getEmail().getPassword() + "," + obj.getSalary();
    }

    @Override
    protected Actor deserialize(String data) {
        String[] objectParts = data.split(",");


        int id = Integer.parseInt(objectParts[0]);
        String name = objectParts[1];
        int age = Integer.parseInt(objectParts[2]);
        String emailAddress = objectParts[3];
        String emailPassword = objectParts[4];
        int salary = Integer.parseInt(objectParts[5]);

        EMail email = new EMail(emailAddress, emailPassword);
        return new Actor(id, name, age, email,salary);
    }
}
