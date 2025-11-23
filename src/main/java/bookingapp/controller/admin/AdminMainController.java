package bookingapp.controller.admin;

import bookingapp.util.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class AdminMainController {

    @FXML
    private BorderPane contentArea;

    @FXML
    private Label Chaomung; // Label để hiển thị "Xin chào [Tên Admin]"

    @FXML
    public void initialize() {
        // Hiển thị dòng chào mừng dựa trên username từ session
        if (Session.getCurrentUser() != null) {
            Chaomung.setText("Xin chào " + Session.getCurrentUser().getUsername() + "!");
        } else {
            Chaomung.setText("Xin chào Admin!");
        }

        // Load trang mặc định: quản lý người dùng
        loadView("userManagement.fxml");
    }

    // ------------ HANDLE BUTTONS ----------------

    @FXML
    private void handleManageUsers() {
        loadView("userManagement.fxml");
    }

    @FXML
    private void handleManageCourts() {
        loadView("courtManagement.fxml");
    }

    @FXML
    private void handleManageBookings() {
        loadView("bookingManagement.fxml");
    }

    @FXML
    private void handleManagePrice() {
        loadView("priceManagement.fxml");
    }

    @FXML
    private void handleLogout() {
        System.out.println("Đăng xuất admin...");
        Session.clearSession();
        // TODO: chuyển về màn hình đăng nhập
    }

    // ------------ LOAD SUB UI ----------------

    private void loadView(String fileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bookingapp/view/admin/" + fileName));
            Parent view = loader.load(); // KHÔNG CAST
            contentArea.setCenter(view); // set vào BorderPane
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
