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
                        rs.getInt("NgayBatDau"),
                        rs.getInt("NgayKetThuc"),
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

    public boolean add(int start_day, int end_day, LocalTime start, LocalTime end, float price) {
        String sql = "INSERT INTO BangGia(NgayBatDau, NgayKetThuc, ThoiGianBatDau, ThoiGianKetThuc, GiaMoiGio) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, start_day);
            ps.setInt(2, end_day);
            ps.setString(3, start.toString());
            ps.setString(4, end.toString());
            ps.setFloat(5, price);

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
    public List<PriceTable> getPrice(){
        String sql="SELECT * FROM BangGia";
        List<PriceTable> list = new ArrayList<>();
        try(Connection conn =DatabaseManager.getConnection()){
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Integer id = rs.getInt("MaGia");
                Integer ngayBatDauTrongTuan = rs.getInt("NgayBatDau");
                Integer ngayKetThucTrongTuan = rs.getInt("NgayKetThuc");
                String startTime = rs.getString("ThoiGianBatDau");
                String endTime = rs.getString("ThoiGianKetThuc");
                Float giamoigio = rs.getFloat("GiaMoiGio");
                PriceTable priceTable=new PriceTable(id,ngayBatDauTrongTuan,ngayKetThucTrongTuan,LocalTime.parse(startTime),LocalTime.parse(endTime),giamoigio);
                list.add(priceTable);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
