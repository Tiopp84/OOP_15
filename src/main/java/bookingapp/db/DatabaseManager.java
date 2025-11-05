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
        String createCourtsTableSQL = "CREATE TABLE IF NOT EXISTS courts ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " name TEXT NOT NULL UNIQUE"
                + ");";

        // courtDetails
        String createCourtDetaolTableSQL = "CREATE TABLE IF NOT EXISTS courtdetails ("
                ;
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

            stmt.execute(createUserTableSQL);
            stmt.execute(createCourtsTableSQL);
//            stmt.execute(createBookingsTableSQL);
            System.out.println("Tables created successfully (if not existed).");

        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

