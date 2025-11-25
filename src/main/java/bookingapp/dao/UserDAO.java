package bookingapp.dao;

import bookingapp.db.DatabaseManager;
import bookingapp.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class UserDAO {

    public User validateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("full_name"), rs.getString("phone_number"), rs.getString("password"), rs.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi xác thực người dùng: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    // Lấy tất cả users
    public ObservableList<User> getAllUsers() {
        ObservableList<User> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                list.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("password"),
                        rs.getString("role")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Lỗi load users: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    // Tìm kiếm users theo username / full_name / phone_number
    public ObservableList<User> searchUsers(String keyword) {
        ObservableList<User> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users WHERE username LIKE ? OR full_name LIKE ? OR phone_number LIKE ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String kw = "%" + keyword + "%";
            pstmt.setString(1, kw);
            pstmt.setString(2, kw);
            pstmt.setString(3, kw);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("full_name"),
                            rs.getString("phone_number"),
                            rs.getString("password"),
                            rs.getString("role")
                    ));
                }
            }

        } catch (SQLException e) {
            System.err.println("Lỗi search users: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    // Thêm user
    public boolean addUser(String username, String full_name, String phone_number, String password, String role) {
        String sql = "INSERT INTO users(username, full_name, phone_number, password, role) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, full_name);
            pstmt.setString(3, phone_number);
            pstmt.setString(4, password);
            pstmt.setString(5, role);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm người dùng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Xóa user theo username
    public boolean deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa người dùng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Đổi mật khẩu theo username
    public boolean changePassword(String username, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE username = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected == 1;

        } catch (SQLException e) {
            System.err.println("Lỗi khi đổi mật khẩu: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean changePassword2(int id, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newPassword);
            pstmt.setInt(2, id);

            return pstmt.executeUpdate() == 1;

        } catch (SQLException e) {
            System.err.println("Lỗi khi đổi mật khẩu theo ID: " + e.getMessage());
            return false;
        }
    }

}
