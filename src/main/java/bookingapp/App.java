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
            System.out.println("Database initialized successfully.");
        } catch (Exception e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
            e.printStackTrace();
            return;
        }


        FXMLLoader fxmlLoader = new FXMLLoader(
                //App.class.getResource("/bookingapp/view/welcome.fxml")
                App.class.getResource("/bookingapp/view/usermainWindow.fxml")
        );

        Parent root = fxmlLoader.load();

        scene = new Scene(root, 600, 500);

        stage.getIcons().add(
                new javafx.scene.image.Image(App.class.getResourceAsStream("/bookingapp/assets/images/icon.png"))
        );

        try {
            String css = App.class.getResource("/bookingapp/assets/style.css").toExternalForm();
            scene.getStylesheets().add(css);
        } catch (Exception e) {
            System.err.println("Warning: Could not load CSS file. " + e.getMessage());
        }

        stage.setTitle("Booking BADMINTON");
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

