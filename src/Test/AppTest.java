package Test;

import Domain.*;
import Exceptions.EntityNotFoundException;
import Exceptions.UserExistenceException;
import Exceptions.ValidationException;
import Repository.*;
import Service.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    private TheatreService ts;

    @BeforeEach
    void setUp() {
        new File("Test/TestRepository/actorTest").delete();
        new File("Test/TestRepository/adminTest").delete();
        new File("Test/TestRepository/viewerTest").delete();
        new File("Test/TestRepository/auditoriumTest").delete();
        new File("Test/TestRepository/showTest").delete();
        new File("Test/TestRepository/orderTest").delete();
    }

    private static Stream<Repository<Actor>> createActorRepository() {
        return Stream.of(
                new InMemoryRepository<Actor>()
//                new FileRepository<Actor>("Test/TestRepository/actorTest") {
//                    @Override
//                    protected String serialize(Actor obj) {
//                        return obj.getID() + "," + obj.getName() + "," + obj.getAge() + "," + obj.getEmail().getEmailAddress() + "," + obj.getEmail().getPassword() + "," + obj.getSalary();
//                    }
//
//                    @Override
//                    protected Actor deserialize(String data) {
//                        String[] fields = data.split(",");
//                        return new Actor(Integer.parseInt(fields[0]), fields[1], Integer.parseInt(fields[2]), new EMail(fields[3], fields[4]), Integer.parseInt(fields[5]));
//                    }
//                }
        );
    }

    private static Stream<Repository<Admin>> createAdminRepository() {
        return Stream.of(
                new InMemoryRepository<Admin>()
//                new FileRepository<Admin>("Test/TestRepository/adminTest") {
//                    @Override
//                    protected String serialize(Admin obj) {
//                        return obj.getID() + "," + obj.getName() + ',' + obj.getAge() + "," + obj.getEmail().getEmailAddress() + "," + obj.getEmail().getPassword();
//                    }
//
//                    @Override
//                    protected Admin deserialize(String data) {
//                        String[] fields = data.split(",");
//                        return new Admin(Integer.parseInt(fields[0]), fields[1], Integer.parseInt(fields[2]), new EMail(fields[3], fields[4]));
//                    }
//                }
        );
    }

    private static Stream<Repository<Viewer>> createViewerRepository() {
        return Stream.of(
                new InMemoryRepository<Viewer>()
//                new FileRepository<Viewer>("Test/TestRepository/viewerTest") {
//                    @Override
//                    protected String serialize(Viewer obj) {
//                        return obj.getID() + "," + obj.getName() + ',' + obj.getAge() + "," + obj.getEmail().getEmailAddress() + "," + obj.getEmail().getPassword();
//                    }
//
//                    @Override
//                    protected Viewer deserialize(String data) {
//                        String[] fields = data.split(",");
//                        return new Viewer(Integer.parseInt(fields[0]), fields[1], Integer.parseInt(fields[2]), new EMail(fields[3], fields[4]));
//                    }
//                }
        );
    }

    private static Stream<Repository<Auditorium>> createAuditoriumRepository() {
        return Stream.of(
                new InMemoryRepository<Auditorium>()
//                new FileRepository<Auditorium>("Test/TestRepository/auditoriumTest") {  // FileRepository for Auditorium
//                    @Override
//                    protected String serialize(Auditorium obj) {
//                        return obj.getID() + "," + obj.getName() + "," + obj.getRows() + "," + obj.getCols();
//                    }
//
//                    @Override
//                    protected Auditorium deserialize(String data) {
//                        String[] objectParts = data.split(",");
//                        int id = Integer.parseInt(objectParts[0]);
//                        String name = objectParts[1];
//                        int rows = Integer.parseInt(objectParts[2]);
//                        int cols = Integer.parseInt(objectParts[3]);
//                        return new Auditorium(id, name, rows, cols);
//                    }
//                }
        );
    }

    private static Stream<Repository<Show>> createShowRepository() {
        return Stream.of(
                new InMemoryRepository<Show>()
//                new FileRepository<Show>("Test/TestRepository/showTest") {  // FileRepository for Auditorium
//                    @Override
//                    protected String serialize(Show show) {
//                        String rolesSerialized = show.getRoles().entrySet().stream()
//                                .map(entry -> entry.getKey().getID() + ":" + entry.getValue())
//                                .collect(Collectors.joining("|"));
//
//                        return show.getID() + "," + show.getTitle() + "," + show.getDate() + "," + show.getAudit().getID() + "," + rolesSerialized + "," + show.getPrice();
//                    }
//
//                    @Override
//                    protected Show deserialize(String data) {
//                        String[] objectParts = data.split(",");
//
//                        int id = Integer.parseInt(objectParts[0]);
//                        String title = objectParts[1];
//
//                        Date date = parseDate(objectParts[2]); // custom method for parsing Data objects
//
//                        int auditId = Integer.parseInt(objectParts[3]);
//                        int price = Integer.parseInt(objectParts[5]);
//
//                        Map<Integer, String> roles = Arrays.stream(objectParts[4].split("\\|"))
//                                .map(roleStr -> roleStr.split(":"))
//                                .collect(Collectors.toMap(
//                                        roleParts -> Integer.parseInt(roleParts[0]),
//                                        roleParts -> roleParts[1]
//                                ));
//
//                        Auditorium auditorium = tc.viewAuditorium(auditId);
//
//                        Map<Actor, String> actorRoles = mapActorsToRoles(roles);
//
//                        return new Show(id, title, date, auditorium, actorRoles, price);
//                    }
//                }
        );
    }

    private static Stream<Repository<Order>> createOrderRepository() {
        return Stream.of(
                new InMemoryRepository<Order>()
//                new FileRepository<Order>("Test/TestRepository/orderTest") {  // FileRepository for Order
//                    @Override
//                    protected String serialize(Order obj) {
//                        String seatsSerialized = obj.getSeats().stream()
//                                .map(String::valueOf)
//                                .collect(Collectors.joining("|"));
//
//                        String ticketsSerialized = obj.getTickets().stream()
//                                .map(ticket -> ticket.getID() + ":" +
//                                        ticket.getShowName() + ":" +
//                                        ticket.getViewerName() + ":" +
//                                        ticket.getAuditoriumName() + ":" +
//                                        ticket.getPrice() + ":" +
//                                        ticket.getSeat())
//                                .collect(Collectors.joining("|"));
//
//                        return obj.getID() + "," + obj.getDate() + "," + obj.getViewerID() + "," +
//                                obj.getShowID() + "," + seatsSerialized + "," + ticketsSerialized + "," +
//                                obj.getTotalPrice();
//                    }
//
//                    @Override
//                    protected Order deserialize(String data) {
//                            String[] objectParts = data.split(",");
//
//                            int id = Integer.parseInt(objectParts[0]);
//                            LocalDate date = LocalDate.parse(objectParts[1]);
//                            int viewerID = Integer.parseInt(objectParts[2]);
//                            int showID = Integer.parseInt(objectParts[3]);
//
//                            List<Integer> seats = Arrays.stream(objectParts[4].split("\\|"))
//                                    .map(Integer::parseInt)
//                                    .collect(Collectors.toList());
//
//                            List<Ticket> tickets = Arrays.stream(objectParts[5].split("\\|"))
//                                    .map(ticketStr -> {
//                                        String[] ticketParts = ticketStr.split(":");
//                                        int ticketID = Integer.parseInt(ticketParts[0]);
//                                        String showName = ticketParts[1];
//                                        String viewerName = ticketParts[2];
//                                        String auditoriumName = ticketParts[3];
//                                        int price = Integer.parseInt(ticketParts[4]);
//                                        int seat = Integer.parseInt(ticketParts[5]);
//                                        return new Ticket(ticketID, showName, viewerName, auditoriumName, price, seat);
//                                    })
//                                    .collect(Collectors.toList());
//
//                            int totalPrice = Integer.parseInt(objectParts[6]);
//                            return new Order(id, date, viewerID, showID, seats, tickets, totalPrice);
//                    }
//                }
        );
    }
