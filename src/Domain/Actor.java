package Domain;

/**
 * The Actor class represents an actor with a salary, extending the Person abstract class
 * and implementing the HasID interface.
 * It includes details such as ID, name, age, email, and salary.
 */

public class Actor extends Person implements HasID{

    /**
     * The salary of the actor.
     */
    private int salary;

    /**
     * Constructs a new Actor with the specified details.
     *
     * @param name   the name of the actor
     * @param age    the age of the actor
     * @param email  the email address of the actor as an  EMail object
     * @param salary the salary of the actor
     */
    public Actor(String name, int age, EMail email, int salary) {
        super(name, age, email);
        this.salary = salary;
    }

    public Actor(int id,String name, int age, EMail email, int salary) {
        super(id,name, age, email);
        this.salary = salary;
    }

    /**
     * Gets the salary of the actor.
     *
     * @return the salary of the actor
     */
    public int getSalary() {
        return salary;
    }

    /**
     * Sets the salary of the actor.
     *
     * @param salary the new salary of the actor
     */
    public void setSalary(int salary) {
        this.salary = salary;
    }

    /**
     * Returns a string representation of the Actor object.
     *
     * @return a string containing the details of the actor, including ID, name, age, email, and salary
     */
    @Override
    public String toString() {
        return "Actor{" +
                "ID=" + getID() +
                ", name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", email=" + getEmail() +
                ", salary=" + salary +
                '}';
    }
}
