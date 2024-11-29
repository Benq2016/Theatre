package Domain;

/**
 * The Admin class represents a Admin, extending the Person  abstract class and
 * implementing the HasID interface. It includes details such as ID, name, age, and email.
 */
public class Admin extends Person implements HasID {

//    /**
//     * Constructs a new Admin with the specified details.
//     *
//     * @param name   the name of the Admin
//     * @param age    the age of the Admin
//     * @param email  the email address of the Admin as an EMail object
//     */
//    public Admin(String name, int age, EMail email) {
//        super(name, age, email);
//    }

    /**
     * Constructs a new Admin with the specified details and ID from DB and File.
     * @param id     the stored id
     * @param name   the name of the Admin
     * @param age    the age of the Admin
     * @param email  the email address of the ADmin as an EMail object
     */
    public Admin(Integer id,String name, int age, EMail email) {
        super(id, name, age, email);
    }


    /**
     * Returns a string representation of the Admin object.
     *
     * @return a string containing the details of the Admin, including ID, name, age, and email
     */
    @Override
    public String toString() {
        return "Admin{" +
                "ID=" + getID() +
                ", name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", email=" + getEmail() +
                '}';
    }

}
