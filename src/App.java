import ControllerService.TheatreController;
import ControllerService.TheatreService;
import Domain.*;
import RepositoryPackage.InMemoryRepository;

import javax.swing.text.View;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) throws IOException {
        InMemoryRepository<Ceo> ceoInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<Actor> actorInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<Auditorium> auditoriumInMemoryRepository= new InMemoryRepository<>();
        InMemoryRepository<Show> showInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<Viewer> viewerInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<Ticket> ticketInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<Order> orderInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<Seat> seatInMemoryRepository = new InMemoryRepository<>();

        TheatreService ts = new TheatreService(ceoInMemoryRepository, actorInMemoryRepository,
                auditoriumInMemoryRepository,showInMemoryRepository, viewerInMemoryRepository, ticketInMemoryRepository,
                orderInMemoryRepository, seatInMemoryRepository);

        TheatreController tc = new TheatreController(ts);

//        Actor a = new Actor(1,"Peter", 23, new EMail("Email@Test.com","test"),11);
//        System.out.println(a.getAge());
//        System.out.println(a.getEmail());
//        System.out.println(a);
//
//        Ceo c = new Ceo(1,"Boss",45, new EMail("Email@Testceo.com","testceo"));
//        System.out.println(c.getAge());
//        System.out.println(c.getEmail());
//        System.out.println(c);
//
//        Auditorium ad = new Auditorium(1,"Great Hall",15,20);
//        System.out.println(ad.getCapacity());
//        System.out.println(ad);
        tc.createAccount(1, "bence", 20, new EMail("bence.molnar@gmail.com", "1230"));


        tc.ceoHireActor(34,"Jozsika",54,new EMail("Joco@theatre.eu", "velica22"), 4000);
        System.out.println(actorInMemoryRepository.getAll());
        System.out.println('\n');
        tc.ceoHireActor(23,"Petike", 32, new EMail("petike@test.com","qwe"), 3400);
        tc.ceoHireActor(12,"Jeno", 22, new EMail("Jeno@test.com","rty"), 1200);
        tc.ceoHireActor(76,"Viktorka", 67,new EMail("Viktorka@test.com","uiop"), 7600);

        System.out.println("All actors : "+actorInMemoryRepository.getAll());
        System.out.println('\n');
        tc.ceoFireActor(34);
        tc.ceoFireActor(76);
        System.out.println("Actors after firing some of them : " + actorInMemoryRepository.getAll());

        System.out.println('\n');

        System.out.println("Salary before change : " + actorInMemoryRepository.getByID(12).getSalary());
        tc.ceoChangeSalary(12,6000);
        System.out.println("Salary after change : " + actorInMemoryRepository.getByID(12).getSalary());
        login(tc);

    }

    public static void login(TheatreController theatreController) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("------------------------------------------------------------------");
            System.out.println("Please choose whether you want to sign-in or to sign-up: ");
            System.out.println("   1 - Sign-in\n   2 - Sign-up\n   0 - Exit");

            String input = reader.readLine();
            int option;
            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number (0, 1, or 2).");
                continue;
            }

            switch (option) {
                case 1: {
                    System.out.println("Please enter your email: ");
                    String email = reader.readLine();
                    System.out.println("Please enter your password: ");
                    String password = reader.readLine();
                    EMail logEmail = new EMail(email, password);
                    int user = theatreController.login(logEmail);
                    printUserOptions(user, logEmail);
                    //////////CSINÁLHATSZ EGY FUNKCIÓT MINT A LOGIN() ÉS SWITCH-EL CASE-ENKÉNT KIPRINTELED MINDEN USER-NEK AZ OPCIÓIT//////////
                    //////////4 CASE VAN: 1 - ACTOR, 2 - CEO, 3 - VIEWER, 0 - NEM LÉTEZIK ILYEN ACCOUNT//////////
                    //////////LOG-eEMAIL KELL HOGY BE TUDJA AZONOSITANI AZ ID-t MIKOR ORDERT CSINÁLSZ//////////
                    break;
                }
                case 2: {
                    System.out.println("Please enter your id: ");
                    int id = Integer.parseInt(reader.readLine());
                    System.out.println("Please enter your name: ");
                    String name = reader.readLine();
                    System.out.println("Please enter your age: ");
                    int age = Integer.parseInt(reader.readLine());
                    System.out.println("Please enter your email: ");
                    String email = reader.readLine();
                    System.out.println("Please enter your password: ");
                    String password = reader.readLine();
                    EMail logEmail = new EMail(email, password);
                    boolean user = theatreController.createAccount(id, name, age, logEmail);
                    if (user) {
                        System.out.println("Sign-up successful.");
                        printUserOptions(3, logEmail);
                        //////////MIVEL EGY CÉGNEK MINDIG EGY CEO-JA VAN FELTESZEM NEM LEHET ÚJJAT HOZZÁADNI ÉS ACTOR-T
                        //////////MEG CSAK CEO TUD HOZZÁÁDNI TEHÁT ÚJ ACCOUNTOT CSAK VIEWER TUD CSINÁLNI
                        //////////EZÉRT VIEWER-KÉNT AZ APP MEHET TOVÁBB//////////
                    }
                    else System.out.println("Sign-up failed.");
                    break;
                }
                case 0: {
                    System.out.println("Exiting the program. Goodbye!");
                    return;
                }
                default: {
                    System.out.println("Invalid option. Please choose 0, 1, or 2.");
                    break;
                }
            }
        }
    }
}
