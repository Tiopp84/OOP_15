package bookingapp.controller.user;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainCourt {
    @FXML private Button bt_court1;
    @FXML private Button bt_court2;
    @FXML private Button bt_court3;
    @FXML private Button bt_court4;

    @FXML
    private void initialize(){
        bt_court1.setOnAction(e -> {
            UserMainController.getMainController().loadContent("court1.fxml");
        });
        bt_court2.setOnAction(e -> {
            UserMainController.getMainController().loadContent("court2.fxml");
        });
        bt_court3.setOnAction(e -> {
            UserMainController.getMainController().loadContent("court3.fxml");

        });
        bt_court4.setOnAction(e -> {
            UserMainController.getMainController().loadContent("court4.fxml");

        });

    }
}
