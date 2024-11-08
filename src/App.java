import ControllerService.TheatreController;
import ControllerService.TheatreService;
import Domain.*;
import RepositoryPackage.InMemoryRepository;

public class App {
    public static void main(String[] args) {
        InMemoryRepository<Ceo> ceoInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<Actor> actorInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<Auditorium> auditoriumInMemoryRepository= new InMemoryRepository<>();
        InMemoryRepository<Show> showInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<Viewer> viewerInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<Ticket> ticketInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<Order> orderInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<Seat> seatInMemoryRepository = new InMemoryRepository<>();

        TheatreService ts = new TheatreService(ceoInMemoryRepository, actorInMemoryRepository,
                auditoriumInMemoryRepository,showInMemoryRepository, viewerInMemoryRepository, ticketInMemoryRepository,
                orderInMemoryRepository, seatInMemoryRepository);

        TheatreController tc = new TheatreController(ts);

        Actor a = new Actor(1,"Peter", 23, new EMail("Email@Test.com","test"),11);
        System.out.println(a.getAge());
        System.out.println(a.getEmail());
        System.out.println(a);

        Ceo c = new Ceo(1,"Boss",45, new EMail("Email@Testceo.com","testceo"));
        System.out.println(c.getAge());
        System.out.println(c.getEmail());
        System.out.println(c);

        Auditorium ad = new Auditorium(1,"Great Hall",25,20);
        System.out.println(ad.getCapacity());
        System.out.println(ad);

        tc.ceoHireActor(34,"Jozsika",54,new EMail("Joco@theatre.eu", "velica22"), 4000);
        System.out.println(actorInMemoryRepository.getAll());
        System.out.println('\n');
        tc.ceoHireActor(23,"Petike", 32, new EMail("petike@test.com","qwe"), 3400);
        tc.ceoHireActor(12,"Jeno", 22, new EMail("Jeno@test.com","rty"), 1200);
        tc.ceoHireActor(76,"Viktorka", 67,new EMail("Viktorka@test.com","uiop"), 7600);

        System.out.println("All actors : "+actorInMemoryRepository.getAll());
        System.out.println('\n');
        tc.ceoFireActor(34);
        tc.ceoFireActor(76);
        System.out.println("Actors after firing some of them : " + actorInMemoryRepository.getAll());

        System.out.println('\n');

        System.out.println("Salary before change : " + actorInMemoryRepository.getByID(12).getSalary());
        tc.ceoChangeSalary(12,6000);
        System.out.println("Salary after change : " + actorInMemoryRepository.getByID(12).getSalary());

    }
}
