package bookingapp.dao;

import bookingapp.db.DatabaseManager;
import bookingapp.model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public List<Booking> getAll() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings"; // đúng theo tên model bạn nói

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Booking(
                        rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getInt("courtId"),
                        rs.getString("bookingDate"),
                        rs.getString("startTime"),
                        rs.getString("endTime"),
                        rs.getDouble("totalPrice")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Lỗi BookingDAO.getAll: " + e.getMessage());
        }
        return list;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM bookings WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi BookingDAO.delete: " + e.getMessage());
            return false;
        }
    }
}
