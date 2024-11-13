package Domain;

/**
 * Represents a person in the system, serving as the base class for various types
 * of people such as viewers, actors, and CEOs. Each person has a unique ID, a name,
 * an age, and an email address.
 * This class implements the HasID interface, allowing each instance to have a unique identifier.
 */
public abstract class Person implements HasID{
    private final Integer ID;
    private String name;
    private int age;
    private EMail email;

    /**
     * Constructor for a new Person with specified details.
     * @param ID the unique identifier of the person
     * @param name the name of the person
     * @param age the age of the person
     * @param email the email address of the person
     */
    public Person(int ID, String name, int age, EMail email) {
        this.ID = ID;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    /**
     * Returns the unique identifier of the person.
     * @return the ID of the person
     */
    @Override
    public Integer getID() {
        return ID;
    }

    /**
     * Returns the name of the person.
     * @return the name of the person
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the person.
     * @param name the new name to assign to the person
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the age of the person.
     * @return the age of the person
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the person.
     * @param age the new age to assign to the person
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Returns the email address of the person.
     * @return the email address of the person
     */
    public EMail getEmail() {
        return email;
    }

    /**
     * Sets the email address of the person.
     * @param email the new email to assign to the person
     */
    public void setEmail(EMail email) {
        this.email = email;
    }



}