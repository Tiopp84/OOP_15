package bookingapp.controller.user;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class Court4 {
    @FXML private Label lb_header;
    @FXML private DatePicker fld_date;

    @FXML
    private void initialize(){
        lb_header.setText("Đặt Lịch Sân 4");
        fld_date.setOnAction(e->{
            fld_date.show();
        });
    }
}
