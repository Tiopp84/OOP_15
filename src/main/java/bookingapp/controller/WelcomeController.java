package bookingapp.controller;

import bookingapp.App;
import bookingapp.dao.UserDAO;
import bookingapp.model.User;
import bookingapp.util.Session;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

/**
 * Controller cho welcome.fxml.
 * ĐÃ CẬP NHẬT để sử dụng CSDL.
 */
public class WelcomeController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton; // Nút Đăng ký

    // Khởi tạo DAO để tái sử dụng
    private UserDAO userDAO;

    public WelcomeController() {
        // Khởi tạo UserDAO khi Controller được tạo
        this.userDAO = new UserDAO();
    }

    /**
     * Xử lý khi nhấn nút "Đăng nhập".
     * Sẽ gọi UserDAO để xác thực.
     */
    @FXML
    private void handleLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi Đăng nhập", "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!");
            return;
        }

        // BƯỚC 1: Gọi DAO để xác thực
        User user = userDAO.validateUser(username, password);

        // BƯỚC 2: Kiểm tra kết quả
        if (user != null) {
            // Đăng nhập thành công!
            // BƯỚC 3: Lưu người dùng vào Session
            Session.setCurrentUser(user);

            // BƯỚC 4: Chuyển sang màn hình chính
            App.setRoot("mainWindow.fxml");

        } else {
            // Đăng nhập thất bại
            showAlert(Alert.AlertType.ERROR, "Lỗi Đăng nhập", "Tên đăng nhập hoặc mật khẩu không chính xác!");
        }
    }

    /**
     * Xử lý khi nhấn nút "Đăng ký".
     * Chuyển sang màn hình register.fxml.
     */
    @FXML
    private void handleSwitchToRegister() throws IOException {
        App.setRoot("register.fxml");
    }

    /**
     * Hàm tiện ích (helper) để hiển thị Alert
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Không có tiêu đề phụ
        alert.setContentText(message);
        alert.showAndWait();
    }
}

