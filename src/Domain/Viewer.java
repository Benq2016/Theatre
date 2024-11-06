package Domain;

public class Viewer extends Person implements  HasID {


    public Viewer(Integer id, String name, int age, EMail email) {
        super(id, name, age, email);
    }

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
