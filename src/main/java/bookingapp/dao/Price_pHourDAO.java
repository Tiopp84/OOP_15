package bookingapp.dao;

import bookingapp.db.DatabaseManager;
import bookingapp.model.Price_pHour;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Price_pHourDAO {
    public Price_pHour getPrice(int day_in_week, int hour){
        String Hour = String.format("%02d:00",hour);

        String sql = "SELECT * FROM BangGia WHERE NgayBatDau <= ? AND NgayKetThuc >= ? AND ThoiGianBatDau <= ? AND ThoiGianKetThuc > ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             ps.setInt(1, day_in_week);
             ps.setInt(2, day_in_week);
             ps.setString(3, Hour);
             ps.setString(4, Hour);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return new Price_pHour(day_in_week, hour, rs.getDouble("GiaMoiGio"));
            }


        } catch (SQLException e) {
            System.err.println("Lỗi lay GiaMoiGio: " + e.getMessage());
        }
        return null;
    }

}
