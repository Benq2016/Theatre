package Domain;

/**
 * The Admin class represents a CEO, extending the Person  abstract class and
 * implementing the HasID interface. It includes details such as ID, name, age, and email.
 */
public class Admin extends Person implements HasID {

    /**
     * Constructs a new Admin with the specified details.
     *
     * @param id     the unique identifier of the CEO
     * @param name   the name of the CEO
     * @param age    the age of the CEO
     * @param email  the email address of the CEO as an EMail object
     */
    public Admin(Integer id, String name, int age, EMail email) {
        super(id, name, age, email);
    }

    /**
     * Returns a string representation of the Admin object.
     *
     * @return a string containing the details of the CEO, including ID, name, age, and email
     */
    @Override
    public String toString() {
        return "CEO{" +
                "ID=" + getID() +
                ", name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", email=" + getEmail() +
                '}';
    }

}
