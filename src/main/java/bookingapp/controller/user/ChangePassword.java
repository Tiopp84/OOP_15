package bookingapp.controller.user;

import bookingapp.dao.UserDAO;
import bookingapp.model.User;
import bookingapp.util.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.io.IOException;

public class ChangePassword {
    // @FXML
    // private TextField oldPassword;
    @FXML
    private TextField newPassword;
    @FXML
    private TextField newConfirmPassword;

    private Info parentInfoController; // Tham chiếu tới controller cha

    // Phương thức này sẽ được gọi từ Info controller trước khi mở cửa sổ
    public void setParentController(Info parent) {
        this.parentInfoController = parent;
    }

    @FXML
    public void handleChangePassword(ActionEvent event) {
        User currentUser = Session.getCurrentUser();
        Integer user_id = Session.getCurrentUser().getId();
        // String oldPass = oldPassword.getText();
        String newPass = newPassword.getText();
        String newConfirmPass = newConfirmPassword.getText();
        if (!newPass.equals(newConfirmPass)) {
            showAlert("Lỗi", "Mật khẩu xác nhận không khớp!");
            return;
        }

        if (newConfirmPass.isEmpty() || newConfirmPass.length() < 2) {
            showAlert("Lỗi", "Mật khẩu mới phải ít nhất 2 ký tự!");
            return;
        }
        UserDAO dao = new UserDAO();
        boolean success = dao.changePassword2(user_id, newPass);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        if (success) {
            // dổi luôn thông tin khi thoái khỏi cửa sổ dổi mật khẩu
            currentUser.setPassword(newPass);
            Session.setCurrentUser(currentUser);
            if (parentInfoController != null) {
                parentInfoController.loadinfo(); // Cập nhật ngay txtPassword
            }
            alert.setContentText("Đổi mật khẩu thành công");
            Stage stage = (Stage) newPassword.getScene().getWindow();
            stage.close();
        } else {
            alert.setContentText("Đổi mật khẩu thất bại.");
        }
        alert.showAndWait();
    }

    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
