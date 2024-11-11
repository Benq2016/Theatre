package Domain;

public class Ceo extends Person implements HasID {

    public Ceo(Integer id, String name, int age, EMail email) {
        super(id, name, age, email);
    }

    @Override
    public String toString() {
        return "CEO{" +
                "ID=" + getID() +
                ", name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", email=" + getEmail() +
                '}';
    }

}
