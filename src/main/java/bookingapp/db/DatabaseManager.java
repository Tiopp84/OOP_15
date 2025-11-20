package bookingapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String DATABASE_URL = "jdbc:sqlite:badminton.db";
    private static DatabaseManager instance;

    private DatabaseManager() {
        createTables();
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    private void createTables() {
        // 1. Bảng users
        String createUserTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " username TEXT NOT NULL UNIQUE," +
                " password TEXT NOT NULL," +
                " role TEXT NOT NULL" +
                ");";

        // 2. Bảng courts
        String createSanTableSQL = "CREATE TABLE IF NOT EXISTS San (" +
                " MaSan INTEGER PRIMARY KEY AUTOINCREMENT," +
                " TenSan TEXT NOT NULL," +
//                " MoTa TEXT," +
                " TrangThai TEXT DEFAULT 'HoatDong'" +
                ");";

        // 3. Bảng BangGia
        String createBangGiaTableSQL = "CREATE TABLE IF NOT EXISTS BangGia (" +
                " MaGia INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NgayBatDau INTEGER NOT NULL," +
                " NgayKetThuc INTEGER NOT NULL," +
                " ThoiGianBatDau TEXT NOT NULL," +
                " ThoiGianKetThuc TEXT NOT NULL," +
                " GiaMoiGio REAL NOT NULL" +
                ");";

        // 4. Bảng LichDat
        String createLichDatTableSQL = "CREATE TABLE IF NOT EXISTS LichDat (" +
                " MaDatLich INTEGER PRIMARY KEY AUTOINCREMENT," +
                " MaSan INTEGER NOT NULL," +
                " User_id INTEGER NOT NULL," +
                " Ngay TEXT NOT NULL," +
                " ThoiGianBatDau TEXT NOT NULL," +
                " ThoiGianKetThuc TEXT NOT NULL," +
                " GiaLucDat REAL," +
//                " TrangThaiThanhToan TEXT DEFAULT 'ChuaThanhToan'," +
                " FOREIGN KEY (MaSan) REFERENCES San(MaSan)," +
                " FOREIGN KEY (User_id) REFERENCES users(id)," +
                " UNIQUE (MaSan, Ngay, ThoiGianBatDau)" +
                ");";

        // 5. Bảng LichNgoaiLe
        String createLichNgoaiLeTableSQL = "CREATE TABLE IF NOT EXISTS LichNgoaiLe (" +
                " MaHoatDong INTEGER PRIMARY KEY AUTOINCREMENT," +
                " MaSan INTEGER NOT NULL," +
                " Ngay TEXT NOT NULL," +
                " ThoiGianBatDau TEXT NOT NULL," +
                " ThoiGianKetThuc TEXT NOT NULL," +
                " LoaiHoatDong TEXT NOT NULL," +
                " GhiChu TEXT," +
                " FOREIGN KEY (MaSan) REFERENCES San(MaSan)" +
                ");";

        // --- Chèn admin mặc định nếu chưa tồn tại ---
        String insertAdminSQL = "INSERT INTO users(username, password, role) " +
                "SELECT 'admin', 'admin123', 'admin' " +
                "WHERE NOT EXISTS (SELECT 1 FROM users WHERE username='admin');";
        // --- Cập nhật admin nếu đã tồn tại để đảm bảo password là 'admin123' ---
        String updateAdminSQL = "UPDATE users SET password='admin123', role='admin' WHERE username='admin';";


        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Tạo các bảng
            stmt.execute(createUserTableSQL);
            stmt.execute(createSanTableSQL);
            stmt.execute(createBangGiaTableSQL);
            stmt.execute(createLichDatTableSQL);
            stmt.execute(createLichNgoaiLeTableSQL);
            stmt.execute(insertAdminSQL);
            stmt.execute(updateAdminSQL);
            insertDefaultCourts(stmt);

            System.out.println("Tables created successfully and default admin inserted/updated.");

        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void insertDefaultCourts(Statement stmt) throws SQLException {
        String[] courts = { "Sân 1", "Sân 2", "Sân 3", "Sân 4" };

        for (String court : courts) {
            String sql = "INSERT INTO San (TenSan) " +
                    "SELECT '" + court + "' " +
                    "WHERE NOT EXISTS (SELECT 1 FROM San WHERE TenSan='" + court + "');";
            stmt.executeUpdate(sql);
        }
    }
}
