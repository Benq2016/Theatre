package UI;

import Controller.TheatreController;
import Domain.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 * The UI class serves as the user interface for the Theatre Management application.
 * It interacts with the TheatreController to facilitate user actions based on their role
 * (Viewer, Actor, or Admin). The UI provides different functionalities for each type of user,
 * such as viewing shows, managing accounts, and creating orders.
 */
public class UI {

    private final TheatreController theatreController; // Links the UI with the TheatreController

    private final BufferedReader reader; // for input from the console


    /**
     * Constructs a new UI instance with the specified TheatreController.
     *
     * @param theatreController the controller that handles business logic and data operations.
     */
    public UI(TheatreController theatreController) {
        this.theatreController = theatreController;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * The main method that acts as the central hub for all UI interactions.
     * Determines the user's role and directs them to the appropriate UI section (Viewer, Actor, or Admin).
     *
     * @param userEmail the email of the user attempting to log in.
     * @throws IOException if an I/O error occurs during reading.
     */
    public void RUN(EMail userEmail) throws IOException, ParseException {

        while (true) {

            String role = theatreController.login(userEmail);
            Person user = theatreController.viewAccount(userEmail);
            Integer id = user.getID();

            switch (role) {
                case "1":
                    ActorUI(id);
                    break;
                case "2":
                    AdminUI(id);
                    break;
                case "3":
                    ViewerUI(id);
                    break;
                case "0":
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please enter 1, 2, 3 or 0.");
            }
            userEmail = choosingBetweenLoginAndSignup(theatreController);
        }
    }

    /**
     * Displays the Viewer-specific menu and handles user actions related to viewing shows,
     * managing orders, and personal account management.
     *
     * @param id the ID of the Viewer user.
     * @throws IOException if an I/O error occurs during reading.
     */
    private void ViewerUI(Integer id) throws IOException {
        while (true) {
            System.out.println("\nWelcome Viewer");
            System.out.println("What do you want to do?");
            System.out.println("1 - View shows");
            System.out.println("2 - Create order");
            System.out.println("3 - View my orders");
            System.out.println("4 - Manage personal account");
            System.out.println("5 - Delete order");
            System.out.println("6 - Sort show");
            System.out.println("7 - Filter show");
            System.out.println("8 - Sort order");
            System.out.println("9 - Filter order");
            System.out.println("0 - Log out");

            String input = reader.readLine();
            switch (input) {
                case "1":
                    viewAllShows();
                    break;
                case "2":
                    createOrder(id);
                    break;
                case "3":
                    viewMyOrders(id);
                    break;
                case "4":
                    if(managePersonalAccountViewer(id))
                        return;
                    break;
                case "5":
                    deleteOrder(id);
                    break;
                case "6":
                    theatreController.viewShowsSorted().forEach(System.out::println);
                    break;
                case "7":
                    theatreController.viewShowsFiltered().forEach(System.out::println);
                    break;
                case "8":
                    theatreController.viewOrdersSorted().forEach(System.out::println);
                    break;
                case "9":
                    theatreController.viewOrdersFiltered().forEach(System.out::println);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option. Please choose 1, 2, 3, 4, 5, 6, 7, 8, 9 or 0.");
            }
        }
    }

    /***
     * Modifies the details about the Viewer
     *
     * @param id - Viewer ID
     * @return - true if success (in the current state of the code if the Email was modified) else false
     * @throws IOException if an I/O error occurs during reading.
     */
    private Boolean managePersonalAccountViewer(Integer id) throws IOException {
        System.out.println(theatreController.viewViewer(id));
        System.out.println("Enter new name: ");
        String name = reader.readLine();
        System.out.println("Enter new age: ");
        int age = Integer.parseInt(reader.readLine());
        System.out.println("Enter new email address: ");
        String emailAddress = reader.readLine();
        System.out.println("Enter new password: ");
        String password = reader.readLine();
        EMail newMail = new EMail(emailAddress,password);
        System.out.println("Account details changed successfully");
        if (theatreController.manageViewerAccount(id, name,age,newMail))
            return true;
        return false;

    }

    /**
     * Displays all orders associated with the Viewer.
     *
     * @param id the ID of the Viewer.
     * @throws IOException if an I/O error occurs during reading.
     */
    private void viewMyOrders(Integer id) throws IOException {
        List<Order> myOrders = theatreController.viewViewerOrders(id);
        for (Order myOrder : myOrders) {
            System.out.println(myOrder);
        }
    }

    /**
     * Allows the Viewer to create an order by selecting a show, an auditorium,
     * and seat numbers.
     *
     * @param viewerId the ID of the Viewer.
     * @throws IOException if an I/O error occurs during reading.
     */
    private void createOrder(Integer viewerId) throws IOException {
        System.out.println("Id of your order:");
        Integer id = Integer.parseInt(reader.readLine());
        System.out.println("All shows available: ");
        for (Show s : theatreController.viewShows())
            System.out.println(s);
        System.out.println("Choose a show by its id: ");
        Integer showId = Integer.parseInt(reader.readLine());
        Auditorium auditorium = theatreController.getAuditoriumByShow(showId);
        System.out.println(theatreController.viewAuditorium(auditorium.getID()));
        int seatNr = -1;
        List<Integer> seats = new ArrayList<>();
        while(seatNr != 0){
            System.out.println("Choose as many seat numbers as you like (press 0 to stop)");
            seatNr = Integer.parseInt(reader.readLine());
            if (seatNr != 0){
                seats.add(seatNr);
            }
        }

        theatreController.createOrder(id,viewerId,showId,seats);
        System.out.println("Order successfully created");
    }

    /**
     * Displays all available shows in the system.
     *
     * @throws IOException if an I/O error occurs during reading.
     */
    private void viewAllShows() throws IOException {
        List<Show> allShows = theatreController.viewShows();
        for(Show s : allShows)
            System.out.println(s);
    }

    /**
     * Displays the Actor-specific menu and handles actions such as viewing upcoming shows
     * and managing their personal account.
     *
     * @param id the ID of the Actor user.
     * @throws IOException if an I/O error occurs during reading.
     */
    private void ActorUI(Integer id) throws IOException {
        while (true) {
            System.out.println("\nWelcome Actor");
            System.out.println("What do you want to do?");
            System.out.println("1 - View upcoming shows");
            System.out.println("2 - Manage personal account");
            System.out.println("0 - Log out");

            String input = reader.readLine();
            switch (input) {
                case "1":
                    viewUpcomingShows(id);
                    break;
                case "2":
                    if(manageActorAccount(id))
                        return;
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option. Please choose 1, 2 or 0.");
            }
        }
    }

    /***
     * Modifies the details about the Actor
     *
     * @param id - actor ID
     * @return - true if success (in the current state of the code if the Email was modified) else false
     * @throws IOException if an I/O error occurs during reading.
     */
    private Boolean manageActorAccount(Integer id) throws IOException {
        System.out.println(theatreController.viewActor(id));
        System.out.println("Enter new name: ");
        String name = reader.readLine();
        System.out.println("Enter new age: ");
        int age = Integer.parseInt(reader.readLine());
        System.out.println("Enter new email address: ");
        String emailAddress = reader.readLine();
        System.out.println("Enter new password: ");
        String password = reader.readLine();
        EMail newMail = new EMail(emailAddress,password);
        System.out.println("Account details changed successfully");
        if (theatreController.manageActorAccount(id,name,age,newMail))
            return true;
        return false;

    }

    /**
     * Displays all upcoming shows associated with the Actor.
     *
     * @param id the ID of the Actor.
     * @throws IOException if an I/O error occurs during reading.
     */
    private void viewUpcomingShows(Integer id) throws IOException {
        System.out.println("Listing all your upcoming shows");
        List<Show> myShows = theatreController.viewActorShows(id);
        for (Show myShow : myShows)
            System.out.println(myShow);
    }

    /**
     * Displays the Admin-specific menu and handles actions such as managing actors,
     * shows, auditoriums, and their personal account.
     *
     * @param id the ID of the Admin user.
     * @throws IOException if an I/O error occurs during reading.
     * @throws ParseException if there is an error parsing user input or date-related fields.
     */
    private void AdminUI(Integer id) throws IOException, ParseException {
        while (true) {
            System.out.println("\nWelcome Admin");
            System.out.println("What do you want to do?");
            System.out.println("1 - Work with actors");
            System.out.println("2 - Work with shows and auditoriums");
            System.out.println("3 - Manage personal account");
            System.out.println("0 - Log out");

            String input = reader.readLine();
            switch (input) {
                case "1":
                    manageActors();
                    break;
                case "2":
                    manageShowsAndAuditoriums();
                    break;
                case "3":
                    if (managePersonalAccountAdmin(id))
                        return;

                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option. Please choose 1, 2, 3, or 0.");
            }
        }
    }

    /***
     * Modifies the details about the Admin
     *
     * @param id - admin ID
     * @return - true if success (in the current state of the code if the Email was modified) else false
     * @throws IOException if an I/O error occurs during reading.
     */
    private Boolean managePersonalAccountAdmin(Integer id) throws IOException {
        System.out.println(theatreController.viewAdmin(id));
        System.out.println("Enter new name: ");
        String name = reader.readLine();
        System.out.println("Enter new age: ");
        int age = Integer.parseInt(reader.readLine());
        System.out.println("Enter new email address: ");
        String emailAddress = reader.readLine();
        System.out.println("Enter new password: ");
        String password = reader.readLine();
        EMail newMail = new EMail(emailAddress,password);
        System.out.println("Account details changed successfully");
        if (theatreController.manageAdminAccount(id, name,age,newMail))
            return true;
        return false;

    }

    /**
     * Allows the Admin to manage actors within the theater system, such as adding or viewing actors.
     *
     * @throws IOException if an I/O error occurs during reading.
     */
    private void manageActors() throws IOException {
        while (true) {
            System.out.println("\nWork with actors - options");
            System.out.println("1 - Hire an actor");
            System.out.println("2 - Fire an actor");
            System.out.println("3 - Change actor salary");
            System.out.println("4 - List all actors");
            System.out.println("0 - Back");

            String option = reader.readLine();
            switch (option) {
                case "1":
                    hireActor();
                    break;
                case "2":
                    fireActor();
                    break;
                case "3":
                    changeActorSalary();
                    break;
                case "4":
                    listAllActors();
                    break;
                case "0":
                    return;  // Go back to CEO menu in CeoUi
                default:
                    System.out.println("Invalid option. Please choose 1, 2, 3, 4, or 0.");
            }
        }
    }

    /**
     * Allows the Admin to manage shows and auditoriums, including adding new shows or viewing current ones.
     *
     * @throws IOException if an I/O error occurs during reading.
     */
    private void manageShowsAndAuditoriums() throws IOException, ParseException {
        while (true) {
            System.out.println("\nManage shows and auditoriums - options");
            System.out.println("1 - Create show");
            System.out.println("2 - Delete show");
            System.out.println("3 - Create auditorium");
            System.out.println("4 - Delete auditorium");
            System.out.println("5 - List all shows");
            System.out.println("6 - List all auditoriums");
            System.out.println("0 - Back");

            String option = reader.readLine();
            switch (option) {
                case "1":
                    createShow();
                    break;
                case "2":
                    deleteShow();
                    break;
                case "3":
                    createAuditorium();
                    break;
                case "4":
                    deleteAuditorium();
                    break;
                case "5":
                    listAllShows();
                    break;
                case "6":
                    listAllAuditoriums();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option. Please choose 1, 2, 3, 4, 5, 6 or 0.");
            }
        }

    }

    /**
    * Lists all available auditoriums
     *
     * @throws IOException if an I/O error occurs during reading.
    * */
    private void listAllAuditoriums() throws IOException {
        System.out.println("List of all the auditoriums");
        System.out.println(theatreController.viewAuditoriums());
    }

    /**
     * Lists all shows
     *
     * @throws IOException if an I/O error occurs during reading.
     * */
    private void listAllShows() throws IOException {
        System.out.println("List of all the shows");
        System.out.println(theatreController.viewShows());
    }

    /**
     * Deletes an auditorium
     *
     * @throws IOException if an I/O error occurs during reading.
     * */
    private void deleteAuditorium() throws IOException {
        System.out.println("\nDeleting an auditorium");
        System.out.println(theatreController.viewAuditoriums());
        System.out.println("Id of the auditorium you want to delete:");
        Integer audId = Integer.parseInt(reader.readLine());
        theatreController.deleteAuditorium(audId);

    }

    /**
     * Cancels a show
     *
     * @throws IOException if an I/O error occurs during reading.
     * */
    private void deleteShow() throws IOException {
        System.out.println("\nDeleting shows");
        System.out.println(theatreController.viewShows());
        System.out.println("Id of the show you want to delete:");
        Integer showId = Integer.parseInt(reader.readLine());

        theatreController.deleteShow(showId);
        System.out.println("Show successfully deleted.");
    }

    /***
     *Creates a new auditorium
     *
     * @throws IOException if an I/O error occurs during reading.
     */
    private void createAuditorium() throws IOException {
        System.out.println("\nCreating auditorium");
        System.out.println("Auditorium id:");
        int id = Integer.parseInt(reader.readLine());
        System.out.println("Auditorium name:");
        String name = reader.readLine();
        System.out.println("Number of rows");
        int nrRows = Integer.parseInt(reader.readLine());
        System.out.println("Number of columns");
        int nrCols = Integer.parseInt(reader.readLine());

        theatreController.createAuditorium(id, name, nrRows, nrCols);
    }


    /***
     * Creates a new show - after this the Viewer can buy tickets for the show
     * @throws IOException if an I/O error occurs during reading.
     */
    private void createShow() throws IOException, ParseException {
        System.out.println("\nCreating show");

        System.out.print("Show ID: ");
        int showId = Integer.parseInt(reader.readLine());

        System.out.print("Show title: ");
        String showTitle = reader.readLine();

        // Display available auditoriums and choose one
        System.out.println("Available auditoriums:");
        System.out.println(theatreController.viewAuditoriums());
        System.out.print("Choose a valid auditorium by its ID: ");
        int auditoriumId = Integer.parseInt(reader.readLine());

        // Create a map for actors and their roles
        Map<Actor, String> roleMap = new HashMap<>();
        System.out.println("You can add as many actors as you want. Enter '0' to finish adding actors.");

        Integer id;
        while (true) {
            System.out.println("\nAll available actors:");
            System.out.println(theatreController.viewActors());
            System.out.print("Select the ID of an actor (or '0' to stop): ");
            id = Integer.parseInt(reader.readLine());

            if (id == 0) {
                break;  // Stop adding actors if user enters 0
            }

            Actor actor = theatreController.viewActor(id);
            if (actor == null) {
                System.out.println("Invalid actor ID. Please try again.");
                continue;  // Skip this iteration if the actor ID is invalid
            }

            System.out.print("Role for actor " + actor.getName() + ": ");
            String role = reader.readLine();
            roleMap.put(actor, role);
        }

        System.out.print("Enter the date of the show (e.g., YYYY-MM-DD): ");
        String showDate = reader.readLine();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(showDate);

        System.out.println("The price of the show: ");
        int price = Integer.parseInt(reader.readLine());



        // Call createShow with the collected parameters
        theatreController.createShow(showId, showTitle, date, auditoriumId, roleMap, price );
        System.out.println("Show created successfully.");
    }

    /***
     * Creates a new actor
     *
     * @throws IOException if an I/O error occurs during reading.
     */
    private void hireActor() throws IOException {
        System.out.println("\nHiring a new actor");

        System.out.print("Actor ID: ");
        int actorId = Integer.parseInt(reader.readLine());

        System.out.print("Actor Name: ");
        String actorName = reader.readLine();

        System.out.print("Actor Age: ");
        int actorAge = Integer.parseInt(reader.readLine());

        System.out.print("Actor Email address: ");
        String actorEmailAddress = reader.readLine();

        System.out.print("Actor Email password: ");
        String actorEmailPassword = reader.readLine();

        System.out.print("Actor Salary: ");
        int actorSalary = Integer.parseInt(reader.readLine());

        theatreController.createActorAccount(actorId, actorName, actorAge,
                new EMail(actorEmailAddress, actorEmailPassword), actorSalary);
        System.out.println("Actor hired successfully.");
    }

    /***
     * Deletes an actor
     *
     * @throws IOException if an I/O error occurs during reading.
     */
    private void fireActor() throws IOException {
        System.out.print("\nEnter the ID of the actor to fire: ");
        int actorId = Integer.parseInt(reader.readLine());
        theatreController.deleteActorAccount(actorId);
        System.out.println("Actor fired successfully.");
    }

    /**Changes the salary of an actor
     *
     * @throws IOException if an I/O error occurs during reading.
     * */
    private void changeActorSalary() throws IOException {
        System.out.print("\nEnter the ID of the actor whose salary you want to change: ");
        int actorId = Integer.parseInt(reader.readLine());

        System.out.print("Enter the new salary: ");
        int newSalary = Integer.parseInt(reader.readLine());

        theatreController.changeSalary(actorId, newSalary);
        System.out.println("Actor's salary updated successfully.");
    }

    /**
     * Lists all the actors of the theatre
     * */
    private void listAllActors() {
        System.out.println("\nList of all actors:");
        theatreController.viewActors().forEach(System.out::println);
    }

    /***
     * Deletes an order based on its ID and refunds for the Viewer(a simple print)
     *
     * @param id - the id of the Viewer
     * @throws IOException
     */
    private void deleteOrder(Integer id) throws IOException {
        viewMyOrders(id);
        System.out.println("Id of the order you want to delete: ");
        Integer orderId = Integer.parseInt(reader.readLine());
        int refund = theatreController.deleteOrder(orderId);
        System.out.println("You have been refunded with:"+refund);
    }

    /**
     * Facilitates login or signup options for the user.
     *
     * @param tc the TheatreController instance managing domain interactions.
     * @return the email of the newly logged in or signed-up user.
     * @throws IOException if an I/O error occurs during reading.
     */
    public static EMail choosingBetweenLoginAndSignup(TheatreController tc) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Welcome to the theater management system");
        System.out.println("If you want to proceed please chose an option");
        System.out.println("1 - Login");
        System.out.println("2 - Sign up");
        System.out.println("0 - Exit");
        String option = reader.readLine();
        while (true){
            switch (option) {
                case "1":
                    return login(tc);

                case "2":
                    return signUp(tc);

                case "0":
                    System.out.println("Good bye!");
                    System.exit(0);

                default:
                    System.out.println("Invalid option. Please choose 1 or 2.");

            }
        }

    }

