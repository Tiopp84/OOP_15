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
        // users
        String createUserTableSQL = "CREATE TABLE IF NOT EXISTS users ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " username TEXT NOT NULL UNIQUE,"
                + " password TEXT NOT NULL,"
                + " role TEXT NOT NULL"
                + ");";
       // courts
        String createSanTableSQL = "CREATE TABLE IF NOT EXISTS San ("
                + " MaSan INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " TenSan TEXT NOT NULL,"
                + " MoTa TEXT,"
                + " TrangThai TEXT DEFAULT 'HoatDong'"
                + ");";
        // 3. Bảng Quy tắc Giá
        String createBangGiaTableSQL = "CREATE TABLE IF NOT EXISTS BangGia ("
                + " MaGia INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " NgayTrongTuan TEXT NOT NULL,"
                + " ThoiGianBatDau TEXT NOT NULL,"  // Lưu dạng "HH:MM:SS"
                + " ThoiGianKetThuc TEXT NOT NULL," // Lưu dạng "HH:MM:SS"
                + " GiaMoiGio REAL NOT NULL"        // Dùng REAL cho số thập phân (tiền)
                + ");";
        // 4. Bảng Lịch Đặt (của khách)
        String createLichDatTableSQL = "CREATE TABLE IF NOT EXISTS LichDat ("
                + " MaDatLich INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " MaSan INTEGER NOT NULL,"
                + " User_id INTEGER NOT NULL,"
                + " Ngay TEXT NOT NULL,"             // Lưu dạng "YYYY-MM-DD"
                + " ThoiGianBatDau TEXT NOT NULL,"
                + " ThoiGianKetThuc TEXT NOT NULL,"
                + " GiaLucDat REAL,"
                + " TrangThaiThanhToan TEXT DEFAULT 'ChuaThanhToan',"
                + " FOREIGN KEY (MaSan) REFERENCES San(MaSan),"
                + " FOREIGN KEY (User_id) REFERENCES users(id),"
                + " UNIQUE (MaSan, Ngay, ThoiGianBatDau)"
                + ");";
        // 5. Bảng Lịch Ngoại Lệ (Đóng/Sự kiện)
        String createLichNgoaiLeTableSQL = "CREATE TABLE IF NOT EXISTS LichNgoaiLe ("
                + " MaHoatDong INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " MaSan INTEGER NOT NULL,"
                + " Ngay TEXT NOT NULL,"
                + " ThoiGianBatDau TEXT NOT NULL,"
                + " ThoiGianKetThuc TEXT NOT NULL,"
                + " LoaiHoatDong TEXT NOT NULL," // ('Khoa', 'SuKien', 'BaoTri')
                + " GhiChu TEXT,"
                + " FOREIGN KEY (MaSan) REFERENCES San(MaSan)"
                + ");";

        // Dùng try-with-resources để đảm bảo kết nối được đóng
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(createUserTableSQL);
            stmt.execute(createSanTableSQL);
            stmt.execute(createBangGiaTableSQL);
            stmt.execute(createLichDatTableSQL);
            stmt.execute(createLichNgoaiLeTableSQL);
            System.out.println("Tables created successfully (if not existed).");

        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

