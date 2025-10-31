package bookingapp.dao;

import bookingapp.db.DatabaseManager;
import bookingapp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO (Data Access Object) cho đối tượng User.
 * Chứa tất cả logic SQL liên quan đến bảng 'users'.
 */
public class UserDAO {

    /*
     * Xác thực người dùng dựa trên username và password.
     * @param username Tên đăng nhập
     * @param password Mật khẩu
     * @return Đối tượng User nếu hợp lệ, ngược lại trả về null.
     */



    public User validateUser(String username, String password) {
        // Câu lệnh SQL (an toàn) để chống SQL Injection
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        // THAY ĐỔI: Chúng ta gọi hàm static getConnection()
        // Dùng try-with-resources để tự động đóng Connection và PreparedStatement
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password); // TODO: Nên mã hóa (hash) mật khẩu này

            ResultSet rs = pstmt.executeQuery();

            // Nếu tìm thấy một kết quả
            if (rs.next()) {
                // Tạo một đối tượng User và trả về
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                // (Không lấy mật khẩu)
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi xác thực người dùng: " + e.getMessage());
            e.printStackTrace();
        }

        // Trả về null nếu không tìm thấy hoặc có lỗi
        return null;
    }

    /**
     * Thêm một người dùng mới vào CSDL.
     * @param username Tên đăng nhập
     * @param password Mật khẩu
     * @return true nếu thành công, false nếu thất bại (ví dụ: username đã tồn tại).
     */
    public boolean addUser(String username, String password) {

        // TODO: Nên mã hóa mật khẩu trước khi lưu
        // Ví dụ: String hashedPassword = hashFunction(password);
        String sql = "INSERT INTO users(username, password) VALUES(?, ?)";

        // THAY ĐỔI: Chúng ta gọi hàm static getConnection()
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password); // Nên lưu hashedPassword ở đây

            int rowsAffected = pstmt.executeUpdate();

            // Nếu có 1 hàng bị ảnh hưởng (thêm thành công)
            return rowsAffected > 0;

        } catch (SQLException e) {
            // Lỗi SQL (ví dụ: UNIQUE constraint failed - username đã tồn tại)
            System.err.println("Lỗi khi thêm người dùng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Bạn có thể thêm các hàm khác ở đây, ví dụ:
    // public boolean deleteUser(int userId) { ... }
    // public boolean updateUserPassword(String username, String newPassword) { ... }
}

