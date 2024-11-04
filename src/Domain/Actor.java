package src.Domain;

public class Actor implements Person, HasID {
    private Integer id;
    private String name;
    private int age;
    private EMail email;
    private int salary;

    public Actor(Integer id,String name, int age, EMail email, int salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email=" + email +
                ", salary=" + salary +
                '}';
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public EMail getEmail() {
        return email;
    }

    @Override
    public void setEmail(EMail email) {
        this.email = email;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public Integer getID() {
        return this.id;
    }
}
