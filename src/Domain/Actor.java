package Domain;

public class Actor implements Person {
    String name;
    int age;
    EMail email;
    int salary;

    public Actor(String name, int age, EMail email, int salary) {
        this.name = name;
        this.age = age;
        this.email = email;
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
}
