import Domain.Actor;
import Domain.EMail;

public class App {
    public static void main(String[] args) {
        Actor a = new Actor(1,"Peter", 23, new EMail("Email@Test.com","test"),11);
        System.out.println((a.getName()));
    }
}
