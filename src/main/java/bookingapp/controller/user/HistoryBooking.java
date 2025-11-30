package bookingapp.controller.user;

import bookingapp.dao.BookingDAO;
import bookingapp.model.Booking;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class HistoryBooking {
    @FXML
    private TableView<Booking> tableHistory;
    @FXML
    private TableColumn<Booking, String> colTenSan;
    @FXML
    private TableColumn<Booking, String> colNgay;
    @FXML
    private TableColumn<Booking, String> colThoiGianBatDau;
    @FXML
    private TableColumn<Booking, String> colThoiGianKetThuc;
    @FXML
    private TableColumn<Booking, String> colGiaLucDat;

    @FXML
    public void initialize() {
        setupTable();
        loadBooking();
    }

    private void setupTable() {

        // Căn giữa các cột
        colTenSan.setStyle("-fx-alignment: CENTER;");
        colNgay.setStyle("-fx-alignment: CENTER;");
        colThoiGianBatDau.setStyle("-fx-alignment: CENTER;");
        colThoiGianKetThuc.setStyle("-fx-alignment: CENTER;");
        colGiaLucDat.setStyle("-fx-alignment: CENTER;");

        // Tên sân
        colTenSan.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getCourtName() == null ? "" : data.getValue().getCourtName()));

        // Ngày đặt (LocalDate → String)
        colNgay.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getBookingDate() == null ? "" : data.getValue().getBookingDate()));

        // Thời gian bắt đầu
        colThoiGianBatDau.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getStartTime() == null ? "" : data.getValue().getStartTime()));

        // Thời gian kết thúc
        colThoiGianKetThuc.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getEndTime() == null ? "" : data.getValue().getEndTime()));

        // Giá lúc đặt (Double)
        colGiaLucDat.setCellValueFactory(
                data -> new SimpleStringProperty(String.format("%.0f VND", data.getValue().getTotalPrice())));
    }

    private void loadBooking() {
        BookingDAO booking = new BookingDAO();
        List<Booking> bookingList = booking.loadBooking();
        tableHistory.getItems().clear();
        tableHistory.getItems().addAll(bookingList);
    }
}
