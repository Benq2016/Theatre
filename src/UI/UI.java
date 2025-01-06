package UI;

import Controller.TheatreController;
import Domain.*;
import Exceptions.*;

import javax.security.sasl.SaslException;
import javax.swing.text.View;
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

    private final TheatreController theatreController;

    private final BufferedReader reader;


    /**
     * Constructs a new UI instance with the specified TheatreController.
     * @param theatreController the controller that handles business logic and data operations.
     */
    public UI(TheatreController theatreController) {
        this.theatreController = theatreController;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * The main method that acts as the central hub for all UI interactions.
     * Determines the user's role and directs them to the appropriate
     * UI section (Viewer, Actor, or Admin).
     * @param userEmail the email of the user attempting to log in.
     * @throws IOException if an I/O error occurs during reading.
     */
    public void RUN(EMail userEmail) throws IOException, ParseException {

        while (true) {

            String role = theatreController.login(userEmail);
            Integer id = theatreController.viewAccount(userEmail).getID();

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
     * @param id the ID of the Viewer user.
     * @throws IOException if an I/O error occurs during reading.
     */
    private void ViewerUI(Integer id) throws IOException {
        while (true) {
            System.out.println("\nWelcome Viewer");
            System.out.println("What do you want to do?");
            System.out.println("1 - View upcoming shows");
            System.out.println("2 - Create order");
            System.out.println("3 - View my orders");
            System.out.println("4 - Manage personal account");
            System.out.println("5 - Delete order");
            System.out.println("6 - Sort show by date");
            System.out.println("7 - Sort order by date");
            System.out.println("8 - Filter order by date");
            System.out.println("0 - Log out");

            String input = reader.readLine();
            switch (input) {
                case "1":
                    filterShows();
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
                    sortShows();
                    break;
                case "7":
                    sortOrders(id);
                    break;
                case "8":
                    filterOrders(id);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option. Please choose " +
                            "1, 2, 3, 4, 5, 6, 7, 8 or 0.");
            }
        }
    }

    /**
     * Modifies the details about the Viewer
     * @param id - Viewer ID
     * @return - true if success (in the current state of the
     * code if the Email was modified) else false
     * @throws IOException if an I/O error occurs during reading.
     */
    private Boolean managePersonalAccountViewer(Integer id) throws IOException {
//        System.out.println(theatreController.viewViewer(id));
        Viewer viewer = theatreController.viewViewer(id);
        System.out.println("Old account data:");
        System.out.println("Name: " + viewer.getName() + ", age: " + viewer.getAge() +
                ", email address: " + viewer.getEmail().getEmailAddress() + ", password: " + viewer.getEmail().getPassword());
        System.out.print("Enter new name: ");
        String name;

        while (true) {
            try {
                name = reader.readLine();
                if (name.length() > 2 && name.matches("[a-zA-Z ]+"))
                    break;
                else if (name.length() <= 2)
                    throw new InvalidStringLenghtException("Your name should contain minim 3 letters");
                else if (!name.matches("[a-zA-Z ]+"))
                    throw new InvalidFormatException("Your name should contain only letters, not other characters");
            } catch (InvalidStringLenghtException | InvalidFormatException e) {
                System.out.println("Error occurred: " + e.getMessage());
                System.out.println("Enter new name: ");
            }
        }

        System.out.print("Enter new age: ");
        int age;
        while (true) {
            try {
                age = Integer.parseInt(reader.readLine());
                if (age <= 16)
                    throw new NumberFormatException();
                break;
            }catch (NumberFormatException e) {
                System.out.println("Invalid Input. Input must be a positive integer greater than or equal to 16");
                System.out.println("Enter new age: ");
            }
        }
        System.out.println("Enter new email address: ");
        String emailAddress;
        while (true) {
            try {
                emailAddress = reader.readLine();
                if (validEmailAddress(emailAddress))
                    break;
                else
                    throw new InvalidEmailFormatException("Email address must contain '@' symbol");
            }catch(InvalidEmailFormatException e){
                System.out.println("Problem with your Email address: " + e.getMessage());
                System.out.println("Enter new email address: ");
            }
        }
        System.out.println("Enter new password: ");
        String password = reader.readLine();
        EMail newMail = new EMail(emailAddress,password);

        try {
            Viewer newViewer = theatreController.manageViewerAccount(id, name, age, newMail);
            System.out.println("Account details to " + newViewer.getName() + " changed successfully!");
            return true;
        } catch (UserExistenceException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * @param email - the email that should be verified
     * returns true if there is exactly 1 @ symbol in the email address, false otherwise
     * */
    private static boolean validEmailAddress(String email) {
        int atCount = email.length() - email.replace("@", "").length();
        return atCount == 1;
    }

    /**
     * Displays all orders associated with the Viewer.
     * @param id the ID of the Viewer.
     */
    private void viewMyOrders(Integer id) {
        List<Order> myOrders = theatreController.viewViewerOrders(id);
        for (Order myOrder : myOrders) {

            List<Ticket> myTickets = myOrder.getTickets();
            System.out.println("Order ID: " + myOrder.getID());
            System.out.println("Your tickets: ");
            for (Ticket ticket : myTickets){
                System.out.println("    Ticket Nr." + ticket.getID() +
                        ", for the Show: " + ticket.getShowName() +
                        ", in the auditorium: " + ticket.getAuditoriumName() +
                        ", Seat: " + ticket.getSeat());
            }
        }
    }

    /**
     * Allows the Viewer to create an order by selecting a show, an auditorium,
     * and seat numbers.
     * @param viewerId the ID of the Viewer.
     */
    private void createOrder(Integer viewerId) {
    try {
        System.out.println("All shows available: ");
        filterShows();
        Integer showId;


        List<Show> allShows = theatreController.viewShowsFiltered();
        List<Integer> allShowsID = new ArrayList<>();
        for (Show show : allShows) {
            allShowsID.add(show.getID());
        }
        boolean showExists = false;
        while(true){
            System.out.println("Choose a show by its id: ");
            try {
                showId = Integer.parseInt(reader.readLine());
                for (int showID : allShowsID) {
                    if(showID == showId)
                        showExists = true;
                }
                if(showExists)
                    break;
                else{
                    System.out.println("The show with this Id does not exists. Please select a valid one");
                }

            }catch (NumberFormatException e){
                System.out.println("Invalid input. Input must be an integer");
            }
        }
        //we get the auditorium without occupied seats, and we
        // overwrite the auditorium with the one where the seats are occupied
        Auditorium auditorium = theatreController.getAuditoriumByShow(showId);
        auditorium = theatreController.getAuditoriumWithOccupiedSeats(showId,auditorium.getID());

        if (auditorium == null)
            throw new EntityNotFoundException("There is no auditorium for the selected show");

        System.out.println(auditorium);
        int seatNr = -1;
        List<Integer> seats = new ArrayList<>();
        while (seatNr != 0) {
            System.out.println("Choose as many seat numbers as you like (press 0 to stop)");
            while(true){
                try {
                    seatNr = Integer.parseInt(reader.readLine());
                    break;
                }catch (NumberFormatException e){
                    System.out.println("Invalid input. Input must be an integer");
                    System.out.println("Choose a seat (integer): ");
                }
            }
            if (seatNr != 0) {
                seats.add(seatNr);
            }
        }

        try {
            Order newOrder = theatreController.createOrder(viewerId, showId, seats);
            System.out.println("Order: " + newOrder.getID() + " created successfully!");
        } catch (EntityNotFoundException | ValidationException e) {
            System.out.println("Error: " + e.getMessage());
        }

    } catch (EntityNotFoundException e){
        System.out.println("Error occurred: " + e.getMessage());
    } catch (Exception e) {
        throw new RuntimeException("An unexpected exception occurred: " + e.getMessage());
    }

    }

    /**
     * Displays all available shows in the system.
     */
    private void viewAllShows() {
        List<Show> allShows = theatreController.viewShows();
        for(Show s : allShows) {
            Map<Actor,String> allActors = s.getRoles();
            System.out.println("Show ID: " + s.getID() + ", title: " + s.getTitle() + ", date: " + new SimpleDateFormat("yyyy-MM-dd").format(s.getDate()));
            System.out.println("Casting: ");
            for(Actor actor : allActors.keySet()) {
                System.out.println("    Actor: " + actor.getName() + ", role: " + allActors.get(actor));
            }
            System.out.println();
        }
        System.out.println("-------------------------------------------------------");
    }

    /**
     * Displays the Actor-specific menu and handles actions such as viewing upcoming shows
     * and managing their personal account.
     * @param id the ID of the Actor user.
     * @throws IOException if an I/O error occurs during reading.
     */
    private void ActorUI(Integer id) throws IOException {
        while (true) {
            System.out.println("\nWelcome Actor");
            System.out.println("What do you want to do?");
            System.out.println("1 - View my upcoming shows");
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

    /**
     * Modifies the details about the Actor
     * @param id - actor ID
     * @return - true if success (in the current state of the code
     * if the Email was modified) else false
     * @throws IOException if an I/O error occurs during reading.
     */
    private Boolean manageActorAccount(Integer id) throws IOException {
        Actor self = theatreController.viewActor(id);
        System.out.println("ID: " + self.getID() + ", name: " + self.getName() +
                ", age: " + self.getAge() +
                ", salary: " + self.getSalary() + ", email address: " +
                self.getEmail().getEmailAddress() +
                "email password: " + self.getEmail().getPassword());

        System.out.println("Enter new name: ");
        String name;

        while (true) {
            try {
                name = reader.readLine();
                if (name.length() > 2 && name.matches("[a-zA-Z ]+"))
                    break;
                else if (name.length() <= 2)
                    throw new InvalidStringLenghtException("Your name should contain minim 3 letters");
                else if (!name.matches("[a-zA-Z ]+"))
                    throw new InvalidFormatException("Your name should contain only letters, not other characters");
            } catch (InvalidStringLenghtException | InvalidFormatException e) {
                System.out.println("Error occurred: " + e.getMessage());
                System.out.println("Enter new name: ");
            }
        }

        System.out.println("Enter new age: ");
        int age;
        while (true) {
            try {
                age = Integer.parseInt(reader.readLine());
                if (age <= 18)
                    throw new NumberFormatException();
                break;
            }catch (NumberFormatException e) {
                System.out.println("Invalid Input. Input must be a positive integer greater than or equal to 18");
                System.out.println("Enter new age: ");
            }
        }
        System.out.println("Enter new email address: ");
        String emailAddress;
        while (true) {
            try {
                emailAddress = reader.readLine();
                if (validEmailAddress(emailAddress))
                    break;
                else
                    throw new InvalidEmailFormatException("Email address must contain '@' symbol");
            }catch(InvalidEmailFormatException e){
                System.out.println("Problem with your Email address: " + e.getMessage());
                System.out.println("Enter new email address: ");
            }
        }
        System.out.println("Enter new password: ");
        String password = reader.readLine();
        EMail newMail = new EMail(emailAddress,password);
        try {
            Actor newActor = theatreController.manageActorAccount(id, name,age,newMail);
            System.out.println("Account details to: " + newActor.getName() + " changed successfully!");
            return true;
        }catch (UserExistenceException e ){
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Displays all upcoming shows associated with the Actor.
     * @param id the ID of the Actor.
     */
    private void viewUpcomingShows(Integer id) {
        System.out.println("Listing all your upcoming shows: \n");
        List<Show> myShows = theatreController.viewActorShows(id);
        for (Show myShow : myShows)
            System.out.println("Show With ID: "+myShow.getID()+"\n"+
                    "Title of the show: "+ myShow.getTitle() + "\n"+
                    "Date: " + new SimpleDateFormat("yyyy-MM-dd").format(myShow.getDate()) + "\n" +
                    "Auditorium ID: " + myShow.getAudit().getID() + ", Auditorium name: " + myShow.getAudit().getName() + "\n" +
                    "---------------------------------------------\n");
    }
    /**
     * Displays the Admin-specific menu and handles actions such as managing actors,
     * shows, auditoriums, and their personal account.
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

    /**
     * Modifies the details about the Admin
     * @param id - admin ID
     * @return - true if success (in the current state of the
     * code if the Email was modified) else false
     * @throws IOException if an I/O error occurs during reading.
     */
    private Boolean managePersonalAccountAdmin(Integer id) throws IOException {
        Admin self = theatreController.viewAdmin(id);
        System.out.println("ID: " + self.getID() + ", name: " + self.getName() +
                ", age: " + self.getAge() +
                ", email address: " + self.getEmail().getEmailAddress());
        System.out.println("Enter new name: ");
        String name;

        while (true) {
            try {
                name = reader.readLine();
                if (name.length() > 2 && name.matches("[a-zA-Z ]+"))
                    break;
                else if (name.length() <= 2)
                    throw new InvalidStringLenghtException("Your name should contain minim 3 letters");
                else if (!name.matches("[a-zA-Z ]+"))
                    throw new InvalidFormatException("Your name should contain only letters, not other characters");
            } catch (InvalidStringLenghtException | InvalidFormatException e) {
                System.out.println("Error occurred: " + e.getMessage());
                System.out.println("Enter new name: ");
            }
        }

        System.out.println("Enter new age: ");
        int age;
        while (true) {
            try {
                age = Integer.parseInt(reader.readLine());
                if (age < 20)
                    throw new NumberFormatException();
                break;
            }catch (NumberFormatException e) {
                System.out.println("Invalid Input. Input must be a positive integer greater than 20 years");
                System.out.println("Enter new age: ");
            }
        }
        System.out.println("Enter new email address: ");
        String emailAddress;
        while (true) {
            try {
                emailAddress = reader.readLine();
                if (validEmailAddress(emailAddress))
                    break;
                else
                    throw new InvalidEmailFormatException("Email address must contain '@' symbol");
            }catch(InvalidEmailFormatException e){
                System.out.println("Problem with your Email address: " + e.getMessage());
                System.out.println("Enter new email address: ");
            }
        }
        System.out.println("Enter new password: ");
        String password = reader.readLine();
        EMail newMail = new EMail(emailAddress,password);
        try {
            Admin newAdmin = theatreController.manageAdminAccount(id, name, age, newMail);
            System.out.println("Account details to: " + newAdmin.getName() + " changed successfully!");
            return true;

        } catch (UserExistenceException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Allows the Admin to manage actors within the theater system, such as adding or viewing actors.
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
     * Allows the Admin to manage shows and auditoriums,
     * including adding new shows or viewing current ones.
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
     */
    private void listAllAuditoriums() {
        System.out.println("List of all the auditoriums");
        for (Auditorium auditorium : theatreController.viewAuditoriums()){
            System.out.println("ID: " + auditorium.getID() + ", name:" + auditorium.getName() +
                    ", capacity: " + auditorium.getCapacity());
        }
    }

    /**
     * Lists all shows
     */
    private void listAllShows() {
        System.out.println("List of all the shows: \n");
        for (Show show : theatreController.viewShows()){
            System.out.println("ID: " + show.getID() + ", title: " + show.getTitle() + ", date: " + new SimpleDateFormat("yyyy-MM-dd").format(show.getDate()));
            System.out.println("auditorium name: " + show.getAudit().getName() + ", price:" + show.getPrice());
            Map<Actor,String> roles = show.getRoles();
            for (Map.Entry<Actor, String> entry : roles.entrySet()) {
                Actor actor = entry.getKey();
                String role = entry.getValue();
                System.out.println("actor name: " + actor.getName() + ", role: " + role);
            }
            System.out.println();
        }
        System.out.println("----------------------------------------------");
    }

    /**
     * Deletes an auditorium
     * @throws IOException if an I/O error occurs during reading.
     */
    private void deleteAuditorium() throws IOException {
        System.out.println("\nDeleting an auditorium");
        for(Auditorium auditorium : theatreController.viewAuditoriums()){
            System.out.println("ID: " + auditorium.getID() + ", name: " + auditorium.getName());
        }
        System.out.println("Id of the auditorium you want to delete (or 0 if you dont want to delete anything):");
        Integer audId;
        while (true) {
            try {
                audId = Integer.parseInt(reader.readLine());
                if (audId == 0) {
                    return;
                }
                if (audId > 0)
                    break;
                else throw new NumberFormatException();
            }catch (NumberFormatException e){
                System.out.println("Invalid Input. Input must be a positive integer");
                System.out.println("Id of the auditorium you want to delete:");
            }catch (EntityNotFoundException e){
                System.out.println("Auditorium with this ID does not exist. Try again.");
            }
        }
        try {
            theatreController.deleteAuditorium(audId);
            System.out.println("Auditorium successfully deleted.");
        } catch (UserExistenceException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Cancels a show
     * @throws IOException if an I/O error occurs during reading.
     */
    private void deleteShow() throws IOException {
        System.out.println("\nDeleting shows");
        filterShows();
        System.out.println("Id of the show you want to delete (0 if you dont want to delete anithing):");
        Integer showId;
        while (true) {
            try {
                showId = Integer.parseInt(reader.readLine());
                if (showId == 0)
                    return;
                break;
            }catch (NumberFormatException e){
                System.out.println("Invalid Input. Input must be an integer");
                System.out.println("Id of the show you want to delete:");
            }
        }

        try {
            theatreController.deleteShow(showId);
            System.out.println("Show deleted successfully!");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     *Creates a new auditorium
     * @throws IOException if an I/O error occurs during reading.
     */
    private void createAuditorium() throws IOException {
        System.out.println("\nCreating auditorium");

        System.out.println("Auditorium name:");
        String name;
        while (true) {
            try {
            name = reader.readLine();
            if(!name.isEmpty())
                break;
            else {
                throw new InvalidStringLenghtException("Auditorium name should contain minimum 1 letter!");
            }
            }catch (InvalidStringLenghtException e){
                System.out.println(e.getMessage());
                System.out.println("Auditorium name: ");
            }
        }

        int nrRows;
        while (true) {
            System.out.println("Number of rows: ");
            try {
                nrRows = Integer.parseInt(reader.readLine());
                if (nrRows < 0)
                    throw new ValidationException("Number of rows must be a positive integer!");
                break;
            } catch (NumberFormatException e){
                System.out.println("Invalid Input. Input must be an integer!");
                System.out.println("Number of rows: ");
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        }

        int nrCols;
        while (true) {
            System.out.println("Number of columns: ");
            try {
                nrCols = Integer.parseInt(reader.readLine());
                if (nrCols < 0)
                    throw new ValidationException("Number of columns must be a positive integer!");
                break;
            }catch (NumberFormatException e){
                System.out.println("Invalid Input. Input must be an integer!");
                System.out.println("Number of columns: ");
            }catch (ValidationException e){
                System.out.println(e.getMessage());
            }
        }

        try {
            Auditorium newAuditorium = theatreController.createAuditorium(name, nrRows, nrCols);
            System.out.println("Auditorium: " + newAuditorium.getName() + " successfully created.");
        } catch (ValidationException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    /**
     * Creates a new show - after this the Viewer can buy tickets for the show
     * @throws IOException if an I/O error occurs during reading.
     */
    private void createShow() throws IOException, ParseException {
        System.out.println("\nCreating show");


        System.out.print("Show title: ");
        String showTitle;
        while (true) {
            try {
                showTitle = reader.readLine();
                if(!showTitle.isEmpty())
                    break;
                else throw new InvalidStringLenghtException("Show title should contain minim 1 letter");
            }catch (InvalidStringLenghtException e){
                System.out.println(e.getMessage());
                System.out.print("Show title: ");
            }
        }

        // this is for checking if the auditoriumID exists
        List<Integer> allValidAuditoriumIDs = new ArrayList<>();
        // Display available auditoriums and choose one
        System.out.println("Available auditoriums:");
        List<Auditorium> allAuditoriums = theatreController.viewAuditoriums();
        for (Auditorium auditorium : allAuditoriums) {
            System.out.println("ID: "+auditorium.getID()+", auditorium name: " + auditorium.getName()
                    + ", number of seats: " + auditorium.getCapacity());
            allValidAuditoriumIDs.add(auditorium.getID());
        }

        int auditoriumId;
        while (true) {
            try {
                System.out.print("Choose a valid auditorium by its ID: ");
                auditoriumId = Integer.parseInt(reader.readLine());
                boolean existsAuit = false;
                for (int auditID : allValidAuditoriumIDs) {
                    if (auditID == auditoriumId)
                        existsAuit = true;
                }
                if (existsAuit)
                    break;
                if (!existsAuit){
                    System.out.println("Auditorium not found. Please select a valid one");
                }
        }catch (NumberFormatException e){
                System.out.println("Invalid Input. Input must be an integer");
                System.out.println("Choose a valid auditorium by its ID: ");
            }
        }

        // Create a map for actors and their roles
        Map<Actor, String> roleMap = new HashMap<>();
        System.out.println("\nYou can add as many actors as you want. Enter '0' to finish adding actors.");

        Integer id = 0;

        System.out.println("\nAll available actors:");
        for (Actor actor : theatreController.viewActors()) {
            System.out.println("ID: " + actor.getID() + ",name: " + actor.getName());
        }

        while (true) {

            System.out.print("Select the ID of an actor (or '0' to stop): ");
            while (true) {
                try {
                    id = Integer.parseInt(reader.readLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Input. Input must be an integer");
                    System.out.print("Select the ID of an actor (or '0' to stop): ");
                }
            }


            if (id == 0) {
                break;  // Stop adding actors if user enters 0
            }

            Actor actor = theatreController.viewActor(id);
            if (actor == null) {
                System.out.println("Invalid actor ID. Please try again.");
                continue;  // Skip this iteration if the actor ID is invalid
            }

            System.out.print("Role for actor " + actor.getName() + ": ");
            String role;
            while (true){
                try {
                    role = reader.readLine();
                    if (!role.isEmpty() && role.matches("[a-zA-Z ]+"))
                        break;
                    else if (role.isEmpty())
                        throw new InvalidStringLenghtException("Role should contain minim 1 letter");
                    else if (!role.matches("[a-zA-Z ]+"))
                        throw new InvalidFormatException("Role should contain only letters and spaces");
                }catch (InvalidStringLenghtException e){
                    System.out.println(e.getMessage());
                    System.out.print("Role for actor " + actor.getName() + ": ");
                }catch (InvalidFormatException e){
                    System.out.println(e.getMessage());
                }
            }

            roleMap.put(actor, role);
        }


        String showDate;
        Date date = null;
        while (true){
            System.out.print("Enter the date of the show (e.g., YYYY-MM-DD): ");
            try {
                showDate = reader.readLine();
                if(!validateDateFormat(showDate))
                   throw new InvalidDateFormat("The Date should be in the YYYY-MM-DD format");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                date = sdf.parse(showDate);
                Date today = Calendar.getInstance().getTime();
                if (date.before(today))
                    throw new InvalidDateFormat("The Date should be today or in a future date");
                break;
            }catch (InvalidDateFormat e){
                System.out.println(e.getMessage());
            }
        }

        int price;
        while (true){
            System.out.println("The price of the show: ");
            try {
                price = Integer.parseInt(reader.readLine());
                break;
            } catch (NumberFormatException e){
                System.out.println("Invalid Input. Input must be an integer");
            }
        }

        try {
            Show newShow = theatreController.createShow(showTitle, date, auditoriumId, roleMap, price );
            System.out.println("New show: '" + newShow.getTitle() + "' was created!");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * @param showDate - a date format that should be verified to parse it to a DATE object
     * @return - true if the format is correct, false otherwise
     */
    private static boolean validateDateFormat(String showDate){
        String regex = "^\\d{4}-((0[1-9])|(1[0-2]))-([0-2][1-9]|3[01])$";
        return showDate.matches(regex);
    }
    /**
     * Creates a new actor
     * @throws IOException if an I/O error occurs during reading.
     */

    private void hireActor() throws IOException {
        System.out.println("\nHiring a new actor");

        System.out.print("Actor Name: ");
        String actorName;
        while(true){
            try {
                actorName = reader.readLine();
                if (actorName.length() > 2 && actorName.matches("[a-zA-Z ]+"))
                    break;
                else if (actorName.length() <= 2)
                    throw new InvalidStringLenghtException("The name should contain minim 3 letters");
                else if (!actorName.matches("[a-zA-Z ]+"))
                    throw new InvalidFormatException("The name should contain only letters and spaces, not other characters");
            } catch (InvalidStringLenghtException | InvalidFormatException e){
                System.out.println(e.getMessage());
                System.out.print("Actor Name: ");
            }
        }


        System.out.print("Actor Age: ");
        int actorAge;
        while(true){
            try {
                actorAge = Integer.parseInt(reader.readLine());
                if (actorAge < 10 || actorAge > 80)
                    throw new AgeNotInBoundException("The age of the actor should be between 10 and 80");
                break;
            } catch (NumberFormatException e){
                System.out.println("Invalid Input. Input must be an integer");
                System.out.print("Actor Age: ");
            } catch (AgeNotInBoundException e){
                System.out.println(e.getMessage());
                System.out.print("Actor Age: ");
            }
        }

        System.out.print("Actor Email address: ");
        String actorEmailAddress;
        while(true){
            try {
                actorEmailAddress = reader.readLine();
                if(validEmailAddress(actorEmailAddress))
                    break;
                else throw new InvalidEmailFormatException("Email should contain a '@' symbol");
            } catch (InvalidEmailFormatException e){
                System.out.println(e.getMessage());
                System.out.print("Actor Email address: ");
            }
        }

        System.out.print("Actor Email password: ");
        String actorEmailPassword = reader.readLine();

        System.out.print("Actor Salary: ");
        int actorSalary;
        while(true){
            try {
                actorSalary = Integer.parseInt(reader.readLine());
                if (actorSalary < 1000)
                    throw new MinimalSalaryException("The salary of the actor must be minim 1000");
                break;
            } catch (NumberFormatException e){
                System.out.println("Invalid Input. Input must be an integer");
                System.out.print("Actor Salary: ");
            } catch (MinimalSalaryException e){
                System.out.println(e.getMessage());
                System.out.print("Actor Salary: ");
            }
        }

        try {
            Actor newActor = theatreController.createActorAccount(actorName, actorAge, new EMail(actorEmailAddress, actorEmailPassword), actorSalary);
            System.out.println("Actor: " + newActor.getName() + " hired successfully.");
        } catch (UserExistenceException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Deletes an actor
     * @throws IOException if an I/O error occurs during reading.
     */
    private void fireActor() throws IOException {
        for(Actor actor : theatreController.viewActors()){
            System.out.println("ID: " + actor.getID() + ", Name: " + actor.getName());
        }
        System.out.print("\nEnter the ID of the actor to fire: ");
        int actorId;
        while(true){
            try {
                actorId = Integer.parseInt(reader.readLine());
                break;
            }catch (NumberFormatException e){
                System.out.println("Invalid Input. Input must be an integer");
                System.out.print("\nEnter the ID of the actor to fire: ");
            }
        }
        try {
            theatreController.deleteActorAccount(actorId);
            System.out.println("Actor fired successfully.");
        }catch (UserExistenceException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Changes the salary of an actor
     * @throws IOException if an I/O error occurs during reading.
     */
    private void changeActorSalary() throws IOException {
        for(Actor actor : theatreController.viewActors()){
            System.out.println("ID: " + actor.getID() + ", name: " +
                    actor.getName() + ", salary: " + actor.getSalary());
        }

        System.out.print("\nEnter the ID of the actor whose salary you want to change: ");
        int actorId;
        while(true){
            try {
                actorId = Integer.parseInt(reader.readLine());
                break;
            }catch (NumberFormatException e){
                System.out.println("Invalid Input. Input must be an integer");
                System.out.print("\nEnter the ID of the actor whose salary you want to change: ");
            }
        }
        System.out.print("Enter the new salary: ");
        int newSalary;
        while(true){
            try {
                newSalary = Integer.parseInt(reader.readLine());
                if (newSalary < 1000)
                    throw new MinimalSalaryException("The salary of the actor must be minim 1000");
                break;
            }catch (NumberFormatException e){
                System.out.println("Invalid Input. Input must be an integer");
                System.out.print("Enter the new salary: ");
            }catch (MinimalSalaryException e){
                System.out.println(e.getMessage());
                System.out.print("Enter the new salary: ");
            }
        }

        try {
            theatreController.changeSalary(actorId, newSalary);
            System.out.println("Actor's salary updated successfully.");
        } catch (UserExistenceException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Lists all the actors of the theatre
     */
    private void listAllActors() {
        System.out.println("\nList of all actors:");
        for(Actor actor : theatreController.viewActors()){
            System.out.println("ID: " + actor.getID() + ", Name: " + actor.getName()
                    + ", Age: " + actor.getAge()
                    + ", Salary: " + actor.getSalary()
                    + ", Email Address: " + actor.getEmail().getEmailAddress()
                    + ", Email Password: " + actor.getEmail().getPassword());
        }
    }

    /**
     * Deletes an order based on its ID and refunds for the Viewer(a simple print)
     * @param id - the id of the Viewer
     */
    private void deleteOrder(Integer id) throws IOException {
        viewMyOrders(id);
        System.out.println("Id of the order you want to delete: ");
        Integer orderId;
        while (true){
            try {
                orderId = Integer.parseInt(reader.readLine());
                break;
            } catch (NumberFormatException e){
                System.out.println(e.getMessage());
                System.out.println("Id of the order you want to delete: ");
            }
        }
        try {
            int refund = theatreController.deleteOrder(orderId);
            System.out.println("You were refunded with: " + refund);
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void sortShows(){
       List<Show> shows = theatreController.viewShowsSorted();
       for (Show s : shows){
           Map<Actor,String> actorsRole = s.getRoles();
           System.out.println("Show id: " + s.getID() + ", title: " + s.getTitle() + ", date: " +
                   new SimpleDateFormat("yyyy-MM-dd").format(s.getDate()) +
                   ", auditorium: " + s.getAudit().getName());
           System.out.println("Actors with their roles:");
           for (Map.Entry<Actor, String> entry : actorsRole.entrySet()) {
               Actor actor = entry.getKey();
               String role = entry.getValue();
               System.out.println("   " + actor.getName() + " as " + role);
           }
           System.out.println();
       }
    }

    private void filterShows(){
        List<Show> shows = theatreController.viewShowsFiltered();
        for (Show s : shows){
            Map<Actor,String> actorsRole = s.getRoles();
            System.out.println("Show id: " + s.getID() + ", title: " + s.getTitle() + ", date: " +
                    new SimpleDateFormat("yyyy-MM-dd").format(s.getDate()) +
                    ", auditorium: " + s.getAudit().getName());
            System.out.println("Actors with their roles:");
            for (Map.Entry<Actor, String> entry : actorsRole.entrySet()) {
                Actor actor = entry.getKey();
                String role = entry.getValue();
                System.out.println("   " + actor.getName() + " as " + role);
            }
            System.out.println();
        }
    }

    private void sortOrders(Integer id){
       List<Order> myOrders = theatreController.viewOrdersSorted(id);

        for (Order myOrder : myOrders) {
            List<Ticket> myTickets = myOrder.getTickets();
            System.out.println("Order ID: " + myOrder.getID());
            System.out.println("Your tickets: ");
            for (Ticket ticket : myTickets){
                System.out.println("    Ticket Nr." + ticket.getID() +
                        ", for the Show: " + ticket.getShowName() +
                        ", in the auditorium: " + ticket.getAuditoriumName() +
                        ", Seat: " + ticket.getSeat());
            }
        }

    }

    private void filterOrders(Integer id){
        List<Order> myOrders = theatreController.viewOrdersFiltered(id);

        for (Order myOrder : myOrders) {
            List<Ticket> myTickets = myOrder.getTickets();
            System.out.println("Order ID: " + myOrder.getID());
            System.out.println("Your tickets: ");
            for (Ticket ticket : myTickets){
                System.out.println("    Ticket Nr." + ticket.getID() +
                        ", for the Show: " + ticket.getShowName() +
                        ", in the auditorium: " + ticket.getAuditoriumName() +
                        ", Seat: " + ticket.getSeat());
            }
        }
    }
    /**
     * Facilitates login or signup options for the user.
     * @param tc the TheatreController instance managing domain interactions.
     * @return the email of the newly logged in or signed-up user.
     * @throws IOException if an I/O error occurs during reading.
     */
    public static EMail choosingBetweenLoginAndSignup(TheatreController tc) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

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
     * @param tc the TheatreController instance managing domain interactions.
     * @return the email of the signed-up user.
     * @throws IOException if an I/O error occurs during reading.
     */
    private static EMail signUp(TheatreController tc) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        EMail newMail;
        while (true) {

            System.out.println("Welcome new User");
            System.out.println("Please create your account as follows:");
            System.out.println("Provide us with your name:");
            String name;
            while(true){
                try {
                    name = reader.readLine();
                    if (name.length() > 2 && name.matches("[a-zA-Z ]+"))
                        break;
                    else if (name.length() <= 2)
                        throw new InvalidStringLenghtException("Your name should contain minim 3 letters");
                    else if (!name.matches("[a-zA-Z ]+"))
                        throw new InvalidFormatException("Your name should contain only letters, not other characters");
                }catch (InvalidStringLenghtException | InvalidFormatException e){
                    System.out.println(e.getMessage());
                    System.out.println("Provide us with your name:");
                }
            }

            System.out.println("Your age: ");
            int age;
            while (true){
                try {
                    age = Integer.parseInt(reader.readLine());
                    if(age <= 16)
                        throw new NumberFormatException();
                    break;
                }catch (NumberFormatException e){
                    System.out.println("Your age should be a positive integer greater than  or equal to 16");
                    System.out.println("Your age: ");
                }
            }
            System.out.println("Your emailAddress: ");
            String emailAddress;
            while (true){
                try {
                    emailAddress = reader.readLine();
                    if (validEmailAddress(emailAddress))
                        break;
                    else throw new InvalidEmailFormatException("The email should contain a '@' symbol");
                }catch (InvalidEmailFormatException e){
                    System.out.println(e.getMessage());
                    System.out.println("Your emailAddress: ");
                }
            }
            System.out.println("Your password: ");
            String password = reader.readLine();
            newMail = new EMail(emailAddress, password);

            try {
                Viewer newViewer = tc.createViewerAccount(name, age, newMail);
                System.out.println("Account: " + newViewer.getName() + " created successfully!");
                break;
            } catch (UserExistenceException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        return newMail;
    }

    /**
     * Allows a user to log in to the system.
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
            String emailAddress;
            while(true){
                try {
                    emailAddress = reader.readLine();
                    if(validEmailAddress(emailAddress))
                        break;
                    else throw new InvalidEmailFormatException("Email should contain a '@' symbol");
                }catch (InvalidEmailFormatException e){
                    System.out.println(e.getMessage());
                    System.out.println("Email Address:");
                }
            }
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