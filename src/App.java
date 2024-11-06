import Domain.Actor;
import Domain.Auditorium;
import Domain.Ceo;
import Domain.EMail;

public class App {
    public static void main(String[] args) {
        Actor a = new Actor(1,"Peter", 23, new EMail("Email@Test.com","test"),11);
        System.out.println(a.getAge());
        System.out.println(a.getEmail());
        System.out.println(a);

        Ceo c = new Ceo(1,"Boss",45, new EMail("Email@Testceo.com","testceo"));
        System.out.println(c.getAge());
        System.out.println(c.getEmail());
        System.out.println(c);

        Auditorium ad = new Auditorium(1,"Great Hall",500,25,20);
        System.out.println(ad.getCapacity());
        System.out.println(ad.getSeatPlace());
    }
}
