package bookingapp.dao;

import bookingapp.db.DatabaseManager;
import bookingapp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public User validateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi xác thực người dùng: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public boolean addUser(String username, String password, String role) {
        String sql = "INSERT INTO users(username, password, role) VALUES(?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);

            int rowsAffected = pstmt.executeUpdate();// dung cho cac truy van khong tra ve du lieu
            // tra ve so luong dong bi thay doi trong csdl

            return rowsAffected > 0; // == 1

        } catch (SQLException e) {
            // Lỗi SQL (ví dụ: UNIQUE constraint failed - username đã tồn tại)
            System.err.println("Lỗi khi thêm người dùng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(String username){
        String sql = "DELETE FROM users WHERE username = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // == 1

        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa người dùng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean changePassword(String username, String new_password){
        String sql = "UPDATE users SET password = ? where username = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, new_password);
            pstmt.setString(2, username);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected == 1;

        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa người dùng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public boolean changePassword2(Integer user_id, String new_password){
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, new_password);
            pstmt.setInt(2, user_id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

