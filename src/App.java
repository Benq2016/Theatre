import Controller.TheatreController;
import Repository.*;
import Service.*;
import Domain.*;
import UI.UI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;

import static UI.UI.choosingBetweenLoginAndSignup;


/**
 * The App class is the entry point of the theater management system.
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
    public static void main(String[] args) throws IOException, ParseException {

        String storageType = chooseStorageType();

        Repository<Admin> adminRepository;
        Repository<Actor> actorRepository;
        Repository<Auditorium> auditoriumRepository;
        Repository<Show> showRepository;
        Repository<Viewer> viewerRepository;
        Repository<Order> orderRepository;

        switch (storageType) {
            case "1": {
                // In-memory instantiation
                adminRepository = new InMemoryRepository<Admin>();
                actorRepository = new InMemoryRepository<Actor>();
                auditoriumRepository = new InMemoryRepository<Auditorium>();
                showRepository = new InMemoryRepository<Show>();
                viewerRepository = new InMemoryRepository<Viewer>();
                orderRepository = new InMemoryRepository<Order>();
                break;
            }
            case "2": {
                // File-based instantiation


                adminRepository = new AdminFileRepository("src/FilesForStorage/AdminFile");
                actorRepository = new ActorFileRepository("src/FilesForStorage/ActorFile");
                auditoriumRepository = new AuditoriumFileRepository("src/FilesForStorage/AuditoriumFile");
                showRepository = new ShowFileRepository("src/FilesForStorage/ShowFile");
                viewerRepository = new ViewerFileRepository("src/FilesForStorage/ViewerFile");
                orderRepository = new OrderFileRepository("src/FilesForStorage/OrderFile");
                break;
            }
            default:
                throw new IllegalArgumentException("Invalid storage type selected.");
        }

        ActorService actorService = new ActorService(actorRepository);
        AdminService adminService = new AdminService(adminRepository);
        ViewerService viewerService = new ViewerService(viewerRepository);
        ShowService showService = new ShowService(showRepository);
        AuditoriumService auditoriumService = new AuditoriumService(auditoriumRepository);
        OrderService orderService = new OrderService(orderRepository);


        TheatreService ts = new TheatreService(adminService, actorService, auditoriumService, showService,
                viewerService, orderService);

        TheatreController tc = new TheatreController(ts);
        if (storageType.equals("1")){
            makeInitialObjects(tc);
        }

        if (storageType.equals("2")) {
            ((ShowFileRepository) showRepository).setTheatreController(tc);
            makeInitialObjects(tc);
            setInitialStaticIDForEveryDomain(tc);
        }

//        /*It retrieves the email from the user*/
        EMail emailGotFromLoginSignIn = choosingBetweenLoginAndSignup(tc);