//
//    private static Date parseDate(String dateString) {
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
//        try {
//            return sdf.parse(dateString);
//        } catch (ParseException ignored) {
//            return null;
//        }
//    }
//
//    private static Map<Actor, String> mapActorsToRoles(Map<Integer, String> roles) {
//        return roles.entrySet().stream()
//                .map(entry -> {
//                    Actor actor = findActorById(entry.getKey());
//                    return Map.entry(actor, entry.getValue());
//                })
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//    }
//
//    private static Actor findActorById(int id) {
//        return tc.viewActors().stream()
//                .filter(actor -> actor.getID() == id)
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("Actor not found with ID: " + id));
//    }

    @ParameterizedTest
    @MethodSource("createActorRepository")
    void actorCreationTest(Repository<Actor> actorRepository) {
        ts = new TheatreService(new AdminService(new InMemoryRepository<Admin>()), new ActorService(actorRepository), new AuditoriumService(new InMemoryRepository<Auditorium>()), new ShowService(new InMemoryRepository<Show>()), new ViewerService(new InMemoryRepository<Viewer>()), new OrderService(new InMemoryRepository<Order>()));
        /* new actor creation*/
        ts.createActorAccount("TestActorName", 20, new EMail("testactor@gmail.com", "test"), 100);

        /* test exception */
        UserExistenceException e = assertThrows(UserExistenceException.class, () -> ts.createActorAccount("TestActorName", 21, new EMail("testactor@gmail.com", "test"), 100));
        assertEquals("Email address already exists!", e.getMessage());

        /* retrieving the new actor and checking for data correctness */
        Actor newActor = ts.getActor(1);
        assertNotNull(newActor);
        assertEquals("TestActorName", newActor.getName());
        assertEquals(20, newActor.getAge());
        assertEquals(100, newActor.getSalary());
        assertEquals("testactor@gmail.com", newActor.getEmail().getEmailAddress());
        assertEquals("test", newActor.getEmail().getPassword());

        /* updating new actor and checking for data correctness */
        ts.manageActorAccount(1, "UpdatedActorName", 21, new EMail("updatedactor@gmail.com", "updated"));
        ts.changeSalary(1, 50);
        Actor updatedTestActor = ts.getActor(1);
        assertNotNull(updatedTestActor);
        assertEquals("UpdatedActorName", updatedTestActor.getName());
        assertEquals(21, updatedTestActor.getAge());
        assertEquals(50, updatedTestActor.getSalary());
        assertEquals("updatedactor@gmail.com", updatedTestActor.getEmail().getEmailAddress());
        assertEquals("updated", updatedTestActor.getEmail().getPassword());

        /* testing object deletion */
        ts.deleteActorAccount(1);
        assertNull(ts.getActor(1));

        /* creating 2 new objects to test for the getAll-returning a List method */
        ts.createActorAccount("TestActorName1", 21, new EMail("testactor1@gmail.com", "test1"), 50);
        ts.createActorAccount("TestActorName2", 22, new EMail("testactor2@gmail.com", "test2"), 100);

        List<Actor> actors = ts.getActors();
        assertEquals(2, actors.size());
    }

    @ParameterizedTest
    @MethodSource("createAdminRepository")
    void adminCreationTest(Repository<Admin> adminRepository) {
        ts = new TheatreService(new AdminService(adminRepository), new ActorService(new InMemoryRepository<Actor>()), new AuditoriumService(new InMemoryRepository<Auditorium>()), new ShowService(new InMemoryRepository<Show>()), new ViewerService(new InMemoryRepository<Viewer>()), new OrderService(new InMemoryRepository<Order>()));

        ts.createAdminAccount(1, "TestAdminName", 20, new EMail("testadmin@gmail.com", "test"));

        UserExistenceException e = assertThrows(UserExistenceException.class, () -> ts.createAdminAccount(2, "TestAdminName", 21, new EMail("testadmin@gmail.com", "test")));
        assertEquals("Email address already exists!", e.getMessage());

        Admin newAdmin = ts.getAdmin(1);
        assertNotNull(newAdmin);
        assertEquals("TestAdminName", newAdmin.getName());
        assertEquals(20, newAdmin.getAge());
        assertEquals("testadmin@gmail.com", newAdmin.getEmail().getEmailAddress());
        assertEquals("test", newAdmin.getEmail().getPassword());

        ts.manageAdminAccount(1, "UpdatedAdminName", 21, new EMail("updatedadmin@gmail.com", "updated"));

        Admin updatedTestAdmin = ts.getAdmin(1);
        assertNotNull(updatedTestAdmin);
        assertEquals("UpdatedAdminName", updatedTestAdmin.getName());
        assertEquals(21, updatedTestAdmin.getAge());
        assertEquals("updatedadmin@gmail.com", updatedTestAdmin.getEmail().getEmailAddress());

        ts.deleteAdminAccount(1);
        assertNull(ts.getAdmin(1));

        ts.createAdminAccount(1, "TestAdminName1", 21, new EMail("testadmin1@gmail.com", "test1"));
        ts.createAdminAccount(2, "TestAdminName2", 22, new EMail("testadmin2@gmail.com", "test2"));
        List<Admin> admins = ts.getAdmins();
        assertEquals(2, admins.size());
    }

    @ParameterizedTest
    @MethodSource("createViewerRepository")
    void viewerCreationTest(Repository<Viewer> viewerRepository) {
        ts = new TheatreService(new AdminService(new InMemoryRepository<Admin>()), new ActorService(new InMemoryRepository<Actor>()), new AuditoriumService(new InMemoryRepository<Auditorium>()), new ShowService(new InMemoryRepository<Show>()), new ViewerService(viewerRepository), new OrderService(new InMemoryRepository<Order>()));

        Viewer viewer = ts.createViewerAccount("TestViewerName", 20, new EMail("testviewer@gmail.com", "test"));

        UserExistenceException e = assertThrows(UserExistenceException.class, () -> ts.createViewerAccount( "TestViewerName", 21, new EMail("testviewer@gmail.com", "test")));
        assertEquals("Email address already exists!", e.getMessage());

        Viewer newViewer = ts.getViewer(viewer.getID());
        assertNotNull(newViewer);
        assertEquals("TestViewerName", newViewer.getName());
        assertEquals(20, newViewer.getAge());
        assertEquals("testviewer@gmail.com", newViewer.getEmail().getEmailAddress());
        assertEquals("test", newViewer.getEmail().getPassword());

        ts.manageViewerAccount(viewer.getID(), "UpdatedViewerName", 22, new EMail("updatedviewer@gmail.com", "updated"));

        Viewer updatedTestViewer = ts.getViewer(viewer.getID());
        assertNotNull(updatedTestViewer);
        assertEquals("UpdatedViewerName", updatedTestViewer.getName());
        assertEquals(22, updatedTestViewer.getAge());
        assertEquals("updatedviewer@gmail.com", updatedTestViewer.getEmail().getEmailAddress());

        ts.deleteViewerAccount(viewer.getID());
        assertNull(viewerRepository.getByID(viewer.getID()));

        ts.createViewerAccount("TestViewerName1", 21, new EMail("testviewer1@gmail.com", "test1"));
        ts.createViewerAccount("TestViewerName2", 22, new EMail("testviewer2@gmail.com", "test2"));
        List<Viewer> viewers = viewerRepository.getAll();
        assertEquals(2, viewers.size());
    }

    @ParameterizedTest
    @MethodSource("createAuditoriumRepository")
    void auditoriumCreationTest(Repository<Auditorium> auditoriumRepository) {
        ts = new TheatreService(new AdminService(new InMemoryRepository<Admin>()), new ActorService(new InMemoryRepository<Actor>()), new AuditoriumService(auditoriumRepository), new ShowService(new InMemoryRepository<Show>()), new ViewerService(new InMemoryRepository<Viewer>()), new OrderService(new InMemoryRepository<Order>()));

        Auditorium auditorium = ts.createAuditorium("TestName", 5, 6);

        ValidationException e = assertThrows(ValidationException.class, () -> ts.createAuditorium("TestName", 6, 7));
        assertEquals("Auditorium with this name already exists!", e.getMessage());

        Auditorium newAuditorium = ts.getAuditorium(auditorium.getID());
        assertNotNull(newAuditorium);
        assertEquals("TestName", newAuditorium.getName());
        assertEquals(5, newAuditorium.getRows());
        assertEquals(6, newAuditorium.getCols());

        ts.deleteAuditorium(auditorium.getID());
        assertNull(ts.getAuditorium(auditorium.getID()));

        ts.createAuditorium("TestName1", 5, 6);
        ts.createAuditorium("TestName2", 7, 8);
        List<Auditorium> auditoriums = ts.getAuditoriums();
        assertEquals(2, auditoriums.size());
    }

    @ParameterizedTest
    @MethodSource("createShowRepository")
    void showCreationTest(Repository<Show> showRepository) {
        ts = new TheatreService(new AdminService(new InMemoryRepository<Admin>()), new ActorService(new InMemoryRepository<Actor>()), new AuditoriumService(new InMemoryRepository<Auditorium>()), new ShowService(showRepository), new ViewerService(new InMemoryRepository<Viewer>()), new OrderService(new InMemoryRepository<Order>()));

        Auditorium auditorium = ts.createAuditorium("TestName", 5, 6);
        Map<Actor, String> roles = new HashMap<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String date1S = "2024-12-20";
        Date date1;
        try {
            date1 = sdf.parse(date1S);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        ts.createShow("TestTitle", date1, auditorium.getID(), roles, 20);

        EntityNotFoundException e = assertThrows(EntityNotFoundException.class, () -> ts.createShow("TestTitle", date1, 2, roles, 20));
        assertEquals("Auditorium does not exist!", e.getMessage());

        assertNotNull(ts.getShow(1));
        assertEquals("TestTitle", ts.getShow(1).getTitle());
        assertEquals(date1, ts.getShow(1).getDate());
        assertEquals(roles, ts.getShow(1).getRoles());
        assertEquals(auditorium, ts.getAuditoriumByShow(auditorium.getID()));

        ts.deleteShow(1);
        assertNull(ts.getShow(1));

        ts.createShow("TestTitle1", date1, auditorium.getID(), roles, 20);
        ts.createShow("TestTitle2", date1, auditorium.getID(), roles, 60);
        List<Show> shows = ts.getShows();
        assertEquals(2, shows.size());
    }

    @ParameterizedTest
    @MethodSource("createOrderRepository")
    void orderCreationTest(Repository<Order> orderRepository) {
        ts = new TheatreService(new AdminService(new InMemoryRepository<Admin>()), new ActorService(new InMemoryRepository<Actor>()), new AuditoriumService(new InMemoryRepository<Auditorium>()), new ShowService(new InMemoryRepository<Show>()), new ViewerService(new InMemoryRepository<Viewer>()), new OrderService(orderRepository));

        Viewer viewer = ts.createViewerAccount("TestViewerName", 20, new EMail("testviewer@gmail.com", "test"));
        Auditorium auditorium = ts.createAuditorium("TestAuditoriumName", 20, 6);
        Map<Actor, String> roles = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String date1S = "2024-12-20";
        Date date1;
        try {
            date1 = sdf.parse(date1S);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Show show = ts.createShow("TestShowTitle", date1, auditorium.getID(), roles, 20);

        List<Integer> seats = new ArrayList<>();
        seats.add(1); seats.add(2); seats.add(3); seats.add(4);
        List<Integer> seats2 = new ArrayList<>();
        seats.add(5); seats.add(6); seats.add(7); seats.add(8);
        Order order = ts.createOrder(viewer.getID(), show.getID(), seats);

        assertNotNull(ts.getOrder(order.getID()));
        assertEquals(seats, ts.getOrder(order.getID()).getSeats());
        assertEquals(viewer.getID(), ts.getOrder(order.getID()).getViewerID());
        assertEquals(show.getID(), ts.getOrder(order.getID()).getShowID());

        ts.deleteOrder(order.getID());
        assertNull(ts.getOrder(order.getID()));

        ts.createOrder(viewer.getID(), show.getID(), seats);
        ts.createOrder(viewer.getID(), show.getID(), seats2);
        List<Order> orders = ts.getOrders();
        assertEquals(2, orders.size());
    }
}