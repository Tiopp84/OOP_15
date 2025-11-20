package bookingapp.dao;

import bookingapp.db.DatabaseManager;
import bookingapp.model.Court;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourtDAO {

    // Lấy tất cả sân từ bảng San
    public List<Court> getAllCourts() {
        List<Court> list = new ArrayList<>();
        String sql = "SELECT * FROM San";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // map cột MaSan->id, TenSan->name, TrangThai->status
                list.add(new Court(
                        rs.getInt("MaSan"),
                        rs.getString("TenSan"),
                        rs.getString("TrangThai")
                ));
            }

        } catch (SQLException e) {
            System.err.println("CourtDAO.getAllCourts() lỗi: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // Thêm sân (chúng ta không dùng describe trong model hiện tại -> lưu MoTa rỗng)
    public boolean addCourt(String name, String status) {
        String sql = "INSERT INTO San (TenSan, TrangThai) VALUES (?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(3, status);

            int updated = ps.executeUpdate();
            return updated > 0;

        } catch (SQLException e) {
            System.err.println("CourtDAO.addCourt() lỗi SQL: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Xóa sân theo MaSan
    public boolean deleteCourt(int id) {
        String sql = "DELETE FROM San WHERE MaSan = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int updated = ps.executeUpdate();
            return updated > 0;

        } catch (SQLException e) {
            System.err.println("CourtDAO.deleteCourt() lỗi SQL: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật trạng thái theo MaSan
    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE San SET TrangThai = ? WHERE MaSan = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);
            int updated = ps.executeUpdate();
            return updated > 0;

        } catch (SQLException e) {
            System.err.println("CourtDAO.updateStatus() lỗi SQL: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
