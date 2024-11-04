package src.Domain;

public class Ceo implements Person, HasID {
    private Integer id;
    private String name;
    private int age;
    private EMail email;

    public Ceo(Integer id, String name, int age, EMail email) {
        this.id = id;
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

    @Override
    public Integer getID() {
        return this.id;
    }
}
