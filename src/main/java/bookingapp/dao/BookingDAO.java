package bookingapp.dao;

import bookingapp.db.DatabaseManager;
import bookingapp.model.Booking;
import bookingapp.util.Session;

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
                        rs.getInt("userId"),
                        rs.getInt("courtId"),
                        LocalDate.parse(rs.getString("bookingDate")),
                        LocalTime.parse(rs.getString("startTime")),
                        LocalTime.parse(rs.getString("endTime")),
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

            int rowsAffected = pstmt.executeUpdate();// dung cho cac truy van khong tra ve du lieu
            // tra ve so luong dong bi thay doi trong csdl

            return rowsAffected > 0; // == 1

        } catch (SQLException e) {
            // Lỗi SQL (ví dụ: UNIQUE constraint failed - username đã tồn tại)
            System.err.println("Lỗi khi thêm Lịch Đặt: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public List<Booking> loadBooking(){
        List<Booking> result = new ArrayList<>();
        StringBuilder sql=new StringBuilder("select * from LichDat ");
        StringBuilder join = new StringBuilder(" join San s on LichDat.MaSan = s.MaSan ");
        StringBuilder where = new StringBuilder(" where 1=1 and User_id = "+ Session.getCurrentUser().getId());
        sql.append(join);
        sql.append(where);
        try (Connection conn = DatabaseManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql.toString());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Booking booking = new Booking();
                booking.setBookingDate(LocalDate.parse(rs.getString("Ngay")));
                booking.setStartTime(LocalTime.parse(rs.getString("ThoiGianBatDau")));
                booking.setEndTime(LocalTime.parse(rs.getString("ThoiGianKetThuc")));
                booking.setTotalPrice(rs.getDouble("GiaLucDat"));
                booking.setCourtName(rs.getString("TenSan"));
                result.add(booking);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
