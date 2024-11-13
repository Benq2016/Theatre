package UserInterface;

import ControllerService.TheatreController;
import Domain.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The UI class provides a command-line interface for users to interact with a theater management system.
 * Different user roles (Viewer, Actor, CEO) have access to different functionalities through their respective menus.
 */
public class UI {
    private final TheatreController theatreController;
    private final BufferedReader reader;

    /**
     * Constructs a UI object with a specified TheatreController.
     *
     * @param theatreController the controller that manages interactions with the domain layer.
     */
    public UI(TheatreController theatreController) {
        this.theatreController = theatreController;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * The main method that acts as the central hub for all UI interactions.
     * Determines the user's role and directs them to the appropriate UI section (Viewer, Actor, or CEO).
     *
     * @param userEmail the email of the user attempting to log in.
     * @throws IOException if an I/O error occurs during reading.
     */
    public void RUN(EMail userEmail) throws IOException {

        while (true) {

            String role = String.valueOf(theatreController.login(userEmail));

            switch (role) {
                case "1":
                    ActorUI(userEmail);
                    break;
                case "2":
                    CeoUi(userEmail);
                    break;
                case "3":
                    ViewerUI(userEmail);
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
     * Displays Viewer options and handles Viewer interactions based on their input.
     *
     * @param viewerEmail the email of the Viewer currently logged in.
     * @throws IOException if an I/O error occurs during reading.
     */
    private void ViewerUI(EMail viewerEmail) throws IOException {
        while (true) {
            System.out.println("\nWelcome Viewer");
            System.out.println("What do you want to do?");
            System.out.println("1 - View shows");
            System.out.println("2 - Create order");
            System.out.println("3 - View my orders");
            System.out.println("4 - Manage personal account");
            System.out.println("0 - Log out");

            String input = reader.readLine();
            switch (input) {
                case "1":
                    viewAllShows();
                    break;
                case "2":
                    createOrder(viewerEmail);
                    break;
                case "3":
                    viewMyOrders(viewerEmail);
                    break;
                case "4":
                    if(managePersonalAccountViewer(viewerEmail))
                        return;
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option. Please choose 1, 2, 3, 4 or 0.");
            }
        }
    }

    /**
     * Manages the personal account information for a Viewer.
     *
     * @param viewerEmail the email of the Viewer whose account is being managed.
     * @return true if the email was changed, false otherwise.
     * @throws IOException if an I/O error occurs during reading.
     */
    private Boolean managePersonalAccountViewer(EMail viewerEmail) throws IOException {
        System.out.println(theatreController.viewAccount(viewerEmail));
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
        if (theatreController.manageViewerAccount(name,age,viewerEmail,newMail))
            return true;
        return false;

    }

    /**
     * Displays the orders placed by the Viewer.
     *
     * @param viewerEmail the email of the Viewer whose orders are being viewed.
     * @throws IOException if an I/O error occurs during reading.
     */
    private void viewMyOrders(EMail viewerEmail) throws IOException {
        List<Order> myOrders = theatreController.viewMyOrders(viewerEmail);
        for (Order myOrder : myOrders) {
            System.out.println(myOrder);
        }
    }

    /**
     * Allows the Viewer to create a new order by selecting a show and seats.
     *
     * @param viewerEmail the email of the Viewer creating the order.
     * @throws IOException if an I/O error occurs during reading.
     */
    private void createOrder(EMail viewerEmail) throws IOException {
        System.out.println("Id of your order:");
        Integer id = Integer.parseInt(reader.readLine());
        System.out.println("All shows available: ");
        for (Show s : theatreController.viewShows())
            System.out.println(s);
        System.out.println("Choose a show by its id: ");
        Integer showId = Integer.parseInt(reader.readLine());
        Auditorium auditorium = theatreController.getAuditoriumByShowId(showId);
        System.out.println(auditorium.viewAuditoriumWithoutLayout());
        int seatNr = -1;
        List<Integer> seats = new ArrayList<>();
        while(seatNr != 0){
            System.out.println("Choose as many seat numbers as you like (press 0 to stop)");
            seatNr = Integer.parseInt(reader.readLine());
            if (seatNr != 0){
                seats.add(seatNr);
            }
        }

        theatreController.createOrder(id,showId,viewerEmail,seats);
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
     * Displays the Actor UI, allowing Actors to view their upcoming shows or manage their account.
     *
     * @param actorMail the email of the Actor currently logged in.
     * @throws IOException if an I/O error occurs during reading.
     */
    private void ActorUI(EMail actorMail) throws IOException {
        while (true) {
            System.out.println("\nWelcome Actor");
            System.out.println("What do you want to do?");
            System.out.println("1 - View upcoming shows");
            System.out.println("2 - Manage personal account");
            System.out.println("0 - Log out");

            String input = reader.readLine();
            switch (input) {
                case "1":
                    viewUpcomingShows(actorMail);
                    break;
                case "2":
                    if(manageActorAccount(actorMail))
                        return;
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option. Please choose 1, 2 or 0.");
            }
        }
    }

    /**
     * Manages the personal account information for an Actor.
     *
     * @param actorEmail the email of the Actor whose account is being managed.
     * @return true if the email was changed, false otherwise.
     * @throws IOException if an I/O error occurs during reading.
     */
    private Boolean manageActorAccount(EMail actorEmail) throws IOException {
        System.out.println(theatreController.viewAccount(actorEmail));
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
        if (theatreController.manageActorAccount(name,age,actorEmail,newMail))
            return true;
        return false;

    }

    /***
     * Lists all upcoming shows in which the actor is playing
     *
     * @param actorMail the actors mail
     * @throws IOException if an I/O error occurs during reading.
     */
    private void viewUpcomingShows(EMail actorMail) throws IOException {
        System.out.println("Listing all your upcoming shows");
        List<Show> myShows = theatreController.viewMyShows(actorMail);
        for (Show myShow : myShows)
            System.out.println(myShow);
    }

    /**
     * Displays the CEO UI, allowing the CEO to manage actors, shows, auditoriums, or their own account.
     *
     * @param ceoEmail the email of the CEO currently logged in.
     * @throws IOException if an I/O error occurs during reading.
     */
    private void CeoUi(EMail ceoEmail) throws IOException {
        while (true) {
            System.out.println("\nWelcome CEO");
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
                    if (managePersonalAccountCeo(ceoEmail))
                        return;

                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option. Please choose 1, 2, 3, or 0.");
            }
        }
    }

    /**
     * Allows the CEO to manage their personal account information.
     *
     * @param ceoEmail the email of the CEO whose account is being managed.
     * @return true if the email was changed, false otherwise.
     * @throws IOException if an I/O error occurs during reading.
     */
    private Boolean managePersonalAccountCeo(EMail ceoEmail) throws IOException {
        System.out.println(theatreController.viewAccount(ceoEmail));
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
        if (theatreController.manageCeoAccount(name,age,ceoEmail,newMail))
            return true;
        return false;

    }

    /**
     * Allows the CEO to manage actors within the theater system, such as adding or viewing actors.
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
     * Allows the CEO to manage shows and auditoriums, including adding new shows or viewing current ones.
     *
     * @throws IOException if an I/O error occurs during reading.
     */
    private void manageShowsAndAuditoriums() throws IOException {
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
        System.out.println(theatreController.viewAllAuditoriums());
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
        System.out.println(theatreController.viewAllAuditoriums());
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

        theatreController.ceoDeleteShow(showId);
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
    private void createShow() throws IOException {
        System.out.println("\nCreating show");

        System.out.print("Show ID: ");
        int showId = Integer.parseInt(reader.readLine());

        System.out.print("Show title: ");
        String showTitle = reader.readLine();

        // Display available auditoriums and choose one
        System.out.println("Available auditoriums:");
        System.out.println(theatreController.viewAllAuditoriums());
        System.out.print("Choose a valid auditorium by its ID: ");
        int auditoriumId = Integer.parseInt(reader.readLine());

        Auditorium auditorium = theatreController.viewAuditorium(auditoriumId);
        if (auditorium == null) {
            System.out.println("Invalid auditorium ID.");
            return;
        }

        // Create a map for actors and their roles
        Map<Actor, String> roleMap = new HashMap<>();
        System.out.println("You can add as many actors as you want. Enter '0' to finish adding actors.");

        Integer id;
        while (true) {
            System.out.println("\nAll available actors:");
            System.out.println(theatreController.viewAllActors());
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

        // Call createShow with the collected parameters
        theatreController.createShow(showId, showTitle, auditorium, roleMap, showDate);
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

        theatreController.ceoHireActor(actorId, actorName, actorAge,
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
        theatreController.ceoFireActor(actorId);
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

        theatreController.ceoChangeSalary(actorId, newSalary);
        System.out.println("Actor's salary updated successfully.");
    }

    /**
     * Lists all the actors of the theatre
     * */
    private void listAllActors() {
        System.out.println("\nList of all actors:");
        theatreController.viewAllActors().forEach(System.out::println);
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
            Integer userNumber = tc.login(eMail);

            if (userNumber == 0 ) {
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
