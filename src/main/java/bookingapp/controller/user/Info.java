package bookingapp.controller.user;

import bookingapp.dao.UserDAO;
import bookingapp.model.User;
import bookingapp.util.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class Info {
    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    public void initialize(){
        loadinfo();
    }
    private void loadinfo(){
        User user= Session.getCurrentUser();
        txtUsername.setText(user.getUsername());
        txtPassword.setText(user.getPassword());
    }
    @FXML
    public void changePass(ActionEvent event){
        String newUsername = txtUsername.getText();
        String newPassword = txtPassword.getText();
        if(newUsername.isEmpty() || newPassword.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo"); // Tiêu đề cửa sổ
            alert.setHeaderText(null);   // Không có header
            alert.setContentText("Vui lòng nhập đầy đủ tài khoản và mật khẩu"); // Nội dung thông báo
            alert.showAndWait(); // Hiển thị cửa sổ và chờ người dùng đóng
        }
        UserDAO dao=new UserDAO();
        boolean success=dao.changePassword(newUsername, newPassword);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        if (success) {
            alert.setContentText("Đổi thông tin thành công");
        } else {
            alert.setContentText("Đổi thông tin thất bại. Vui lòng kiểm tra lại username(Không được thay đỏi Username .");
        }
        alert.showAndWait();
    }
}
