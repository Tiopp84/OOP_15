package bookingapp.controller.admin;

import bookingapp.dao.UserDAO;
import bookingapp.model.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UserManagementController {

    @FXML private TextField txtSearch;
    @FXML private TableView<User> tableUsers;
    @FXML private TableColumn<User, Integer> colId;
    @FXML private TableColumn<User, String> colUsername;
    @FXML private TableColumn<User, String> colFullName;
    @FXML private TableColumn<User, String> colPhone;
    @FXML private TableColumn<User, String> colRole;

    private UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        // map columns
        colId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        colUsername.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUsername()));
        colFullName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFull_name()));
        colPhone.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPhone_number()));
        colRole.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRole()));

        loadUsers();

        // optional: when user types into search field, live filter
        txtSearch.textProperty().addListener((obs, oldVal, newVal) -> handleSearch());
    }

    // load dữ liệu từ DAO
    private void loadUsers() {
        ObservableList<User> list = userDAO.getAllUsers();
        tableUsers.setItems(list);
    }

    // tìm kiếm bằng DAO
    @FXML
    private void handleSearch() {
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()) {
            loadUsers();
        } else {
            ObservableList<User> list = userDAO.searchUsers(keyword);
            tableUsers.setItems(list);
        }
    }

    // mở form thêm (FXML phải đặt trong resource path hợp lý)
    @FXML
    private void handleAddUser() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bookingapp/view/admin/addUser.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Thêm người dùng");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // reload danh sách sau khi đóng form
            loadUsers();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể mở form thêm người dùng: " + e.getMessage());
        }
    }

    @FXML
    private void handleDeleteUser() {
        User selected = tableUsers.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Lỗi", "Hãy chọn 1 user!");
            return;
        }

        // Confirm
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Xác nhận");
        confirm.setHeaderText(null);
        confirm.setContentText("Bạn có chắc muốn xóa user: " + selected.getUsername() + " ?");
        if (confirm.showAndWait().filter(btn -> btn == ButtonType.OK).isPresent()) {
            boolean success = userDAO.deleteUser(selected.getUsername());
            if (!success) {
                showAlert("Lỗi", "Không thể xóa user. Có thể do ràng buộc ở DB.");
            } else {
                // không hiện alert khi thành công
                loadUsers();
            }
        }
    }

    @FXML
    private void handleChangePassword() {
        User selected = tableUsers.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Lỗi", "Hãy chọn 1 user!");
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Đổi mật khẩu");
        dialog.setHeaderText("Đổi mật khẩu cho user: " + selected.getUsername());
        dialog.setContentText("Mật khẩu mới:");

        dialog.showAndWait().ifPresent(newPass -> {
            boolean success = userDAO.changePassword(selected.getUsername(), newPass);
            if (!success) {
                showAlert("Lỗi", "Không thể đổi mật khẩu. Vui lòng thử lại.");
            } else {
                // không hiện thông báo khi thành công
            }
        });
    }

    // chỉ hiện alert khi lỗi / cần báo
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
