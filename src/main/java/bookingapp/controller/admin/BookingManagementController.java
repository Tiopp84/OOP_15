package bookingapp.controller.admin;

import bookingapp.dao.BookingDAO;
import bookingapp.model.Booking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class BookingManagementController {

    @FXML private TableView<Booking> table;

    @FXML private TableColumn<Booking, Integer> colId;
    @FXML private TableColumn<Booking, Integer> colUser;
    @FXML private TableColumn<Booking, Integer> colCourt;
    @FXML private TableColumn<Booking, String> colDate;
    @FXML private TableColumn<Booking, String> colStart;
    @FXML private TableColumn<Booking, String> colEnd;
    @FXML private TableColumn<Booking, Double> colPrice;

    private final BookingDAO dao = new BookingDAO();
    private final ObservableList<Booking> list = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getId()));
        colUser.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getUserId()));
        colCourt.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getCourtId()));
        colDate.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getBookingDate()));
        colStart.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getStartTime()));
        colEnd.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEndTime()));
        colPrice.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getTotalPrice()));

        loadData();
    }

    @FXML
    public void loadData() {
        list.setAll(dao.getAll());
        table.setItems(list);
    }

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
