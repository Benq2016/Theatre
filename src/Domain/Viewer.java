package Domain;

public class Viewer implements Person {
    String name;
    int age;
    EMail email;

    public Viewer(String name, int age, EMail email) {
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
}
