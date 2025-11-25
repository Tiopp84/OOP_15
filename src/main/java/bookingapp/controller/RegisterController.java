package bookingapp.controller;

import bookingapp.App;
import bookingapp.dao.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;


public class RegisterController {

    @FXML
    private TextField usernameField;
    @FXML private TextField fld_fullname;
    @FXML private TextField fld_phonenumber;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
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


    @FXML
    private void handleRegister() throws IOException {
        String username = usernameField.getText();
        String full_name = fld_fullname.getText();
        String phone_number = fld_phonenumber.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // 1. Kiểm tra các trường
        if (username.isEmpty() || full_name.isEmpty() || phone_number.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi Đăng ký", "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        // 2. Kiểm tra mật khẩu khớp
        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Lỗi Đăng ký", "Mật khẩu xác nhận không khớp!");
            return;
        }

        // 3. Gọi DAO để thêm người dùng
        // Format FUllNAME
        boolean success = userDAO.addUser(username,full_name, phone_number, password, "user");

        // 4. Kiểm tra kết quả từ CSDL
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đăng ký tài khoản thành công!");
            App.setRoot("welcome.fxml");
        } else {
            // Lỗi này có thể xảy ra nếu CSDL bị lỗi, hoặc tên 'username' đã tồn tại
            // (vì chúng ta đã đặt 'username' là UNIQUE trong CSDL)
            showAlert(Alert.AlertType.ERROR, "Lỗi Đăng ký", "Tên đăng nhập này đã tồn tại!");
        }
    }


    @FXML
    private void handleBackToLogin() throws IOException {
        App.setRoot("welcome.fxml");
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Không có tiêu đề phụ
        alert.setContentText(message);
        alert.showAndWait();
    }
}