    /**
     * Allows a Viewer to sign up and create a new account.
     *
     * @param tc the TheatreController instance managing domain interactions.
     * @return the email of the signed-up user.
     * @throws IOException if an I/O error occurs during reading.
     */
    private static EMail signUp(TheatreController tc) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        EMail newMail;
        while (true) {

            System.out.println("Welcome new User");
            System.out.println("Please create you account as follows:");
            System.out.println("Choose an Id:");
            Integer id = Integer.parseInt(reader.readLine());
            System.out.println("Provide us with your name:");
            String name = reader.readLine();
            System.out.println("Your age:");
            int age = Integer.parseInt(reader.readLine());
            System.out.println("Your emailAddress:");
            String emailAddress = reader.readLine();
            System.out.println("Your password:");
            String password = reader.readLine();
            newMail = new EMail(emailAddress, password);

            boolean success = tc.createViewerAccount(id, name, age, newMail);
            if (success) {
                break;
            }
            else {
                System.out.println("Try again please.");
            }
        }


        return newMail;
    }

    /**
     * Allows a user to log in to the system.
     *
     * @param tc the TheatreController instance managing domain interactions.
     * @return the email of the logged-in user.
     * @throws IOException if an I/O error occurs during reading.
     */
    private static EMail login(TheatreController tc) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        EMail eMail;
        System.out.println("Welcome Back! Please type in your email for authentication.");
        while (true) {
            System.out.println("Email Address:");
            String emailAddress = reader.readLine();
            System.out.println("Password:");
            String password = reader.readLine();
            eMail = new EMail(emailAddress, password);
            String userNumber = tc.login(eMail);

            if (Objects.equals(userNumber, "0")) {
                System.out.println("Invalid Email, try again...");
            }
            else {
                System.out.println("You have successfully logged in!");
                break;
            }
        }
        return eMail;

    }

}

