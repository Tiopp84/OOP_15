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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Info {
    @FXML private Label lblUsername;
    @FXML private Label lblFullname;
    @FXML private Label lblNumberPhone;
    @FXML
    public void initialize(){
        loadinfo();
    }
    public void loadinfo(){
        User user= Session.getCurrentUser();
        lblUsername.setText(user.getUsername());
        lblFullname.setText(user.getFull_name());
        lblNumberPhone.setText(user.getPhone_number());
    }
    @FXML
    private void changePass(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/bookingapp/view/user/ChangePassword.fxml"));
            Parent root = fxmlLoader.load();
            // Lấy controller của cửa sổ đổi mật khẩu
            ChangePassword changePassCtrl = fxmlLoader.getController();

            // Truyền chính controller này (Info) vào để nó gọi lại khi thành công
            changePassCtrl.setParentController(this);
            Stage stage = new Stage();
            stage.setTitle("Đổi mật khẩu");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // cửa sổ modal, không cho thao tác với cửa sổ trước
            stage.showAndWait(); // showAndWait nếu muốn chờ người dùng đóng
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void updateInfo(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/bookingapp/view/user/UpdateInfo.fxml"));
            Parent root=fxmlLoader.load();
            UpdateInfo updateInfoCtrl = fxmlLoader.getController();
            updateInfoCtrl.setParentController(this);
            Stage stage = new Stage();
            stage.setTitle("Thay đổi thông tin người dùng");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // cửa sổ modal, không cho thao tác với cửa sổ trước
            stage.showAndWait();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
