package bookingapp.controller.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class AdminMainController {

    @FXML
    private ChoiceBox<String> choiceFunctions;

    @FXML
    private BorderPane contentArea;

    @FXML
    public void initialize() {
        choiceFunctions.getItems().addAll(
                "Quản lý người dùng",
                "Quản lý sân",
                "Quản lý bảng giá",
                "Quản lý lịch đặt",
                "Quản lý lịch ngoại lệ"
        );
    }

    @FXML
    private void handleFunctionSelect() {
        String selected = choiceFunctions.getValue();

        if (selected == null) return;

        switch (selected) {
            case "Quản lý người dùng":
                loadView("/bookingapp/view/admin/userManagement.fxml");
                break;
            // các chức năng khác bạn load tương tự

            case "Quản lý sân":
                loadView("/bookingapp/view/admin/courtManagement.fxml");
                break;

        }
    }

    private void loadView(String fxmlPath) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentArea.setCenter(view); // load vào vùng trung tâm
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
