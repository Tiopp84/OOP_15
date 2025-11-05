package bookingapp.controller;

import bookingapp.App;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class UserMainController {

    @FXML private Button bt_court1;
    @FXML private Button bt_court2;
    @FXML private Button bt_court3;
    @FXML private Button bt_court4;
    @FXML private Button bt_logout;


    @FXML
    public void initialize(){
        bt_logout.setOnAction(a -> {
            try {
                App.setRoot("welcome.fxml");
            } catch (Exception e) {
                e.printStackTrace();
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


    /** Hàm tiện ích hiển thị thông báo */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
