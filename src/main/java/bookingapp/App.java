package bookingapp;

import bookingapp.db.DatabaseManager; // THÊM IMPORT NÀY
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Lớp JavaFX Application chính.
 * Đã cập nhật để khởi tạo CSDL khi bắt đầu.
 */
public class App extends Application {

    private static Scene scene;

    /**
     * Hàm start() - Khởi tạo CSDL và tải màn hình đầu tiên.
     */
    @Override
    public void start(Stage stage) throws IOException {

        // THAY ĐỔI Ở ĐÂY: Khởi tạo CSDL ngay lập tức
        // Lệnh này sẽ gọi hàm "createTables()" trong DatabaseManager
        try {
            DatabaseManager.getInstance();
            System.out.println("Database initialized successfully."); // Log cho bạn
        } catch (Exception e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
            e.printStackTrace();
            // (Tùy chọn) Bạn có thể hiển thị Alert lỗi ở đây nếu muốn
            return; // Thoát nếu không thể khởi tạo CSDL
        }


        // 1. Tải FXML (giữ nguyên)
        // Lưu ý: đường dẫn tuyệt đối bắt đầu bằng "/"
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/bookingapp/view/welcome.fxml"));

        // 2. Tải FXML -> nó sẽ tạo ra toàn bộ giao diện (giữ nguyên)
        Parent root = fxmlLoader.load();

        // 3. Tạo Scene (giữ nguyên)
        scene = new Scene(root, 600, 500);

        // 4. Tải file CSS (giữ nguyên)
        try {
            String css = App.class.getResource("/bookingapp/assets/style.css").toExternalForm();
            scene.getStylesheets().add(css);
        } catch (Exception e) {
            System.err.println("Warning: Could not load CSS file. " + e.getMessage());
        }

        // 5. Thiết lập cửa sổ (giữ nguyên)
        stage.setTitle("Ứng dụng Đặt sân Cầu lông");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMinWidth(700);
        stage.setMinHeight(600);
        stage.show();
    }

    /**
     * Hàm setRoot() - để chuyển đổi màn hình (giữ nguyên)
     */
    public static void setRoot(String fxml) throws IOException {
        // Tự động thêm tiền tố cho đường dẫn FXML
        String fxmlPath = "/bookingapp/view/" + fxml;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlPath));

        Parent root = fxmlLoader.load();
        scene.setRoot(root);
    }

    /**
     * Hàm main() (giữ nguyên)
     */
    public static void main(String[] args) {
        launch();
    }
}