//
//        /*Start of the program carrying out ui functions which carries out functions from other parts of the app*/
        UI ui = new UI(tc);
        ui.RUN(emailGotFromLoginSignIn);


    }

    /***
     * Creates some initial Objects
     *
     * @param tc - TheatreController for connecting with the running theaterController
     */
    public static void makeInitialObjects(TheatreController tc) {
        tc.createAdminAccount(1,"Boss David", 54, new EMail("david@gmail.com", "1230"));

        tc.createActorAccount("Peter", 23, new EMail("peter@gmail.com", "123"), 1200);
        tc.createActorAccount("Bence", 33, new EMail("bence@gmail.com", "asd"), 1500);
        tc.createActorAccount("Anna", 21, new EMail("anna@gmail.com", "rte"), 2300);
        tc.createActorAccount("Balazs", 26, new EMail("balazs@gmail.com", "hgf"), 1900);
        tc.createActorAccount("Iosif", 44, new EMail("iosif@gmail.com", "123456"), 2230);

        tc.createAuditorium("Grand Hall", 6, 15);
        tc.createAuditorium("Klein Stage", 7, 12);

        Map<Actor, String> roles = new HashMap<Actor, String>();
        roles.putIfAbsent(tc.viewActor(1),"Werther");
        roles.putIfAbsent(tc.viewActor(3),"Luise");
        roles.putIfAbsent(tc.viewActor(4),"Faust");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String date1S = "2024-11-18";
        Date date1;
        try {
            date1 = sdf.parse(date1S);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String date2S = "2024-11-27";
        Date date2;
        try {
            date2 = sdf.parse(date2S);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String date3S = "2024-12-05";
        Date date3;
        try {
            date3 = sdf.parse(date3S);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String date4S = "2024-12-25";
        Date date4;
        try {
            date4 = sdf.parse(date4S);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        tc.createShow("Lets see if it runs!", date1, 1, roles, 25);
        tc.createShow("Lets test the sort", date2, 2, roles, 30);
        tc.createShow("New show1", date3, 1, roles, 25);
        tc.createShow("New show2", date4, 2, roles, 30);

        tc.createViewerAccount("Victor Ross", 23, new EMail("victor@gmail.com", "123"));

        List<Integer> seats1 = new ArrayList<Integer>();
        seats1.add(1); seats1.add(2); seats1.add(45); seats1.add(46);
        List<Integer> seats2 = new ArrayList<Integer>();
        seats2.add(1); seats2.add(7); seats2.add(64); seats2.add(84);

        tc.createOrder(1, 1,  seats1);
//        System.out.println(tc.viewAuditorium(1));

        tc.createOrder(2, 1,  seats2);
//        System.out.println(tc.viewAuditorium(1));
//        System.out.println(tc.viewAuditorium(2));

//        tc.viewOrders().forEach(System.out::println);
//        tc.viewOrdersSorted().forEach(System.out::println);
//        tc.viewOrders().forEach(System.out::println);

//        tc.deleteOrder(1);
//        tc.viewOrders().forEach(System.out::println);
//        System.out.println(tc.viewAuditorium(1));
    }

    /***
     * Choosing the type of storage that the program uses
     *
     * @return - a string representing a number which indicates what type of storage the user wants
     * @throws IOException
     */
    public static String chooseStorageType() throws IOException {
        String choice;
        while (true) {
            System.out.println("WELCOME TO THE THEATER MANAGEMENT APP!\n");
            System.out.println("To proceed please choose a storage format:");
            System.out.println("1 - In Memory storage ");
            System.out.println("2 - File storage ");
            System.out.println("3 - Database storage (WORK IN PROGRESS)");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            choice = String.valueOf(reader.readLine());
            if (choice.equals("1") || choice.equals("2") || choice.equals("3"))
                break;
            else
                System.out.println("Invalid type, please select 1, 2 or 3");
        }
        return choice;
    }

    /**
     * Sets the static ID in the Domain not to start from 0 but from the next available id
     * @param tc - a link to the Theatre controller
     * */
    public static void setInitialStaticIDForEveryDomain(TheatreController tc){
        //for setting the auditorium static ID
        int staticID = 0;
        List<Auditorium> allAudit = tc.viewAuditoriums();
        for (Auditorium auditorium : allAudit) {
            if (staticID < auditorium.getID())
                staticID = auditorium.getID();
        }
        Auditorium.setIdCounter(staticID);

        //for setting the order static ID
        staticID = 0;
        List<Order> allOrder = tc.viewOrders();
        for (Order order : allOrder) {
            if (staticID < order.getID())
                staticID = order.getID();
        }
        Order.setIdCounter(staticID);

        //for setting the Show static ID
        staticID = 0;
        List<Show> allShow = tc.viewShows();
        for (Show show : allShow) {
            if(staticID < show.getID())
                staticID = show.getID();
        }
        Show.setIdCounter(staticID);

        //for setting the next Persons static ID
        staticID = 0;
        List<Actor> allActor = tc.viewActors();
        List<Viewer> allViewers = tc.viewViewers();
        for (Viewer viewer : allViewers) {
            if (staticID < viewer.getID())
                staticID = viewer.getID();
        }
        for (Actor actor : allActor) {
            if (staticID < actor.getID())
                staticID = actor.getID();
        }
        Person.setIdCounter(staticID);

    }
}
