package bookingapp.controller;

import bookingapp.App;
import bookingapp.dao.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Controller cho register.fxml.
 * ĐÃ CẬP NHẬT để sử dụng CSDL.
 */
public class RegisterController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField; // Ô xác nhận mật khẩu
    @FXML
    private Button registerButton;
    @FXML
    private Button backButton;

    // Khởi tạo DAO để tái sử dụng
    private UserDAO userDAO;

    public RegisterController() {
        // Khởi tạo UserDAO khi Controller được tạo
        this.userDAO = new UserDAO();
    }

    /**
     * Xử lý khi nhấn nút "Đăng ký".
     * Sẽ gọi UserDAO để tạo người dùng mới.
     */
    @FXML
    private void handleRegister() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // 1. Kiểm tra các trường
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi Đăng ký", "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        // 2. Kiểm tra mật khẩu khớp
        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Lỗi Đăng ký", "Mật khẩu xác nhận không khớp!");
            return;
        }

        // 3. Gọi DAO để thêm người dùng
        boolean success = userDAO.addUser(username, password);

        // 4. Kiểm tra kết quả từ CSDL
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đăng ký tài khoản thành công! \nVui lòng quay lại để đăng nhập.");
            // Tùy chọn: Tự động chuyển về trang đăng nhập
            // handleBackToLogin();
        } else {
            // Lỗi này có thể xảy ra nếu CSDL bị lỗi, hoặc tên 'username' đã tồn tại
            // (vì chúng ta đã đặt 'username' là UNIQUE trong CSDL)
            showAlert(Alert.AlertType.ERROR, "Lỗi Đăng ký", "Tên đăng nhập này đã tồn tại!");
        }
    }

    /**
     * Xử lý khi nhấn nút "Quay về".
     * Chuyển về màn hình welcome.fxml.
     */
    @FXML
    private void handleBackToLogin() throws IOException {
        App.setRoot("welcome.fxml");
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

