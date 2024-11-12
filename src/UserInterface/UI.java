package UserInterface;

import ControllerService.TheatreController;
import Domain.Actor;
import Domain.Auditorium;
import Domain.EMail;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class UI {
    private final TheatreController theatreController;
    private final BufferedReader reader;

    public UI(TheatreController theatreController) {
        this.theatreController = theatreController;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void RUN(EMail userEmail) throws IOException {
        while (true) {

            String role = String.valueOf(theatreController.login(userEmail));

//            String input = reader.readLine();
            switch (role) {
                case "1":
                    ActorUI(userEmail);
                    break;
                case "2":
                    CeoUi();
                    break;
                case "3":
//                    ViewerUI();
                    break;
                case "0":
                    System.out.println("Exiting the program. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please enter 1 or 0.");
            }
        }
    }
    private void ActorUI(EMail actorMail) throws IOException {
        while (true) {
            System.out.println("\nWelcome Actor");
            System.out.println("What do you want to do?");
            System.out.println("1 - View upcoming shows");
            System.out.println("2 - Manage personal account");
            System.out.println("0 - Back to main menu");

            String input = reader.readLine();
            switch (input) {
                case "1":
                    viewUpcomingShows(actorMail);
                    break;
                case "2":
//                    manageActorAccount();
                    break;
                case "0":
                    return;  // Go back to the main menu in RUN
                default:
                    System.out.println("Invalid option. Please choose 1, 2 or 0.");
            }
        }
    }
    private void viewUpcomingShows(EMail actorMail) throws IOException {
        System.out.println("Listing all your upcoming shows");
        System.out.println(theatreController.viewMyShows(actorMail));
    }


    private void CeoUi() throws IOException {
        while (true) {
            System.out.println("\nWelcome CEO");
            System.out.println("What do you want to do?");
            System.out.println("1 - Work with actors");
            System.out.println("2 - Work with shows and auditoriums");
            System.out.println("3 - Manage personal account");
            System.out.println("0 - Back to main menu");

            String input = reader.readLine();
            switch (input) {
                case "1":
                    manageActors();
                    break;
                case "2":
                    manageShowsAndAuditoriums();
                    break;
                case "3":
//                    managePersonalAccountCeo();
                    break;
                case "0":
                    return;  // Go back to the main menu in RUN
                default:
                    System.out.println("Invalid option. Please choose 1, 2, 3, or 0.");
            }
        }
    }

//    private void managePersonalAccountCeo() throws IOException {
//        System.out.println("\nManage personal account - options");
//        System.out.println("1 - ");
//    }

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
                    return;  // Go back to CEO menu in CeoUi
                default:
                    System.out.println("Invalid option. Please choose 1, 2, 3, 4, 5, 6 or 0.");
            }
        }

    }

    private void listAllAuditoriums() throws IOException {
        System.out.println("List of all the auditoriums");
        System.out.println(theatreController.viewAllAuditoriums());
    }

    private void listAllShows() throws IOException {
        System.out.println("List of all the shows");
        System.out.println(theatreController.viewShows());
    }

    private void deleteAuditorium() throws IOException {
        System.out.println("\nDeleting an auditorium");
        System.out.println(theatreController.viewAllAuditoriums());
        System.out.println("Id of the auditorium you want to delete:");
        Integer audId = Integer.parseInt(reader.readLine());
        theatreController.deleteAuditorium(audId);

    }

    private void deleteShow() throws IOException {
        System.out.println("\nDeleting shows");
        System.out.println(theatreController.viewShows());
        System.out.println("Id of the show you want to delete:");
        Integer showId = Integer.parseInt(reader.readLine());

        theatreController.ceoDeleteShow(showId);
        System.out.println("Show successfully deleted.");
    }

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

    private void fireActor() throws IOException {
        System.out.print("\nEnter the ID of the actor to fire: ");
        int actorId = Integer.parseInt(reader.readLine());
        theatreController.ceoFireActor(actorId);
        System.out.println("Actor fired successfully.");
    }

    private void changeActorSalary() throws IOException {
        System.out.print("\nEnter the ID of the actor whose salary you want to change: ");
        int actorId = Integer.parseInt(reader.readLine());

        System.out.print("Enter the new salary: ");
        int newSalary = Integer.parseInt(reader.readLine());

        theatreController.ceoChangeSalary(actorId, newSalary);
        System.out.println("Actor's salary updated successfully.");
    }

    private void listAllActors() {
        System.out.println("\nList of all actors:");
        theatreController.viewAllActors().forEach(actor -> System.out.println(actor));
    }

}
