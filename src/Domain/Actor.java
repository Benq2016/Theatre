package Domain;

public class Actor extends Person implements HasID{

    private int salary;

    public Actor(Integer id,String name, int age, EMail email, int salary) {
        super(id, name, age, email);
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

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
