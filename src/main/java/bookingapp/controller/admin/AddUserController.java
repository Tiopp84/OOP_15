package bookingapp.controller.admin;

import bookingapp.dao.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddUserController {

    @FXML private TextField txtUsername;
    @FXML private TextField txtFullName;
    @FXML private TextField txtPhone;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox<String> cbRole;

    private UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        cbRole.getItems().addAll("admin", "user");
    }

    @FXML
    private void handleSubmit() {
        String username = txtUsername.getText().trim();
        String fullName = txtFullName.getText().trim();
        String phone = txtPhone.getText().trim();
        String password = txtPassword.getText();
        String role = cbRole.getValue();

        if (username.isEmpty() || fullName.isEmpty() || phone.isEmpty() || password.isEmpty() || role == null) {
            showError("Vui lòng nhập đầy đủ thông tin.");
            return;
        }

        boolean ok = userDAO.addUser(username, fullName, phone, password, role);
        if (!ok) {
            showError("Không thể thêm user (username có thể trùng hoặc lỗi DB).");
            return;
        }

        // nếu thành công -> đóng cửa sổ (không hiện alert theo yêu cầu)
        Stage stage = (Stage) txtUsername.getScene().getWindow();
        stage.close();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
