package Domain;

public abstract class Person implements HasID{
    private Integer ID;
    private String name;
    private int age;
    private EMail email;

    public Person(int ID, String name, int age, EMail email) {
        this.ID = ID;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    @Override
    public Integer getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public EMail getEmail() {
        return email;
    }

    public void setEmail(EMail email) {
        this.email = email;
    }

}