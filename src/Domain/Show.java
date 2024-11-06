package Domain;
import java.util.Map;

public class Show implements HasID{
    private Integer id;
    private String title;
    private Auditorium audit;
    private Map<Actor, String> shows;

    protected Show(Integer id, String title, Auditorium audit, Map<Actor, String> shows) {
        this.id = id;
        this.title = title;
        this.audit = audit;
        this.shows = shows;
    }

    @Override
    public Integer getID() {
        return this.id;
    }
}
