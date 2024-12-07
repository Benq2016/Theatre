package Repository;

import Domain.Auditorium;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuditoriumDBRepository implements Repository<Auditorium>{
    private final String url = "jdbc:sqlserver://localhost:1433;databaseName=Theatre_MAP_Project;encrypt=true;trustServerCertificate=true";
    private final String userName = "SystemAdmin";
    private final String password = "0000";

    @Override
    public void create(Auditorium obj) {
        if (exists(obj.getID())) {
            return;  // Skip creation if the Auditorium already exists
        }

        String sql = "INSERT INTO Auditorium (ID, name, rows, cols) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, obj.getID());
            ps.setString(2, obj.getName());
            ps.setInt(3, obj.getRows());
            ps.setInt(4, obj.getCols());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding Auditorium to the database");
        }
    }

    @Override
    public void delete(Integer objID) {
        String sql = "DELETE FROM Auditorium WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, objID);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting Auditorium from the database");
        }
    }

    @Override
    public void update(Auditorium obj) {
        String sql = "UPDATE Auditorium SET name = ?, rows = ?, cols = ? WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, obj.getName());
            ps.setInt(2, obj.getRows());
            ps.setInt(3, obj.getCols());
            ps.setInt(4, obj.getID());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating Auditorium in the database");
        }
    }

    @Override
    public Auditorium getByID(Integer id) {
        String sql = "SELECT * FROM Auditorium WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Auditorium(
                        rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getInt("rows"),
                        rs.getInt("cols")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving Auditorium from the database");
        }
        return null;
    }

    @Override
    public List<Auditorium> getAll() {
        List<Auditorium> auditoriums = new ArrayList<>();
        String sql = "SELECT * FROM Auditorium";
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Auditorium auditorium = new Auditorium(
                        rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getInt("rows"),
                        rs.getInt("cols")
                );
                auditoriums.add(auditorium);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving all Auditoriums from the database");
        }
        return auditoriums;
    }

    private boolean exists(int id) {
        String sql = "SELECT COUNT(*) FROM Auditorium WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
