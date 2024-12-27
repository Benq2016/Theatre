import Controller.TheatreController;
import Exceptions.EntityNotFoundException;
import Exceptions.UserExistenceException;
import Exceptions.ValidationException;
import Repository.*;
import Service.*;
import Domain.*;
import UI.UI;
//import com.microsoft.sqlserver.jdbc.SQLServerException;

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
     * The main method of the application. This is where the repositories, services, and controllers
     * are instantiated, and the application's flow starts by creating initial objects.
     * It also retrieves user input for login or signup and starts the user interface.
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
//                 In-memory instantiation
                adminRepository = new InMemoryRepository<Admin>();
                actorRepository = new InMemoryRepository<Actor>();
                auditoriumRepository = new InMemoryRepository<Auditorium>();
                showRepository = new InMemoryRepository<Show>();
                viewerRepository = new InMemoryRepository<Viewer>();
                orderRepository = new InMemoryRepository<Order>();
                break;
            }
            case "2": {
//                 File-based instantiation
                adminRepository = new AdminFileRepository("src/FilesForStorage/AdminFile");
                actorRepository = new ActorFileRepository("src/FilesForStorage/ActorFile");
                auditoriumRepository = new AuditoriumFileRepository("src/FilesForStorage/AuditoriumFile");
                showRepository = new ShowFileRepository("src/FilesForStorage/ShowFile");
                viewerRepository = new ViewerFileRepository("src/FilesForStorage/ViewerFile");
                orderRepository = new OrderFileRepository("src/FilesForStorage/OrderFile");
                break;
            }
            case "3": {
//                  Database instantiation
                adminRepository = new AdminDBRepository();
                actorRepository = new ActorDBRepository();
                auditoriumRepository = new AuditoriumDBRepository();
                showRepository = new ShowDBRepository();
                viewerRepository = new ViewerDBRepository();
                orderRepository = new OrderDBRepository();
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

        if (storageType.equals("3")) {
//            makeInitialObjects(tc);
            setInitialStaticIDForEveryDomain(tc);
        }

        EMail emailGotFromLoginSignIn = choosingBetweenLoginAndSignup(tc);

        UI ui = new UI(tc);
        ui.RUN(emailGotFromLoginSignIn);

    }

    /**
     * Creates some initial Objects
     * @param tc - TheatreController for connecting with the running theaterController
     */
    public static void makeInitialObjects(TheatreController tc) {
        try {
        tc.createAdminAccount(1,"Boss David", 54, new EMail("david@gmail.com", "1230"));
        } catch (UserExistenceException ignored) {}

        try {
        tc.createActorAccount("Peter", 23, new EMail("peter@gmail.com", "123"), 1200);
        } catch (UserExistenceException ignored) {}
        try {
        tc.createActorAccount("Bence", 33, new EMail("bence@gmail.com", "asd"), 1500);
        } catch (UserExistenceException ignored) {}
        try {
        tc.createActorAccount("Anna", 21, new EMail("anna@gmail.com", "rte"), 2300);
        } catch (UserExistenceException ignored) {}
        try {
        tc.createActorAccount("Balazs", 26, new EMail("balazs@gmail.com", "hgf"), 1900);
        } catch (UserExistenceException ignored) {}
        try {
        tc.createActorAccount("Iosif", 44, new EMail("iosif@gmail.com", "123456"), 2230);
        } catch (UserExistenceException ignored) {}

        try{
        tc.createAuditorium("Grand Hall", 6, 15);
        } catch (ValidationException ignored) {}
        try {
        tc.createAuditorium("Klein Stage", 7, 12);
        } catch (ValidationException ignored) {}

        Map<Actor, String> roles = new HashMap<>();
        roles.putIfAbsent(tc.viewActor(1),"Werther");
        roles.putIfAbsent(tc.viewActor(3),"Luise");
        roles.putIfAbsent(tc.viewActor(4),"Faust");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String date1S = "2024-12-18";
        Date date1;
        try {
            date1 = sdf.parse(date1S);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String date2S = "2024-12-26";
        Date date2;
        try {
            date2 = sdf.parse(date2S);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String date3S = "2025-02-05";
        Date date3;
        try {
            date3 = sdf.parse(date3S);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String date4S = "2025-01-25";
        Date date4;
        try {
            date4 = sdf.parse(date4S);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        try {
        tc.createShow("Lets see if it runs!", date1, 1, roles, 25);
        } catch (EntityNotFoundException ignored) {}
        try {
        tc.createShow("Lets test the sort", date2, 2, roles, 30);
        } catch (EntityNotFoundException ignored) {}
        try {
        tc.createShow("Future show1", date3, 1, roles, 25);
        } catch (EntityNotFoundException ignored) {}
        try{
        tc.createShow("Future show2", date4, 2, roles, 30);
        } catch (EntityNotFoundException ignored) {}

        try {
            tc.createViewerAccount("Victor Ross", 23, new EMail("victor@gmail.com", "123"));
        } catch (UserExistenceException ignored) {}
        List<Integer> seats1 = new ArrayList<>();
        seats1.add(1); seats1.add(2); seats1.add(45); seats1.add(46);
        List<Integer> seats2 = new ArrayList<>();
        seats2.add(3); seats2.add(7); seats2.add(64); seats2.add(84);

        try {
        tc.createOrder(6, 1,  seats1);
        } catch (EntityNotFoundException | ValidationException ignored) {}
        try {
        tc.createOrder(6, 3,  seats2);
        } catch (EntityNotFoundException | ValidationException ignored) {}
    }

    /**
     * Choosing the type of storage that the program uses
     * @return - a string representing a number which indicates what type of storage the user wants
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
//        for setting the auditorium static ID
        int staticID = 0;
        List<Auditorium> allAudit = tc.viewAuditoriums();
        for (Auditorium auditorium : allAudit) {
            if (staticID < auditorium.getID())
                staticID = auditorium.getID();
        }
        Auditorium.setIdCounter(staticID);

//        for setting the order static ID
        staticID = 0;
        List<Order> allOrder = tc.viewOrders();
        for (Order order : allOrder) {
            if (staticID < order.getID())
                staticID = order.getID();
        }
        Order.setIdCounter(staticID);

//        for setting the Show static ID
        staticID = 0;
        List<Show> allShow = tc.viewShows();
        for (Show show : allShow) {
            if(staticID < show.getID())
                staticID = show.getID();
        }
        Show.setIdCounter(staticID);

//        for setting the next Persons static ID
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
