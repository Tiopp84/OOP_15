package bookingapp.controller;

import bookingapp.App;
import bookingapp.model.User;
import bookingapp.util.Session; // Import Session
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label; // Import Label

import java.io.IOException;

/**
 * Controller cho mainWindow.fxml.
 * ĐÃ CẬP NHẬT để sử dụng Session.
 */
public class MainWindowController {

    @FXML
    private Label welcomeLabel; // Label để chào người dùng (fx:id="welcomeLabel")
    @FXML
    private Button logoutButton;

    /**
     * Hàm này được JavaFX tự động gọi
     * sau khi file FXML được tải.
     */
    @FXML
    public void initialize() {
        // Lấy người dùng hiện tại từ Session
        User currentUser = Session.getCurrentUser();

        // Hiển thị lời chào
        if (currentUser != null) {
            welcomeLabel.setText("Chào mừng, " + currentUser.getUsername() + "!");
        } else {
            // Trường hợp này không nên xảy ra nếu logic đúng
            welcomeLabel.setText("Chào mừng!");
        }
    }


    /**
     * Xử lý khi nhấn nút "Đăng xuất".
     * Sẽ xóa Session và quay về màn hình welcome.
     */
    @FXML
    private void handleLogout() throws IOException {
        // Xóa người dùng khỏi Session
        Session.setCurrentUser(null);

        // Quay về màn hình đăng nhập
        App.setRoot("welcome.fxml");
    }
}

