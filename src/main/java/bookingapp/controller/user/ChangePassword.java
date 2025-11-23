package bookingapp.controller.user;

import bookingapp.dao.UserDAO;
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

import java.io.IOException;

public class ChangePassword {
//    @FXML
//    private TextField oldPassword;
    @FXML
    private TextField newPassword;
    @FXML
    private TextField newConfirmPassword;

    @FXML
    public void handleChangePassword(ActionEvent event){
        Integer user_id = Session.getCurrentUser().getId();
//        String oldPass = oldPassword.getText();
        String newPass = newPassword.getText();
        String newConfirmPass = newConfirmPassword.getText();
        if(!newPass.equals(newConfirmPass)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Xác nhận mật khẩu không chính xác với mật khẩu mới");
            alert.showAndWait();
            return;
        }
        UserDAO dao=new UserDAO();
        boolean success=dao.changePassword2(user_id, newPass);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        if (success) {
            alert.setContentText("Đổi mật khẩu thành công");
        } else {
            alert.setContentText("Đổi mật khẩu thất bại.");
        }
        alert.showAndWait();
    }
}
