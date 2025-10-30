package bookingapp.dao;

import bookingapp.db.DatabaseManager;
import bookingapp.model.Court;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp DAO (Data Access Object) cho đối tượng Court.
 * ĐÃ CẬP NHẬT: Thêm hàm getCourtById().
 */
public class CourtDAO {

    /**
     * Lấy tất cả các sân từ CSDL.
     * (Như cũ)
     */
    public List<Court> getAllCourts() {
        List<Court> courts = new ArrayList<>();
        String sql = "SELECT * FROM courts ORDER BY name";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Court court = new Court();
                court.setId(rs.getInt("id"));
                court.setName(rs.getString("name"));
                court.setType(rs.getString("type"));
                court.setPricePerHour(rs.getDouble("price_per_hour"));
                courts.add(court);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách sân: " + e.getMessage());
        }
        return courts;
    }

    /**
     * MỚI: Lấy thông tin một sân cụ thể bằng ID.
     * @param courtId ID của sân cần tìm.
     * @return một đối tượng Court, hoặc null nếu không tìm thấy.
     */
    public Court getCourtById(int courtId) {
        String sql = "SELECT * FROM courts WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, courtId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Court court = new Court();
                    court.setId(rs.getInt("id"));
                    court.setName(rs.getString("name"));
                    court.setType(rs.getString("type"));
                    court.setPricePerHour(rs.getDouble("price_per_hour"));
                    return court;
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy sân bằng ID: " + e.getMessage());
        }
        return null; // Không tìm thấy
    }
}

