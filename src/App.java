import ControllerService.TheatreController;
import ControllerService.TheatreService;
import Domain.*;
import RepositoryPackage.InMemoryRepository;
import UserInterface.UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void tests() throws IOException {
        InMemoryRepository<Ceo> ceoInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<Actor> actorInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<Auditorium> auditoriumInMemoryRepository= new InMemoryRepository<>();
        InMemoryRepository<Show> showInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<Viewer> viewerInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<Order> orderInMemoryRepository = new InMemoryRepository<>();

        TheatreService ts = new TheatreService(ceoInMemoryRepository, actorInMemoryRepository,
                auditoriumInMemoryRepository,showInMemoryRepository, viewerInMemoryRepository,
                orderInMemoryRepository);

        TheatreController tc = new TheatreController(ts);



        //creation of ceo//
        EMail eMail = new EMail("bence.c3o@gmail.com", "sosemTalalodKi");
        tc.createCeoAccount(1, "BOSS", 24, eMail);
        //creation of 2 actors//
        EMail eMail1 = new EMail("bazsi123", "1230");
        EMail eMail2 = new EMail("anna123", "1232");
        tc.ceoHireActor(1, "bazsi", 20, eMail1, 2000);
        tc.ceoHireActor(2, "anna", 19, eMail2, 1800);
        //assigning roles//
        Map<Actor, String> roles = new HashMap<>();
        roles.putIfAbsent(tc.viewActor(1), "Romeo");
        roles.putIfAbsent(tc.viewActor(2), "Julia");
        //creating auditorium//
        tc.createAuditorium(1, "Great Sh*t Hole", 10, 20);
        //creating show//
        tc.createShow(1, "Romeo es Julia!", tc.viewAuditorium(1), roles, "2024-11-15");
        tc.createShow(2, "Hatyuk tava", tc.viewAuditorium(1), roles, "2024-11-16");
        tc.createShow(3, "Romeo es Julia!", tc.viewAuditorium(1), roles, "2024-11-17");

        //creating viewer//
        EMail eMailV1 = new EMail("bence.molnar@gmail.com", "1230");
        tc.createViewerAccount(1, "bence", 20, eMailV1);

//        System.out.println(tc.viewAllActors());
//        System.out.println(tc.viewMyShows(eMail1));
//
//        //print before order//
//        System.out.println(tc.viewShows());
//        System.out.println(tc.viewShow("Romeo es Julia!"));
//
//        //fire, adjust actors//
//        tc.ceoFireActor(1);
//        tc.ceoChangeSalary(2, 2000);
//        System.out.println(tc.viewAllActors());
//
//        //manage Shows/ Auditoriums//
//        tc.deleteAuditorium(1);
//        tc.ceoDeleteShow(2);
//        System.out.println(tc.viewAllAuditoriums());
//        System.out.println(tc.viewShows());

        //reserving seats//
        List<Integer> seats = new ArrayList<>();
        seats.add(66); seats.add(67); seats.add(68); seats.add(69); seats.add(1); seats.add(2); seats.add(150); seats.add(151);
        int totalPrice = 25 * seats.size();

        //creating order//
        tc.createOrder(1, 1, eMailV1, seats, totalPrice);

        //printing lists and audit after reservation//
//        System.out.println("\n" + tc.viewMyOrders(eMailV1) +  "\n");
//        System.out.println(tc.viewOrderTickets(1));
//
//        //manage Account for Viewer//
//        System.out.println(tc.viewAccount(eMailV1));
//        tc.manageViewerAccount("Bence", 21, eMailV1, eMailV1);
//        System.out.println(tc.viewAccount(eMailV1));
//        EMail eMailV1_mod = new EMail("bence.molnar@yahoo.com", "1231");
//        tc.manageViewerAccount("bence", 20, eMailV1, eMailV1_mod);
//        System.out.println(tc.viewAccount(eMailV1_mod));
//
//        //manage Account for Ceo//
//        System.out.println(tc.viewAccount(eMail));
//        tc.manageCeoAccount("Bence", 21, eMail, eMail);
//        System.out.println(tc.viewAccount(eMail));
//        tc.manageCeoAccount("bence", 20, eMail, eMailV1_mod);
//        System.out.println(tc.viewAccount(eMailV1_mod));
//
//
//        System.out.println(tc.viewAllActors());
//        System.out.println(tc.viewAllAuditoriums());


//        login(tc);

    }

    public static void main(String[] args) throws IOException {

        InMemoryRepository<Ceo> ceoInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<Actor> actorInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<Auditorium> auditoriumInMemoryRepository= new InMemoryRepository<>();
        InMemoryRepository<Show> showInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<Viewer> viewerInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<Order> orderInMemoryRepository = new InMemoryRepository<>();

        TheatreService ts = new TheatreService(ceoInMemoryRepository, actorInMemoryRepository,
                auditoriumInMemoryRepository,showInMemoryRepository, viewerInMemoryRepository,
                orderInMemoryRepository);

        TheatreController tc = new TheatreController(ts);

//        tests();
        Auditorium auditorium = new Auditorium(2,"Main Hall", 25,25);
//        Actor actor = new Actor(1,"Peter", 34, new EMail())

        UI ui = new UI(tc);
        ui.RUN();


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
//                    printUserOptions(user, logEmail);
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
                    boolean user = theatreController.createViewerAccount(id, name, age, logEmail);
                    if (user) {
                        System.out.println("Sign-up successful.");
//                        printUserOptions(3, logEmail);
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
