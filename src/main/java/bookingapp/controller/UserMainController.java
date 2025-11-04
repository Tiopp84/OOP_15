package bookingapp.controller;

import bookingapp.App;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class UserMainController {

    // Khai báo các button từ FXML
    @FXML private Button button_court1;
    @FXML private Button button_court2;
    @FXML private Button button_court3;
    @FXML private Button button_court4;


    @FXML
    public void initialize() throws IOException{
        // Gắn sự kiện cho các sân
        button_court1.setOnAction(e -> {
            try {
                datSan(1);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        button_court2.setOnAction(e -> {
            try {
                datSan(2);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        button_court3.setOnAction(e -> {
            try {
                datSan(3);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        button_court4.setOnAction(e -> {
            try {
                datSan(4);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


    }

    private void datSan(int soSan) throws IOException {
        try {
            App.setRoot("welcome.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleLogout() {
        try {
            App.setRoot("welcome.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Hàm tiện ích hiển thị thông báo */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
