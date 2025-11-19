package bookingapp.controller;

import bookingapp.App;
import bookingapp.dao.UserDAO;

import bookingapp.model.User;
import bookingapp.util.Session;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class WelcomeController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;

    private UserDAO userDAO;

    public WelcomeController() {
        this.userDAO = new UserDAO();
    }


    @FXML
    private void handleLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi Đăng nhập", "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!");
            return;
        }

        User user = userDAO.validateUser(username, password);

        if (user != null) {
            if(user.getRole().equals("user")){
                Session.setCurrentUser(user);
                App.setRoot("user/usermainWindow.fxml");
            }
            else{
                Session.setCurrentUser(user);
                App.setRoot("admin/adminmainWindow.fxml");
            }
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Lỗi Đăng nhập", "Tên đăng nhập hoặc mật khẩu không chính xác!");
        }
    }


    @FXML
    private void handleSwitchToRegister() throws IOException {
        App.setRoot("register.fxml");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

