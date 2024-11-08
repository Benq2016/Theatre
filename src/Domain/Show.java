package Domain;
import java.util.Map;

public class Show implements HasID{
    private Integer id;
    private String title;
    private Auditorium audit;
    private Map<Actor, String> roles;
    private String date;

    public Show(Integer id, String title, Auditorium audit, Map<Actor, String> roles, String date) {
        this.id = id;
        this.title = title;
        this.audit = audit;
        this.roles = roles;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<Actor, String> getRoles() {
        return roles;
    }

    public void setRoles(Map<Actor, String> roles) {
        this.roles = roles;
    }

    public Auditorium getAudit() {
        return audit;
    }

    public void setAudit(Auditorium audit) {
        this.audit = audit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Integer getID() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Show{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", audit=" + audit +
                ", shows=" + roles +
                '}';
    }
}
