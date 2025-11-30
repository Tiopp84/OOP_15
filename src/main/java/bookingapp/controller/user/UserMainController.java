package bookingapp.controller.user;

import bookingapp.App;
import bookingapp.dao.UserDAO;
import bookingapp.model.User;
import bookingapp.util.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class UserMainController {

    @FXML
    private BorderPane contentArea;
    @FXML
    private Button bt_booking;
    @FXML
    private Button bt_pricedetail;
    @FXML
    private Button bt_history;
    @FXML
    private Button bt_info;
    @FXML
    private Button info_court;
    @FXML
    private Button bt_logout;
    @FXML
    private Label Chaomung;
    private User currentUser;

    private static UserMainController mainController;

    private UserDAO userdao;

    public static void setMainController(UserMainController controller) {
        mainController = controller;
    }

    public static UserMainController getMainController() {
        return mainController;
    }

    @FXML
    public void initialize() {
        UserMainController.setMainController(this);
        currentUser = Session.getCurrentUser();
        if (currentUser == null) {
            userdao = new UserDAO();
            Chaomung.setText("Welcome!");
            currentUser = userdao.validateUser("sang", "123");
            Session.setCurrentUser(currentUser);
        }
        Chaomung.setText("Chào mừng " + currentUser.getFull_name() + "!");

        loadContent("maincourt.fxml");

        bt_pricedetail.setOnAction(e -> {
            loadContent("price_detail.fxml");
        });
        bt_history.setOnAction(e -> {
            loadContent("history_booking.fxml");
        });
        bt_info.setOnAction(e -> {
            loadContent("info.fxml");
        });
        bt_booking.setOnAction(e -> {
            loadContent("maincourt.fxml");
        });
        info_court.setOnAction(e -> {
            loadContent("info_court.fxml");
        });

        bt_logout.setOnAction(a -> {
            try {
                App.setRoot("welcome.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void loadContent(String fxml) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/bookingapp/view/user/" + fxml));
            contentArea.setCenter(view);
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
