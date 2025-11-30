package bookingapp.controller.user;

import bookingapp.dao.UserDAO;
import bookingapp.model.User;
import bookingapp.util.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class UpdateInfo {
    @FXML
    private TextField txtFullname;
    @FXML
    private TextField txtPhone;

    private Info parentInfoController;  // Tham chiếu tới controller cha

    // Phương thức này sẽ được gọi từ Info controller trước khi mở cửa sổ
    public void setParentController(Info parentInfoController) {
        this.parentInfoController = parentInfoController;
    }
    @FXML
    private void onSave() {
        User currentUser = Session.getCurrentUser();
        String fullname = txtFullname.getText();
        String phone = txtPhone.getText();
        UserDAO userDAO = new UserDAO();
        boolean update=userDAO.updateUser(currentUser.getId(),fullname,phone);
        if(update==true){
            currentUser.setFull_name(fullname);
            currentUser.setPhone_number(phone);
            Session.setCurrentUser(currentUser);
            showAlert("Thông báo","Thay đổi thông tin tài khoản thành công");
        }
        else {
            showAlert("Thông báo","Thay đổi thông tin tài khoản không thành công");
        }
    }
    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
