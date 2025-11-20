package bookingapp.controller.admin;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class AdminMainController {

    @FXML
    private ChoiceBox<String> choiceFunctions;

    @FXML
    public void initialize() {
        // Thêm 5 chức năng vào ChoiceBox khi mở màn hình
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
        // Mới chỉ test xem đã chọn được hay chưa
        String selected = choiceFunctions.getValue();

        if (selected != null) {
            System.out.println("Đã chọn chức năng: " + selected);
        }
    }
}
