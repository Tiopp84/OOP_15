package bookingapp.controller.user;

import bookingapp.dao.PriceTableDAO;
import bookingapp.dao.UserDAO;
import bookingapp.model.PriceTable;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PriceDetail {
    @FXML
    private TableView<PriceTable> priceDetailTable;

    @FXML
    private TableColumn<PriceTable, String> colDayStart;

    @FXML
    private TableColumn<PriceTable, String> colDayEnd;
    @FXML
    private TableColumn<PriceTable, String> colStartTime;

    @FXML
    private TableColumn<PriceTable, String> colEndTime;
    @FXML
    private TableColumn<PriceTable, String> colPrice;
    @FXML
    public void initialize(){
        setPriceTableStartWeek();
        loadTableStartWeek();
    }
    private void setPriceTableStartWeek() {
        colDayStart.setStyle("-fx-alignment: CENTER;");
        colDayEnd.setStyle("-fx-alignment: CENTER;");
        colStartTime.setStyle("-fx-alignment: CENTER;");
        colEndTime.setStyle("-fx-alignment: CENTER;");
        colPrice.setStyle("-fx-alignment: CENTER;");


        colDayStart.setCellValueFactory(data ->{
            int dayValue = data.getValue().getStart_day_in_week();
            String day_in_week;
            if(dayValue != 8){
                day_in_week = String.format("T%d", dayValue);
            }
            else day_in_week = "CN";
            return new SimpleStringProperty(day_in_week);
        });

        colDayEnd.setCellValueFactory(data ->{
            int dayValue = data.getValue().getEnd_day_in_week();
            String day_in_week;
            if(dayValue != 8){
                day_in_week = String.format("T%d", dayValue);
            }
            else day_in_week = "CN";
            return new SimpleStringProperty(day_in_week);
        });

        colStartTime.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStart_time().toString())
        );

        colEndTime.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getEnd_time().toString())
        );

        colPrice.setCellValueFactory(data ->
                new SimpleStringProperty(String.format("%.0f VND", data.getValue().getPrice()))
        );
    }
    private void loadTableStartWeek(){
        PriceTableDAO priceTableDAO = new PriceTableDAO();
        List<PriceTable> result=priceTableDAO.getPrice();
        ObservableList<PriceTable> list = FXCollections.observableArrayList(result);
        priceDetailTable.setItems(list);
    }
}
