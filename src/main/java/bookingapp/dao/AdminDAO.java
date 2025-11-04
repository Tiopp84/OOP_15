package bookingapp.dao;

import bookingapp.db.DatabaseManager;
import bookingapp.model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    public Admin validateAdmin(String username, String password){
        String sql = "SELECT * FROM admins WHERE username = ? AND password = ?";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                Admin admin = new Admin(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
                return admin;
            }
        }
        catch (SQLException e){
            System.err.println("Lỗi khi xác thực admin: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public boolean addAdmin(String username, String password){
        String sql = "INSERT INTO admins(username, password) VALUES(?, ?)";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            int rowAffected = pstmt.executeUpdate();
            return rowAffected > 0;
        }
        catch (SQLException e){
            System.err.println("Lỗi khi thêm người dùng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAdmin(String username){
        String sql = "DELETE FROM admins WHERE username = ?";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, username);

            int rowAffected = pstmt.executeUpdate();
            return rowAffected > 0;
        }
        catch (SQLException e){
            System.err.println("Lỗi khi thêm người dùng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean changePassword(String username, String new_password) {
        String sql = "UPDATE admins SET password = ? where username = ?";
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
}
