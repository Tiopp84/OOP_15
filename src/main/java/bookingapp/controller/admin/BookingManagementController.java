package bookingapp.controller.admin;

import bookingapp.dao.BookingDAO;
import bookingapp.model.Booking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class BookingManagementController {

    @FXML private TableView<Booking> table;

    @FXML private TableColumn<Booking, Integer> colId;
    @FXML private TableColumn<Booking, Integer> colUser;
    @FXML private TableColumn<Booking, Integer> colCourt;
    @FXML private TableColumn<Booking, String> colDate;
    @FXML private TableColumn<Booking, String> colStart;
    @FXML private TableColumn<Booking, String> colEnd;
    @FXML private TableColumn<Booking, Double> colPrice;

    @FXML private DatePicker datePicker;

    private final BookingDAO dao = new BookingDAO();
    private final ObservableList<Booking> list = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        // ===== Set các cột =====
        colId.setCellValueFactory(data ->
                new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getId()));
        colUser.setCellValueFactory(data ->
                new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getUserId()));
        colCourt.setCellValueFactory(data ->
                new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getCourtId()));
        colDate.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getBookingDate()));
        colStart.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getStartTime()));
        colEnd.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getEndTime()));
        colPrice.setCellValueFactory(data ->
                new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getTotalPrice()));

        // ===== Format VND =====
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        colPrice.setCellFactory(column -> new TableCell<Booking, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setText(null);
                } else {
                    setText(vndFormat.format(value)); // VD: 120.000 ₫
                }
            }
        });

        // ===== Load dữ liệu ban đầu =====
        loadData();
    }

    // ===== Load dữ liệu từ database =====
    @FXML
    public void loadData() {
        list.setAll(dao.getAll());
        table.setItems(list);
    }

    // ===== Tìm kiếm theo ngày =====
    @FXML
    public void handleSearch() {
        if (datePicker.getValue() == null) {
            table.setItems(list); // nếu chưa chọn ngày → hiện tất cả
            return;
        }

        ObservableList<Booking> filtered = FXCollections.observableArrayList();
        String selectedDate = datePicker.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE);

        for (Booking b : list) {
            if (b.getBookingDate().equals(selectedDate)) {
                filtered.add(b);
            }
        }

        table.setItems(filtered);
    }

    // ===== Làm mới =====
    @FXML
    public void handleRefresh() {
        datePicker.setValue(null); // xóa ngày
        loadData();                // load lại dữ liệu từ database
    }

    // ===== Xóa lịch =====
    @FXML
    public void handleDelete() {
        Booking selected = table.getSelectionModel().getSelectedItem();

        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Vui lòng chọn lịch để xóa").show();
            return;
        }

        if (dao.delete(selected.getId())) {
            handleRefresh();
            new Alert(Alert.AlertType.INFORMATION, "Đã xóa lịch đặt").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Không thể xóa lịch").show();
        }
    }
}
