package Repository;

import Domain.Viewer;
import Domain.EMail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViewerDBRepository implements Repository<Viewer>{
    private final String url = "jdbc:sqlserver://localhost:1433;databaseName=Theatre_MAP_Project;encrypt=true;trustServerCertificate=true";
    private final String userName = "SystemAdmin";
    private final String password = "0000";

    @Override
    public void create(Viewer obj) {

        if (exists(obj.getID())) {  // if the object is present in the database, skip the creation of the same(new) object
            return;
        }

        String sql = "INSERT INTO Viewer(ID,name,age,emailAddress,emailPassword) VALUES(?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, obj.getID());
            ps.setString(2, obj.getName());
            ps.setInt(3, obj.getAge());
            ps.setString(4, obj.getEmail().getEmailAddress());
            ps.setString(5, obj.getEmail().getPassword());

            ps.executeUpdate();
        }catch (SQLException e) {
            System.out.println("Error adding Viewer into the database");
        }
    }

    @Override
    public void delete(Integer objID) {
        String sql = "DELETE FROM Viewer WHERE ID=?";
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, objID);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting Viewer from the database");
            e.printStackTrace();
        }

    }

    @Override
    public void update(Viewer obj) {
        String sql = "UPDATE Viewer SET name = ?, age = ?, emailAddress = ?, emailPassword = ? WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, obj.getName());
            ps.setInt(2, obj.getAge());
            ps.setString(3, obj.getEmail().getEmailAddress());
            ps.setString(4, obj.getEmail().getPassword());
            ps.setInt(5, obj.getID());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating Viewer in the database");
            e.printStackTrace();
        }
    }

    @Override
    public Viewer getByID(Integer id) {
        String sql = "SELECT * FROM Viewer WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                EMail email = new EMail(rs.getString("emailAddress"), rs.getString("emailPassword"));
                Viewer viewer = new Viewer(
                        rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        email
                );
                return viewer;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving Viewer from the database");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Viewer> getAll() {
        String sql = "SELECT * FROM Viewer";
        List<Viewer> viewers = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                EMail email = new EMail(rs.getString("emailAddress"), rs.getString("emailPassword"));
                Viewer viewer = new Viewer(
                        rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        email
                );
                viewers.add(viewer);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving all Viewers from the database");
            e.printStackTrace();
        }
        return viewers;
    }

    private boolean exists(int id) {
        String sql = "SELECT COUNT(*) FROM Viewer WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Returns true if count > 0
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}

