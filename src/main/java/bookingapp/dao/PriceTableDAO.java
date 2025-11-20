package bookingapp.dao;

import bookingapp.db.DatabaseManager;
import bookingapp.model.PriceTable;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PriceTableDAO {

    public List<PriceTable> getAll() {
        List<PriceTable> list = new ArrayList<>();
        String sql = "SELECT * FROM BangGia";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new PriceTable(
                        rs.getInt("MaGia"),
                        rs.getString("NgayTrongTuan"),
                        LocalTime.parse(rs.getString("ThoiGianBatDau")),
                        LocalTime.parse(rs.getString("ThoiGianKetThuc")),
                        rs.getFloat("GiaMoiGio")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Lỗi PriceTableDAO.getAll: " + e.getMessage());
        }
        return list;
    }

    public boolean add(String day, LocalTime start, LocalTime end, float price) {
        String sql = "INSERT INTO BangGia(NgayTrongTuan, ThoiGianBatDau, ThoiGianKetThuc, GiaMoiGio) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, day);
            ps.setString(2, start.toString());
            ps.setString(3, end.toString());
            ps.setFloat(4, price);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi PriceTableDAO.add: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM BangGia WHERE MaGia = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi PriceTableDAO.delete: " + e.getMessage());
            return false;
        }
    }
}
