package bookingapp.dao;

import bookingapp.db.DatabaseManager;
import bookingapp.model.LoadStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LoadStatusDAO {
    public boolean isLook(int maSan){
        String sql = "SELECT * FROM San WHERE MaSan = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, maSan);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                if (!rs.getString("TrangThai").equals("HoatDong")) return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Map<Integer, LoadStatus> loadAllLichNgoaiLe(String picked_date, int maSan){
        Map<Integer, LoadStatus> map = new HashMap<>();

        String sql = "SELECT * FROM LichNgoaiLe WHERE Ngay = ? AND MaSan = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, picked_date);
            pstmt.setInt(2, maSan);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int start = Integer.parseInt(rs.getString("ThoiGianBatDau").split(":")[0]);
                int end   = Integer.parseInt(rs.getString("ThoiGianKetThuc").split(":")[0]);
                int end_m   = Integer.parseInt(rs.getString("ThoiGianKetThuc").split(":")[1]);
                if(end_m != 0) end = 24;
                for (int h = start; h < end; h++) {
                    map.put(h, new LoadStatus(h, maSan, picked_date, "Khoa"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }
    public Map<Integer, LoadStatus> loadLichDat(String picked_date, int maSan){
        Map<Integer, LoadStatus> map = new HashMap<>();

        String sql = "SELECT * FROM LichDat WHERE Ngay = ? AND MaSan = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, picked_date);
            pstmt.setInt(2, maSan);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int start = Integer.parseInt(rs.getString("ThoiGianBatDau").split(":")[0]);
                int end   = Integer.parseInt(rs.getString("ThoiGianKetThuc").split(":")[0]);
                int end_m   = Integer.parseInt(rs.getString("ThoiGianKetThuc").split(":")[1]);
                if(end_m != 0) end = 24;
                for (int h = start; h < end; h++) {
                    map.put(h, new LoadStatus(h, maSan, picked_date, "DaDat"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }
}
