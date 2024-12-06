package Repository;

import Domain.Actor;
import Domain.EMail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActorDBRepository implements Repository<Actor>{
    private final String url = "jdbc:sqlserver://localhost:1433;databaseName=Theatre_MAP_Project;encrypt=true;trustServerCertificate=true";
    private final String userName = "SystemAdmin";
    private final String password = "0000";

    @Override
    public void create(Actor obj) {
        String sql = "INSERT INTO Actor(ID,name,age,emailAddress,emailPassword,salary) VALUES(?,?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, obj.getID());
            ps.setString(2, obj.getName());
            ps.setInt(3, obj.getAge());
            ps.setString(4, obj.getEmail().getEmailAddress());
            ps.setString(5, obj.getEmail().getPassword());
            ps.setInt(6, obj.getSalary());

            ps.executeUpdate();
        }catch (SQLException e) {
            System.out.println("Error adding Actor into the database");
        }
    }

    @Override
    public void delete(Integer objID) {
        String sql = "DELETE FROM Actor WHERE ID=?";
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, objID);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting Actor from the database");
            e.printStackTrace();
        }

    }

    @Override
    public void update(Actor obj) {
        String sql = "UPDATE Actor SET name = ?, age = ?, emailAddress = ?, emailPassword = ?, salary = ? WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, obj.getName());
            ps.setInt(2, obj.getAge());
            ps.setString(3, obj.getEmail().getEmailAddress());
            ps.setString(4, obj.getEmail().getPassword());
            ps.setInt(5, obj.getSalary());
            ps.setInt(6, obj.getID());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating Actor in the database");
            e.printStackTrace();
        }
    }

    @Override
    public Actor getByID(Integer id) {
        String sql = "SELECT * FROM Actor WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                EMail email = new EMail(rs.getString("emailAddress"), rs.getString("emailPassword"));
                Actor actor = new Actor(
                        rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        email,
                        rs.getInt("salary")
                );
                return actor;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving Actor from the database");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Actor> getAll() {
        String sql = "SELECT * FROM Actor";
        List<Actor> actors = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                EMail email = new EMail(rs.getString("emailAddress"), rs.getString("emailPassword"));
                Actor actor = new Actor(
                        rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        email,
                        rs.getInt("salary")
                );
                actors.add(actor);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving all Actors from the database");
            e.printStackTrace();
        }
        return actors;
    }
}

