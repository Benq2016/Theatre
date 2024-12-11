package Test;

import Controller.TheatreController;
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
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    private TheatreService ts;

    @BeforeEach
    void setUp() throws IOException {
        new File("src/Test/TestRepository/actorTest").delete();
        new File("src/Test/TestRepository/adminTest").delete();
        new File("src/Test/TestRepository/viewerTest").delete();
        new File("src/Test/TestRepository/auditoriumTest").delete();
        new File("src/Test/TestRepository/showTest").delete();
        new File("src/Test/TestRepository/orderTest").delete();

        new File("src/Test/TestRepository/actorTest").createNewFile();
        new File("src/Test/TestRepository/adminTest").createNewFile();
        new File("src/Test/TestRepository/viewerTest").createNewFile();
        new File("src/Test/TestRepository/auditoriumTest").createNewFile();
        new File("src/Test/TestRepository/showTest").createNewFile();
        new File("src/Test/TestRepository/orderTest").createNewFile();
    }

    private static Stream<Repository<Actor>> createActorRepository() {
        return Stream.of(
                new InMemoryRepository<Actor>(),
                new ActorFileRepository("src/Test/TestRepository/actorTest"));
    }

    private static Stream<Repository<Admin>> createAdminRepository() {
        return Stream.of(
                new InMemoryRepository<Admin>(),
                new AdminFileRepository("src/Test/TestRepository/adminTest"));
    }

    private static Stream<Repository<Viewer>> createViewerRepository() {
        return Stream.of(
                new InMemoryRepository<Viewer>(),
                new ViewerFileRepository("src/Test/TestRepository/viewerTest"));
    }

    private static Stream<Repository<Auditorium>> createAuditoriumRepository() {
        return Stream.of(
                new InMemoryRepository<Auditorium>(),
                new AuditoriumFileRepository("src/Test/TestRepository/auditoriumTest"));
    }

    private static Stream<Repository<Show>> createShowRepository() {
        return Stream.of(
                new InMemoryRepository<Show>(),
                new ShowFileRepository("src/Test/TestRepository/showTest"));
    }

    private static Stream<Repository<Order>> createOrderRepository() {
        return Stream.of(
                new InMemoryRepository<Order>());
//                new OrderFileRepository("src/Test/TestRepository/orderTest"));
    }

    @ParameterizedTest
    @MethodSource("createActorRepository")
    void actorCreationTest(Repository<Actor> actorRepository) {
        if (actorRepository instanceof InMemoryRepository)
            ts = new TheatreService(new AdminService(new InMemoryRepository<Admin>()), new ActorService(actorRepository), new AuditoriumService(new InMemoryRepository<Auditorium>()), new ShowService(new InMemoryRepository<Show>()), new ViewerService(new InMemoryRepository<Viewer>()), new OrderService(new InMemoryRepository<Order>()));
        else if (actorRepository instanceof ActorFileRepository)
            ts = new TheatreService(new AdminService(new AdminFileRepository("src/Test/TestRepository/adminTest")), new ActorService(actorRepository), new AuditoriumService(new AuditoriumFileRepository("src/Test/TestRepository/auditoriumTest")), new ShowService(new ShowFileRepository("src/Test/TestRepository/showTest")), new ViewerService(new ViewerFileRepository("src/Test/TestRepository/viewerTest")), new OrderService(new OrderFileRepository("src/Test/TestRepository/orderTest")));
        /* new actor creation*/
        Actor actor = ts.createActorAccount("TestActorName", 20, new EMail("testactor@gmail.com", "test"), 100);

        /* test exception */
        UserExistenceException e = assertThrows(UserExistenceException.class, () -> ts.createActorAccount("TestActorName", 21, new EMail("testactor@gmail.com", "test"), 100));
        assertEquals("Email address already exists!", e.getMessage());

        /* retrieving the new actor and checking for data correctness */
        Actor newActor = ts.getActor(actor.getID());
        assertNotNull(newActor);
        assertEquals("TestActorName", newActor.getName());
        assertEquals(20, newActor.getAge());
        assertEquals(100, newActor.getSalary());
        assertEquals("testactor@gmail.com", newActor.getEmail().getEmailAddress());
        assertEquals("test", newActor.getEmail().getPassword());

        /* updating new actor and checking for data correctness */
        ts.manageActorAccount(actor.getID(), "UpdatedActorName", 21, new EMail("updatedactor@gmail.com", "updated"));
        ts.changeSalary(actor.getID(), 50);
        Actor updatedTestActor = ts.getActor(actor.getID());
        assertNotNull(updatedTestActor);
        assertEquals("UpdatedActorName", updatedTestActor.getName());
        assertEquals(21, updatedTestActor.getAge());
        assertEquals(50, updatedTestActor.getSalary());
        assertEquals("updatedactor@gmail.com", updatedTestActor.getEmail().getEmailAddress());
        assertEquals("updated", updatedTestActor.getEmail().getPassword());

        /* testing object deletion */
        ts.deleteActorAccount(actor.getID());
        assertNull(ts.getActor(actor.getID()));

        /* creating 2 new objects to test for the getAll-returning a List method */
        ts.createActorAccount("TestActorName1", 21, new EMail("testactor1@gmail.com", "test1"), 50);
        ts.createActorAccount("TestActorName2", 22, new EMail("testactor2@gmail.com", "test2"), 100);
        List<Actor> actors = ts.getActors();
        assertEquals(2, actors.size());
    }

    @ParameterizedTest
    @MethodSource("createAdminRepository")
    void adminCreationTest(Repository<Admin> adminRepository) {
        if (adminRepository instanceof InMemoryRepository)
            ts = new TheatreService(new AdminService(adminRepository), new ActorService(new InMemoryRepository<Actor>()), new AuditoriumService(new InMemoryRepository<Auditorium>()), new ShowService(new InMemoryRepository<Show>()), new ViewerService(new InMemoryRepository<Viewer>()), new OrderService(new InMemoryRepository<Order>()));
        else
            ts = new TheatreService(new AdminService(adminRepository), new ActorService(new ActorFileRepository("src/Test/TestRepository/actorTest")), new AuditoriumService(new AuditoriumFileRepository("src/Test/TestRepository/auditoriumTest")), new ShowService(new ShowFileRepository("src/Test/TestRepository/showTest")), new ViewerService(new ViewerFileRepository("src/Test/TestRepository/viewerTest")), new OrderService(new OrderFileRepository("src/Test/TestRepository/orderTest")));

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
        if (viewerRepository instanceof InMemoryRepository)
            ts = new TheatreService(new AdminService(new InMemoryRepository<Admin>()), new ActorService(new InMemoryRepository<Actor>()), new AuditoriumService(new InMemoryRepository<Auditorium>()), new ShowService(new InMemoryRepository<Show>()), new ViewerService(viewerRepository), new OrderService(new InMemoryRepository<Order>()));
        else
            ts = new TheatreService(new AdminService(new AdminFileRepository("src/Test/TestRepository/adminTest")), new ActorService(new ActorFileRepository("src/Test/TestRepository/actorTest")), new AuditoriumService(new AuditoriumFileRepository("src/Test/TestRepository/auditoriumTest")), new ShowService(new ShowFileRepository("src/Test/TestRepository/showTest")), new ViewerService(viewerRepository), new OrderService(new OrderFileRepository("src/Test/TestRepository/orderTest")));

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
        if (auditoriumRepository instanceof InMemoryRepository)
            ts = new TheatreService(new AdminService(new InMemoryRepository<Admin>()), new ActorService(new InMemoryRepository<Actor>()), new AuditoriumService(auditoriumRepository), new ShowService(new InMemoryRepository<Show>()), new ViewerService(new InMemoryRepository<Viewer>()), new OrderService(new InMemoryRepository<Order>()));
        else
            ts = new TheatreService(new AdminService(new AdminFileRepository("src/Test/TestRepository/adminTest")), new ActorService(new ActorFileRepository("src/Test/TestRepository/actorTest")), new AuditoriumService(auditoriumRepository), new ShowService(new ShowFileRepository("src/Test/TestRepository/showTest")), new ViewerService(new ViewerFileRepository("src/Test/TestRepository/viewerTest")), new OrderService(new OrderFileRepository("src/Test/TestRepository/orderTest")));

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
        if (showRepository instanceof InMemoryRepository)
            ts = new TheatreService(new AdminService(new InMemoryRepository<Admin>()), new ActorService(new InMemoryRepository<Actor>()), new AuditoriumService(new InMemoryRepository<Auditorium>()), new ShowService(showRepository), new ViewerService(new InMemoryRepository<Viewer>()), new OrderService(new InMemoryRepository<Order>()));
        else {
            ts = new TheatreService(new AdminService(new AdminFileRepository("src/Test/TestRepository/adminTest")), new ActorService(new ActorFileRepository("src/Test/TestRepository/actorTest")), new AuditoriumService(new AuditoriumFileRepository("src/Test/TestRepository/auditoriumTest")), new ShowService(showRepository), new ViewerService(new ViewerFileRepository("src/Test/TestRepository/viewerTest")), new OrderService(new OrderFileRepository("src/Test/TestRepository/orderTest")));
            TheatreController tc = new TheatreController(ts);
            ((ShowFileRepository) showRepository).setTheatreController(tc);
        }

        Actor actor = ts.createActorAccount("TestName", 20, new EMail("test@gmail.com", "test"), 150);
        Auditorium auditorium = ts.createAuditorium("TestName", 5, 6);
        Map<Actor, String> roles = new HashMap<>();
        roles.putIfAbsent(actor, "someone");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String date1S = "2024-12-20";
        Date date1;
        try {
            date1 = sdf.parse(date1S);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Show show = ts.createShow("TestTitle", date1, auditorium.getID(), roles, 20);

        EntityNotFoundException e = assertThrows(EntityNotFoundException.class, () -> ts.createShow("TestTitle", date1, auditorium.getID()+1,  roles, 20));
        assertEquals("Auditorium does not exist!", e.getMessage());

        assertNotNull(ts.getShow(show.getID()));
        assertEquals("TestTitle", ts.getShow(show.getID()).getTitle());
        assertEquals(date1, ts.getShow(show.getID()).getDate());
        assertEquals(auditorium, ts.getAuditoriumByShow(show.getID()));

        ts.deleteShow(show.getID());
        assertNull(ts.getShow(show.getID()));

        ts.createShow("TestTitle1", date1, auditorium.getID(), roles, 20);
        ts.createShow("TestTitle2", date1, auditorium.getID(), roles, 60);
        List<Show> shows = ts.getShows();
        assertEquals(2, shows.size());
    }

    @ParameterizedTest
    @MethodSource("createOrderRepository")
    void orderCreationTest(Repository<Order> orderRepository) {
        if (orderRepository instanceof InMemoryRepository)
            ts = new TheatreService(new AdminService(new InMemoryRepository<Admin>()), new ActorService(new InMemoryRepository<Actor>()), new AuditoriumService(new InMemoryRepository<Auditorium>()), new ShowService(new InMemoryRepository<Show>()), new ViewerService(new InMemoryRepository<Viewer>()), new OrderService(orderRepository));
        else {
            ShowFileRepository showRepository = new ShowFileRepository("src/Test/TestRepository/showTest");
            ts = new TheatreService(new AdminService(new AdminFileRepository("src/Test/TestRepository/adminTest")), new ActorService(new ActorFileRepository("src/Test/TestRepository/actorTest")), new AuditoriumService(new AuditoriumFileRepository("src/Test/TestRepository/auditoriumTest")), new ShowService(showRepository), new ViewerService(new ViewerFileRepository("src/Test/TestRepository/viewerTest")), new OrderService(orderRepository));
            TheatreController tc = new TheatreController(ts);
            ((ShowFileRepository) showRepository).setTheatreController(tc);
        }

        Actor actor = new Actor(666,"TestName", 21, new EMail("tested@gmail.com", "test"), 100);
        Viewer viewer = ts.createViewerAccount("TestViewerName", 20, new EMail("testviewer@gmail.com", "test"));
        Auditorium auditorium = ts.createAuditorium("TestAuditoriumName", 20, 6);
        Map<Actor, String> roles = new HashMap<>();
        roles.putIfAbsent(actor, "TestRole");
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
        seats2.add(5); seats2.add(6); seats2.add(7); seats2.add(8);

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