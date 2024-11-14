import Controller.TheatreController;
import Service.*;
import Domain.*;
import Repository.InMemoryRepository;
//import UI.UI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import static UI.UI.choosingBetweenLoginAndSignup;

/**
 * The App class is the entry point of the theatre management system.
 * It sets up the necessary repositories, services, and controllers, and runs the user interface.
 */
public class App {

    /**
     * Runs some test cases for the system. This method is used for initial testing
     * of the system's features and doesn't affect the final production result.
     *
     * @throws IOException if there is an issue with input or output during the tests
     */
    public static void tests() throws IOException {
//        InMemoryRepository<Admin> ceoInMemoryRepository = new InMemoryRepository<>();
//        InMemoryRepository<Actor> actorInMemoryRepository = new InMemoryRepository<>();
//        InMemoryRepository<Auditorium> auditoriumInMemoryRepository= new InMemoryRepository<>();
//        InMemoryRepository<Show> showInMemoryRepository = new InMemoryRepository<>();
//        InMemoryRepository<Viewer> viewerInMemoryRepository = new InMemoryRepository<>();
//        InMemoryRepository<Order> orderInMemoryRepository = new InMemoryRepository<>();
//
//        TheatreService ts = new TheatreService(ceoInMemoryRepository, actorInMemoryRepository,
//                auditoriumInMemoryRepository,showInMemoryRepository, viewerInMemoryRepository,
//                orderInMemoryRepository);
//
//        TheatreController tc = new TheatreController(ts);



        //creation of ceo//
//        EMail eMail = new EMail("bence.c3o@gmail.com", "sosemTalalodKi");
//        tc.createCeoAccount(1, "BOSS", 24, eMail);
//        //creation of 2 actors//
//        EMail eMail1 = new EMail("bazsi123", "1230");
//        EMail eMail2 = new EMail("anna123", "1232");
//        tc.ceoHireActor(1, "bazsi", 20, eMail1, 2000);
//        tc.ceoHireActor(2, "anna", 19, eMail2, 1800);
//        //assigning roles//
//        Map<Actor, String> roles = new HashMap<>();
//        roles.putIfAbsent(tc.viewActor(1), "Romeo");
//        roles.putIfAbsent(tc.viewActor(2), "Julia");
//        //creating auditorium//
//        tc.createAuditorium(1, "Great Sh*t Hole", 10, 20);
//        //creating show//
//        tc.createShow(1, "Romeo es Julia!", tc.viewAuditorium(1), roles, "2024-11-15");
//        tc.createShow(2, "Hatyuk tava", tc.viewAuditorium(1), roles, "2024-11-16");
//        tc.createShow(3, "Romeo es Julia!", tc.viewAuditorium(1), roles, "2024-11-17");
//
//        //creating viewer//
//        EMail eMailV1 = new EMail("bence.molnar@gmail.com", "1230");
//        tc.createViewerAccount(1, "bence", 20, eMailV1);

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
//        List<Integer> seats = new ArrayList<>();
//        seats.add(66); seats.add(67); seats.add(68); seats.add(69); seats.add(1); seats.add(2); seats.add(150); seats.add(151);
//
//        //creating order//
//        tc.createOrder(1, 1, eMailV1, seats);

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
//        //manage Account for Admin//
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

    /**
     * The main method of the application. This is where the repositories, services, and controllers
     * are instantiated, and the application's flow starts by creating initial objects.
     * It also retrieves user input for login or signup and starts the user interface.
     *
     * @param args command-line arguments (not used in this program)
     * @throws IOException if there is an issue with input or output during the program's execution
     */
    public static void main(String[] args) throws IOException {
        InMemoryRepository<Admin> adminInMemoryRepository = new InMemoryRepository<Admin>();
        InMemoryRepository<Actor> actorInMemoryRepository = new InMemoryRepository<Actor>();
        InMemoryRepository<Auditorium> auditoriumInMemoryRepository= new InMemoryRepository<Auditorium>();
        InMemoryRepository<Show> showInMemoryRepository = new InMemoryRepository<Show>();
        InMemoryRepository<Viewer> viewerInMemoryRepository = new InMemoryRepository<Viewer>();
        InMemoryRepository<Order> orderInMemoryRepository = new InMemoryRepository<Order>();

        ActorService actorService = new ActorService(actorInMemoryRepository);
        AdminService adminService = new AdminService(adminInMemoryRepository);
        ViewerService viewerService = new ViewerService(viewerInMemoryRepository);
        ShowService showService = new ShowService(showInMemoryRepository);
        AuditoriumService auditoriumService = new AuditoriumService(auditoriumInMemoryRepository);
        OrderService orderService = new OrderService(orderInMemoryRepository);


        TheatreService ts = new TheatreService(adminService, actorService, auditoriumService, showService,
                        viewerService, orderService);

        TheatreController tc = new TheatreController(ts);

        make_initial_objects(tc);

//        /*It retrieves the email from the user*/
//        EMail emailGotFromLoginSignIn = choosingBetweenLoginAndSignup(tc);
//
//        /*Start of the program carrying out ui functions which carries out functions from other parts of the app*/
//        UI ui = new UI(tc);
//        ui.RUN(emailGotFromLoginSignIn);


    }

    public static void make_initial_objects(TheatreController tc) {
        tc.createAdminAccount(1,"Boss David", 54, new EMail("david@gmail.com", "1230"));

        tc.createActorAccount(1,"Peter",23, new EMail("peter@gmail.com", "123"),1200);
        tc.createActorAccount(2,"Bence",33, new EMail("bence@gmail.com", "asd"),1500);
        tc.createActorAccount(3,"Anna",21, new EMail("anna@gmail.com", "rte"),2300);
        tc.createActorAccount(4,"Balazs",26, new EMail("balazs@gmail.com", "hgf"),1900);
        tc.createActorAccount(5,"Iosif",44, new EMail("iosif@gmail.com", "123456"),2230);
        tc.viewActors().forEach(System.out::println);

        tc.createAuditorium(1,"Grand Hall", 6,15);
        tc.createAuditorium(2,"Klein Stage",20,20);
        tc.viewAuditoriums().forEach(System.out::println);

        Map<Actor, String> roles = new HashMap<Actor, String>();

        tc.createShow(1,"Lets see if it runs!","2024-11-15",1, roles, 25);
        tc.viewShows().forEach(System.out::println);

        tc.createViewerAccount(1,"Victor Ross", 23, new EMail("victor@gmail.com", "123"));
        tc.viewViewers().forEach(System.out::println);

        List<Integer> seats = new ArrayList<Integer>();
        seats.add(1); seats.add(2); seats.add(45); seats.add(46);
        tc.createOrder(1, 1, 1, seats);
        tc.viewOrders().forEach(System.out::println);
    }

}
