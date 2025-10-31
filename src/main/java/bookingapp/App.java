package bookingapp;

import bookingapp.db.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            DatabaseManager.getInstance();
            System.out.println("Database initialized successfully."); // Log cho bạn
        } catch (Exception e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
            e.printStackTrace();
            // (Tùy chọn) Bạn có thể hiển thị Alert lỗi ở đây nếu muốn
            return; // Thoát nếu không thể khởi tạo CSDL
        }


        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/bookingapp/view/welcome.fxml"));

        Parent root = fxmlLoader.load();

        scene = new Scene(root, 600, 500);

        try {
            String css = App.class.getResource("/bookingapp/assets/style.css").toExternalForm();
            scene.getStylesheets().add(css);
        } catch (Exception e) {
            System.err.println("Warning: Could not load CSS file. " + e.getMessage());
        }

        stage.setTitle("Ứng dụng Đặt sân Cầu lông");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMinWidth(700);
        stage.setMinHeight(600);
        stage.show();
    }


    public static void setRoot(String fxml) throws IOException {
        String fxmlPath = "/bookingapp/view/" + fxml;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlPath));

        Parent root = fxmlLoader.load();
        scene.setRoot(root);
    }

    public static void main(String[] args) {
        launch();
    }
}

