package bookingapp.dao;

import bookingapp.db.DatabaseManager;
import bookingapp.model.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp DAO (Data Access Object) cho đối tượng Booking.
 * ĐÃ CẬP NHẬT: Thêm hàm getBookingsByUserId().
 */
public class BookingDAO {

    /**
     * Kiểm tra xem một sân có bị đặt vào một ngày/giờ cụ thể không.
     * (Như cũ)
     */
    public boolean isCourtBooked(int courtId, String date, int startTime) {
        String sql = "SELECT COUNT(*) FROM bookings WHERE court_id = ? AND date = ? AND start_time = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, courtId);
            pstmt.setString(2, date);
            pstmt.setInt(3, startTime);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Nếu > 0 (tức là 1), nghĩa là đã bị đặt
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi kiểm tra đặt sân: " + e.getMessage());
        }
        return false; // Mặc định là trống nếu có lỗi
    }

    /**
     * Thêm một lượt đặt sân (booking) mới vào CSDL.
     * (Như cũ)
     */
    public boolean addBooking(Booking booking) throws SQLException {
        String sql = "INSERT INTO bookings(user_id, court_id, date, start_time, end_time, total_price) "
                + "VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, booking.getUserId());
            pstmt.setInt(2, booking.getCourtId());
            pstmt.setString(3, booking.getDate());
            pstmt.setInt(4, booking.getStartTime());
            pstmt.setInt(5, booking.getEndTime());
            pstmt.setDouble(6, booking.getTotalPrice());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // Trả về true nếu 1 hàng được chèn
        }
    }

    /**
     * MỚI: Lấy tất cả các lượt đặt sân của một người dùng cụ thể.
     * Sắp xếp theo ngày mới nhất lên trước.
     * @param userId ID của người dùng.
     * @return một danh sách (List) các đối tượng Booking.
     */
    public List<Booking> getBookingsByUserId(int userId) {
        List<Booking> bookings = new ArrayList<>();
        // Sắp xếp theo 'date' DESC (giảm dần) và 'start_time' ASC (tăng dần)
        String sql = "SELECT * FROM bookings WHERE user_id = ? ORDER BY date DESC, start_time ASC";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = new Booking();
                    booking.setId(rs.getInt("id"));
                    booking.setUserId(rs.getInt("user_id"));
                    booking.setCourtId(rs.getInt("court_id"));
                    booking.setDate(rs.getString("date"));
                    booking.setStartTime(rs.getInt("start_time"));
                    booking.setEndTime(rs.getInt("end_time"));
                    booking.setTotalPrice(rs.getDouble("total_price"));
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy lịch sử đặt sân: " + e.getMessage());
        }
        return bookings; // Trả về danh sách (có thể rỗng)
    }
}

