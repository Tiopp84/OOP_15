package bookingapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Lớp Singleton để quản lý kết nối CSDL và tạo bảng.
 * Đây là "trái tim" của CSDL.
 */
public class DatabaseManager {

    // Tên file CSDL. Nó sẽ được tạo ở thư mục gốc của project.
    private static final String DATABASE_URL = "jdbc:sqlite:badminton.db";

    private static DatabaseManager instance;

    /**
     * Constructor (private) sẽ tự động tạo bảng.
     */
    private DatabaseManager() {
        // Tự động gọi hàm tạo bảng khi khởi tạo
        createTables();
    }

    /**
     * Hàm để lấy đối tượng Singleton (thể hiện duy nhất).
     * Đây là hàm mà App.java đã gọi.
     */
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Hàm (public) này sẽ được các lớp DAO gọi để lấy một kết nối mới.
     * DAO sẽ chịu trách nhiệm đóng kết nối này.
     * @return Đối tượng Connection
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    /**
     * Hàm (private) để tạo các bảng nếu chúng chưa tồn tại.
     * Sẽ được gọi một lần duy nhất khi Singleton được tạo.
     */
    private void createTables() {
        // Câu lệnh SQL để tạo bảng 'users'
        String createUserTableSQL = "CREATE TABLE IF NOT EXISTS users ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " username TEXT NOT NULL UNIQUE,"
                + " password TEXT NOT NULL"
                + ");";

        // Câu lệnh SQL để tạo bảng 'courts' (sân)
        String createCourtsTableSQL = "CREATE TABLE IF NOT EXISTS courts ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " name TEXT NOT NULL UNIQUE,"
                + " location TEXT,"
                + " price_per_hour REAL NOT NULL"
                + ");";

        // Câu lệnh SQL để tạo bảng 'bookings' (lượt đặt)
        String createBookingsTableSQL = "CREATE TABLE IF NOT EXISTS bookings ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " user_id INTEGER NOT NULL,"
                + " court_id INTEGER NOT NULL,"
                + " booking_date TEXT NOT NULL,"
                + " start_time TEXT NOT NULL,"
                + " end_time TEXT NOT NULL,"
                + " total_price REAL NOT NULL,"
                + " FOREIGN KEY (user_id) REFERENCES users (id),"
                + " FOREIGN KEY (court_id) REFERENCES courts (id)"
                + ");";

        // Dùng try-with-resources để đảm bảo kết nối được đóng
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Thực thi cả 3 câu lệnh
            stmt.execute(createUserTableSQL);
            stmt.execute(createCourtsTableSQL);
            stmt.execute(createBookingsTableSQL);
            System.out.println("Tables created successfully (if not existed).");

        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

