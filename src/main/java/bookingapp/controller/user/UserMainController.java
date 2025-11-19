package bookingapp.controller.user;

import bookingapp.App;
import bookingapp.model.User;
import bookingapp.util.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

import java.io.IOException;

public class UserMainController {

    @FXML private Button bt_court1;
    @FXML private Button bt_court2;
    @FXML private Button bt_court3;
    @FXML private Button bt_court4;
    @FXML private Button bt_logout;
    @FXML private Label Chaomung;
    private User currentUser;


    @FXML
    public void initialize(){
        currentUser = Session.getCurrentUser();
        if(currentUser != null) Chaomung.setText("Chào mừng " + currentUser.getUsername().toUpperCase() + "!");
        else Chaomung.setText("Welcome!");
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
