package bookingapp.dao;

import bookingapp.db.DatabaseManager;
import bookingapp.model.Booking;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public List<Booking> getAll() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM LichDat";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Booking(
                        rs.getInt("MaDatLich"),
                        rs.getInt("User_Id"),
                        rs.getInt("MaSan"),
                        LocalDate.parse(rs.getString("Ngay")),
                        LocalTime.parse(rs.getString("ThoiGianBatDau")),
                        LocalTime.parse(rs.getString("ThoiGianKetThuc")),
                        rs.getDouble("GiaLucDat")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Lỗi BookingDAO.getAll: " + e.getMessage());
        }
        return list;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM LichDat WHERE MaDatLich = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi BookingDAO.delete: " + e.getMessage());
            return false;
        }
    }

    public boolean add(Booking booking){
        String sql = "INSERT INTO LichDat(MaSan, User_id, Ngay, ThoiGianBatDau, ThoiGianKetThuc, GiaLucDat) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, booking.getCourtId());
            pstmt.setInt(2, booking.getUserId());
            pstmt.setString(3, booking.getBookingDate());
            pstmt.setString(4, booking.getStartTime());
            pstmt.setString(5, booking.getEndTime());
            pstmt.setDouble(6, booking.getTotalPrice());

            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0; // == 1

        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm Lịch Đặt: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
