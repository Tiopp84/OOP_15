package bookingapp.controller.user;

import bookingapp.App;
import bookingapp.model.User;
import bookingapp.util.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class UserMainController {

    @FXML private VBox contentArea;
    @FXML private Button bt_pricedetail;
    @FXML private Button bt_history;
    @FXML private Button bt_info;
    @FXML private Button bt_logout;
    @FXML private Label Chaomung;
    private User currentUser;



    @FXML
    public void initialize(){
        currentUser = Session.getCurrentUser();
        if(currentUser != null) Chaomung.setText("Chào mừng " + currentUser.getUsername().toUpperCase() + "!");
        else Chaomung.setText("Welcome!");
        loadContent("maincourt.fxml");

        bt_pricedetail.setOnAction(e->{
            loadContent("price_detail.fxml");
        });
        bt_history.setOnAction(e->{
            loadContent("history_booking.fxml");
        });
        bt_info.setOnAction(e->{
            loadContent("info.fxml");
        });


        bt_logout.setOnAction(a -> {
            try {
                App.setRoot("welcome.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void loadContent(String fxml) {
        try {
            VBox view = FXMLLoader.load(getClass().getResource("/bookingapp/view/user/" + fxml));
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
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
