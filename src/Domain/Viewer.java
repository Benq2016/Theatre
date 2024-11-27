package Domain;

/**
 * Represents a viewer in the system, inheriting basic person Properties such as
 * ID, name, age, and email. Implements the HasID interface to uniquely identify the viewer.
 */
public class Viewer extends Person implements  HasID {

    /**
     * Constructor for a new Viewer object with specified ID, name, age, and email.
     * @param name the name of the viewer
     * @param age the age of the viewer
     * @param email the email address of the viewer
     */
    public Viewer(String name, int age, EMail email) {
        super(name, age, email);
    }

    /**
     * Constructor for a new Viewer object with specified ID, name, age, and email.
     * @param id the stored id of the Viewer
     * @param name the name of the viewer
     * @param age the age of the viewer
     * @param email the email address of the viewer
     */
    public Viewer(int id,String name, int age, EMail email) {
        super(id,name, age, email);
    }

    /**
     * Provides a string representation of the Viewer object, including ID, name, age, and email.
     * @return a string detailing the viewer's attributes
     */
    @Override
    public String toString() {
        return "Viewer{" +
                "ID=" + getID() +
                ", name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", email=" + getEmail() +
                '}';
    }
}
