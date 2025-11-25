package bookingapp.controller.admin;

import bookingapp.dao.BookingDAO;
import bookingapp.model.Booking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.text.NumberFormat;
import java.time.LocalDate;
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

    @FXML private DatePicker dpSearchDate;
    @FXML private TextField txtSearchUser;
    @FXML private TextField txtSearchCourt;

    private final BookingDAO dao = new BookingDAO();
    private final ObservableList<Booking> list = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
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

        // Format tiền VND
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        colPrice.setCellFactory(column -> new TableCell<Booking, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setText(null);
                } else {
                    setText(vndFormat.format(value));
                }
            }
        });

        loadData();
    }

    // ====== Load danh sách ======
    @FXML
    public void loadData() {
        list.setAll(dao.getAll());
        table.setItems(list);

        // Reset form tìm kiếm
        dpSearchDate.setValue(null);
        txtSearchUser.clear();
        txtSearchCourt.clear();
    }

    // ====== Tìm kiếm theo ngày, user, sân ======
    @FXML
    public void handleSearch() {

        ObservableList<Booking> filtered = FXCollections.observableArrayList();

        String userText = txtSearchUser.getText().trim();
        String courtText = txtSearchCourt.getText().trim();

        Integer userId = null;
        Integer courtId = null;

        try {
            if (!userText.isEmpty()) userId = Integer.parseInt(userText);
            if (!courtText.isEmpty()) courtId = Integer.parseInt(courtText);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "User ID hoặc Court ID phải là số").show();
            return;
        }

        LocalDate date = dpSearchDate.getValue();

        for (Booking b : list) {
            boolean match = true;

            if (userId != null && b.getUserId() != userId)
                match = false;

            if (courtId != null && b.getCourtId() != courtId)
                match = false;

            if (date != null && !b.getBookingDate().equals(date.toString()))
                match = false;

            if (match) filtered.add(b);
        }

        table.setItems(filtered);
    }

    // ====== Xóa lịch ======
    @FXML
    public void handleDelete() {
        Booking selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Vui lòng chọn lịch để xóa").show();
            return;
        }

        if (dao.delete(selected.getId())) {
            loadData();
            new Alert(Alert.AlertType.INFORMATION, "Đã xóa lịch đặt").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Không thể xóa lịch").show();
        }
    }
}
