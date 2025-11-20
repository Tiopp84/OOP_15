package bookingapp.dao;

import bookingapp.db.DatabaseManager;
import bookingapp.model.LichNgoaiLe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LichNgoaiLeDAO {

    public List<LichNgoaiLe> getAll() {
        List<LichNgoaiLe> list = new ArrayList<>();
        String sql = "SELECT * FROM LichNgoaiLe";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new LichNgoaiLe(
                        rs.getInt("MaHoatDong"),
                        rs.getInt("MaSan"),
                        rs.getString("Ngay"),
                        rs.getString("ThoiGianBatDau"),
                        rs.getString("ThoiGianKetThuc"),
                        rs.getString("LoaiHoatDong"),
                        rs.getString("GhiChu")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(LichNgoaiLe item) {
        String sql = "INSERT INTO LichNgoaiLe(MaSan, Ngay, ThoiGianBatDau, ThoiGianKetThuc, LoaiHoatDong, GhiChu) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, item.getMaSan());
            ps.setString(2, item.getNgay());
            ps.setString(3, item.getThoiGianBatDau());
            ps.setString(4, item.getThoiGianKetThuc());
            ps.setString(5, item.getLoaiHoatDong());
            ps.setString(6, item.getGhiChu());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int maHoatDong) {
        String sql = "DELETE FROM LichNgoaiLe WHERE MaHoatDong=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maHoatDong);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
