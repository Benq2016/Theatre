package Repository;

import Domain.EMail;
import Domain.Show;
import Domain.Auditorium;
import Domain.Actor;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowDBRepository implements Repository<Show> {
    private final String url = "jdbc:sqlserver://localhost:1433;databaseName=Theatre_MAP_Project;encrypt=true;trustServerCertificate=true";
    private final String userName = "SystemAdmin";
    private final String password = "0000";

    @Override
    public void create(Show obj) {

        if (exists(obj.getID())) {  // if the object is present in the database, skip the creation of the same(new) object
            return;
        }

        String sql = "INSERT INTO Show (ID, Title, Date, AuditoriumID, Price) VALUES (?, ?, ?, ?, ?)";

        String actorRoleSql = "INSERT INTO Show_Actor_Roles (ShowID, ActorID, Role) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, userName, password)) {
            connection.setAutoCommit(false); // with this, we control that both SQL statements should work,
                                        //bc we cannot push a tuple into the Show table without the actor roles

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, obj.getID());
                ps.setString(2, obj.getTitle());
                ps.setTimestamp(3, new Timestamp(obj.getDate().getTime()));
                ps.setInt(4, obj.getAudit().getID());

//                System.out.println("AuditoriumID:" + obj.getAudit().getID());

                ps.setInt(5, obj.getPrice());
                ps.executeUpdate();

                try (PreparedStatement psRole = connection.prepareStatement(actorRoleSql)) {
                    for (Map.Entry<Actor, String> entry : obj.getRoles().entrySet()) {
                        Actor actor = entry.getKey();
                        String role = entry.getValue();

                        psRole.setInt(1, obj.getID());
                        psRole.setInt(2, actor.getID());
                        psRole.setString(3, role);
                        psRole.addBatch(); // basically, we make a bunch of statements and run them -> efficiency
                    }

                    psRole.executeBatch();
                }

                connection.commit();

            } catch (SQLException e) {
                connection.rollback(); //when problem we revert the process made
                System.out.println("Error creating Show and inserting actor roles. Transaction rolled back.");
                System.out.println();
//                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database.");
        }
    }


    @Override
    public void delete(Integer objID) {
        String deleteRolesSql = "DELETE FROM Show_Actor_Roles WHERE ShowID = ?";
        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = connection.prepareStatement(deleteRolesSql)) {
            ps.setInt(1, objID);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting roles for ShowID " + objID);
        }

        String deleteShowSql = "DELETE FROM Show WHERE ID = ?";
        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = connection.prepareStatement(deleteShowSql)) {
            ps.setInt(1, objID);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting Show from the database");
        }
    }

    /*The Update methods are not used*/

    @Override
    public void update(Show obj) {
        String sql = "UPDATE Show SET Title = ?, Date = ?, AuditoriumID = ?, Price = ? WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, obj.getTitle());
            ps.setTimestamp(2, new Timestamp(obj.getDate().getTime()));
            ps.setInt(3, obj.getAudit().getID());
            ps.setInt(4, obj.getPrice());
            ps.setInt(5, obj.getID());
            ps.executeUpdate();

            updateActorRoles(obj.getID(), obj.getRoles());
        } catch (SQLException e) {
            System.out.println("Error updating Show in the database");
        }
    }

    private void insertActorRoles(int showId, Map<Actor, String> roles) {
        String actorRoleSql = "INSERT INTO Show_Actor_Roles(ShowID, ActorID, Role) VALUES(?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = conn.prepareStatement(actorRoleSql)) {

            for (Map.Entry<Actor, String> entry : roles.entrySet()) {
                ps.setInt(1, showId);
                ps.setInt(2, entry.getKey().getID());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            System.out.println("Error inserting actor roles in the Show_Actor_Roles table");
        }
    }

    private void updateActorRoles(int showId, Map<Actor, String> roles) {
        String deleteRolesSql = "DELETE FROM Show_Actor_Roles WHERE ShowID = ?";
        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = connection.prepareStatement(deleteRolesSql)) {
            ps.setInt(1, showId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting existing roles for ShowID " + showId);
        }

        insertActorRoles(showId, roles);
    }

    /*Update methods-end*/

    @Override
    public Show getByID(Integer id) {
        String sql = "SELECT * FROM Show WHERE ID = ?";
        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int auditoriumId = rs.getInt("AuditoriumID");
                Auditorium audit = new AuditoriumDBRepository().getByID(auditoriumId);
                Map<Actor, String> roles = getActorRolesForShow(id);
                return new Show(
                        rs.getInt("ID"),
                        rs.getString("Title"),
                        rs.getTimestamp("Date"),
                        audit,
                        roles,
                        rs.getInt("Price")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving Show from the database");
        }
        return null;
    }

    //used by the getByID to have the roles MAP
    private Map<Actor, String> getActorRolesForShow(int showId) {
        Map<Actor, String> actorRoles = new HashMap<>();

        // SQL query to fetch actors and roles for a particular show
        String sql = "SELECT actor.ID, actor.name, actor.age, actor.emailAddress, actor.emailPassword, Show_Actor_Roles.Role " +
                "FROM Show_Actor_Roles " +
                "INNER JOIN Actor ON Show_Actor_Roles.ActorID = Actor.ID " +
                "WHERE Show_Actor_Roles.ShowID = ?";

        try (Connection conn = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, showId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    EMail email = new EMail(rs.getString("emailAddress"), rs.getString("emailPassword"));
                    Actor actor = new Actor(rs.getString("name"), rs.getInt("age"), email, rs.getInt("ID"));

                    String role = rs.getString("Role");

                    actorRoles.put(actor, role);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching actors and roles for show: " + showId);
        }

        return actorRoles;
    }

    @Override
    public List<Show> getAll() {
        List<Show> shows = new ArrayList<>();
        String sql = "SELECT * FROM Show";
        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int auditoriumId = rs.getInt("AuditoriumID");
                Auditorium audit = new AuditoriumDBRepository().getByID(auditoriumId);
                Map<Actor, String> roles = getActorRolesForShow(rs.getInt("ID"));
                shows.add(new Show(
                        rs.getInt("ID"),
                        rs.getString("Title"),
                        rs.getTimestamp("Date"),
                        audit,
                        roles,
                        rs.getInt("Price")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving all Shows from the database");
            e.printStackTrace();
        }
        return shows;
    }

    private boolean exists(int id) {
        String sql = "SELECT COUNT(*) FROM Show WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Returns true if count > 0
                }
            }
        } catch (SQLException e) {
//            e.printStackTrace();
        }
        return false;
    }
}

