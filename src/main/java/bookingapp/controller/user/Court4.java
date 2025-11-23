package bookingapp.controller.user;

import bookingapp.dao.BookingDAO;
import bookingapp.dao.LoadStatusDAO;
import bookingapp.dao.Price_pHourDAO;
import bookingapp.model.Booking;
import bookingapp.model.LoadStatus;
import bookingapp.model.Price_pHour;
import bookingapp.model.User;
import bookingapp.util.Session;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Court4 {
    @FXML private Label lb_header;
    @FXML private DatePicker fld_date;
    @FXML private Button h_1;
    @FXML private Button h_2;
    @FXML private Button h_3;
    @FXML private Button h_4;
    @FXML private Button h_5;
    @FXML private Button h_6;
    @FXML private Button h_7;
    @FXML private Button h_8;
    @FXML private Button h_9;
    @FXML private Button h_10;
    @FXML private Button h_11;
    @FXML private Button h_12;
    @FXML private Button h_13;
    @FXML private Button h_14;
    @FXML private Button h_15;
    @FXML private Button h_16;
    @FXML private Button h_17;
    @FXML private Button h_18;
    @FXML private Button h_19;
    @FXML private Button h_20;
    @FXML private Button h_21;
    @FXML private Button h_22;
    @FXML private Button h_23;
    @FXML private Button h_24;
    @FXML private Button bt_confirm;
    @FXML private Label total;
    private double Total;
    private String picked_date;
    private LoadStatusDAO LoadDAO = new LoadStatusDAO();
    private BookingDAO Bookingdao = new BookingDAO();
    private User user;
    private Map<Integer, LoadStatus> LichNgoaiLe;
    private Map<Integer, LoadStatus> LichDat;
    private Map<Integer, Boolean> picked;
    private Price_pHourDAO pricePHourDAO = new Price_pHourDAO();

    private Button hourToButton(int h) {
        switch (h) {
            case 0: return h_1;
            case 1: return h_2;
            case 2: return h_3;
            case 3: return h_4;
            case 4: return h_5;
            case 5: return h_6;
            case 6: return h_7;
            case 7: return h_8;
            case 8: return h_9;
            case 9: return h_10;
            case 10: return h_11;
            case 11: return h_12;
            case 12: return h_13;
            case 13: return h_14;
            case 14: return h_15;
            case 15: return h_16;
            case 16: return h_17;
            case 17: return h_18;
            case 18: return h_19;
            case 19: return h_20;
            case 20: return h_21;
            case 21: return h_22;
            case 22: return h_23;
            case 23: return h_24;
            default: return null;
        }
    }

    private void loadStatus(){
        Total = 0;
        total.setVisible(false);
        for(int h = 0; h < 24; h++){
            Button btn = hourToButton(h);
            btn.setStyle("-fx-background-color: #FFFFFF;");
        }
        if(LoadDAO.isLook(4)){
            for(int h = 0; h < 24; h++){
                Button btn = hourToButton(h);
                btn.setDisable(true);
                btn.setStyle("-fx-background-color: #A8A8A8;");

            }
            return;
        }
        // Load LichDatNgoaiLe
        for(int h = 0; h < 24; h++){
            Button btn = hourToButton(h);
            if(LichNgoaiLe.containsKey(h)){
                btn.setDisable(true);
                btn.setStyle("-fx-background-color: #A8A8A8;");
            }
            else{
                btn.setDisable(false);
            }
        }

        //LoadLichDat
        for(int h = 0; h < 24; h++){
            if(LichNgoaiLe.containsKey(h)) continue;
            Button btn = hourToButton(h);

            if(LichDat.containsKey(h)){
                btn.setDisable(true);
                btn.setStyle("-fx-background-color: #FF6D6D;");
            }
            else{
                btn.setDisable(false);
            }
        }
    }

    private double getPriceSafely(int hour) {
        if (fld_date.getValue() == null) return 0;

        int day_in_week = fld_date.getValue().getDayOfWeek().getValue() + 1;

        Price_pHour priceData = pricePHourDAO.getPrice(day_in_week, hour);

        if (priceData == null) {
            return 0; // Trả về 0 nếu không tìm thấy giá
        }
        return priceData.getPrice();
    }


    private void handle_btn(int h){
        Button btn = hourToButton(h);
        btn.setOnAction(e->{
            if(!picked.containsKey(h)){
                picked.put(h, true);
                btn.setStyle("-fx-background-color: #50fff9;");
                total.setVisible(true);
                Total += getPriceSafely(h);
                String text = String.format("Tổng tiền: %.0fVND", Total);
                total.setText(text);
            }
            else{
                picked.remove(h);
                Total -= getPriceSafely(h);
                if(Total == 0) total.setVisible(false);
                else{
                    String text = String.format("Tổng tiền: %.0fVND", Total);
                    total.setText(text);
                }
                btn.setStyle("-fx-background-color:white;");
            }
        });
    }


    @FXML
    private void initialize(){
        user = Session.getCurrentUser();
        Total = 0;
        total.setVisible(false);
        lb_header.setText("Đặt Lịch Sân 4");
        fld_date.getEditor().setDisable(true);
        fld_date.setValue(LocalDate.now());
        fld_date.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                LocalDate today = LocalDate.now();
                LocalDate maxDay = today.plusDays(7);
                if (date.isBefore(today) || date.isAfter(maxDay)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #eee;");
                }
            }
        });
        picked_date = fld_date.getValue().toString();
        fld_date.setOnAction(e->{
            fld_date.show();
            picked_date = fld_date.getValue().toString();
            LichNgoaiLe = LoadDAO.loadAllLichNgoaiLe(picked_date, 4);
            LichDat = LoadDAO.loadLichDat(picked_date, 4);
            loadStatus();
        });
        LichNgoaiLe = LoadDAO.loadAllLichNgoaiLe(picked_date, 4);
        LichDat = LoadDAO.loadLichDat(picked_date, 4);
        picked = new HashMap<>();
        loadStatus();
        for(int h = 0; h < 24; h++){
            handle_btn(h);
        }
        bt_confirm.setOnAction(e->{
            handleConfirm();
        });
    }
    private void handleConfirm(){
        for(Integer i : picked.keySet()){
            System.out.println(i);
        }
        double res_total = 0;
        for(int h = 0; h < 24; h++){
            if(picked.containsKey(h)){
                res_total = getPriceSafely(h);
                String start = String.format("%02d:00", h);
                int e = h + 1;
                while(e < 24 && picked.containsKey(e)){
                    res_total += getPriceSafely(h);
                    e++;
                }
                String end = String.format("%02d:00", e);
                if(e == 24){
                    end = "23:59";
                }
                boolean Add = Bookingdao.add(new Booking(user.getId(), 4, LocalDate.parse(picked_date), LocalTime.parse(start), LocalTime.parse(end), res_total));
                if(Add){
                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đặt sân thành công!");
                }
                else{
                    showAlert(Alert.AlertType.ERROR, "Thất bại", "Đặt sân không thành công!");
                }
                for (int i = h; i < e; i++) {
                    picked.remove(i);
                    Button btn = hourToButton(i);
                    if (btn != null) {
                        btn.setStyle("-fx-background-color: white;");
                    }
                }
                h = e - 1;
            }
        }
        picked_date = fld_date.getValue().toString();
        LichNgoaiLe = LoadDAO.loadAllLichNgoaiLe(picked_date, 4);
        LichDat = LoadDAO.loadLichDat(picked_date, 4);
        loadStatus();

    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Không có tiêu đề phụ
        alert.setContentText(message);
        alert.showAndWait();
    }
}


