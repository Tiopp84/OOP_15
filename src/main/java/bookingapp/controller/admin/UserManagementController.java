package bookingapp.controller.admin;

import bookingapp.dao.UserDAO;
import bookingapp.model.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class UserManagementController {

    @FXML private TableView<User> tableUsers;
    @FXML private TableColumn<User, Integer> colId;
    @FXML private TableColumn<User, String> colUsername;
    @FXML private TableColumn<User, String> colRole;

    private UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        colUsername.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUsername()));
        colRole.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRole()));

        loadUsers();
    }

    private void loadUsers() {
        ObservableList<User> list = FXCollections.observableArrayList();

        try (var conn = bookingapp.db.DatabaseManager.getConnection();
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery("SELECT * FROM users")) {

            while (rs.next()) {
                list.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("password"),
                        rs.getString("role")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        tableUsers.setItems(list);
    }

    // -----------------------------------------------------
    // THÊM NGƯỜI DÙNG
    // -----------------------------------------------------
    @FXML
    private void handleAddUser() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Thêm người dùng mới");
        dialog.setHeaderText("Nhập theo dạng: username,password,role");
        dialog.setContentText("Thông tin:");

        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty()) return;

        try {
            String[] parts = result.get().split(",");
            if (parts.length != 5) {
                showAlert("Lỗi", "Định dạng không hợp lệ!", Alert.AlertType.ERROR);
                return;
            }

            boolean success = userDAO.addUser(parts[0], parts[1], parts[2], parts[3], parts[4]);

            if (success) {
                showAlert("Thành công", "Thêm người dùng thành công!", Alert.AlertType.INFORMATION);
                loadUsers();
            } else {
                showAlert("Thất bại", "Không thể thêm người dùng (có thể username trùng).", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -----------------------------------------------------
    // XÓA NGƯỜI DÙNG
    // -----------------------------------------------------
    @FXML
    private void handleDeleteUser() {
        User selected = tableUsers.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Lỗi", "Hãy chọn 1 user!", Alert.AlertType.WARNING);
            return;
        }

        boolean success = userDAO.deleteUser(selected.getUsername());

        if (success) {
            showAlert("Thành công", "Đã xóa user.", Alert.AlertType.INFORMATION);
            loadUsers();
        } else {
            showAlert("Lỗi", "Không thể xóa user.", Alert.AlertType.ERROR);
        }
    }

    // -----------------------------------------------------
    // ĐỔI MẬT KHẨU
    // -----------------------------------------------------
    @FXML
    private void handleChangePassword() {
        User selected = tableUsers.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Lỗi", "Hãy chọn 1 user!", Alert.AlertType.WARNING);
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Đổi mật khẩu");
        dialog.setHeaderText("Đổi mật khẩu cho user: " + selected.getUsername());
        dialog.setContentText("Mật khẩu mới:");

        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty()) return;

        boolean success = userDAO.changePassword(selected.getUsername(), result.get());

        if (success) {
            showAlert("Thành công", "Đổi mật khẩu thành công!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Lỗi", "Không thể đổi mật khẩu.", Alert.AlertType.ERROR);
        }
    }

    // -----------------------------------------------------
    // HÀM TIỆN ÍCH
    // -----------------------------------------------------
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
