package bookingapp.controller.user;

import bookingapp.dao.BookingDAO;
import bookingapp.model.Booking;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static bookingapp.util.showAlert.showAlert;

public class HistoryBooking {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    // FXML tìm kiếm sân
    @FXML
    private Button btnToggleSearch;
    @FXML
    private TextField txtTenSan;
    @FXML
    private DatePicker dpDate;
    @FXML
    private VBox searchPanel;
    // FXML load lịch sử
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
    // FXML load nut xoa
    @FXML
    private TableColumn<Booking, Void> colThaoTac;

    @FXML
    public void initialize() {
        String namesan = txtTenSan.getText();
        dpDate.getEditor().setDisable(true);
        LocalDate selectedDate = dpDate.getValue();
        setupDeleteColumn();
        setupTable();
        loadBooking(namesan, selectedDate);
    }

    // tìm kiếm
    @FXML
    private void onToggleSearchPanel() {
        boolean visible = searchPanel.isVisible();
        searchPanel.setVisible(!visible);
        searchPanel.setManaged(!visible); // quan trọng: không chiếm không gian khi ẩn
    }

    @FXML
    private void onResetSearch() {
        txtTenSan.clear();
        dpDate.setValue(null);

        // Load lại toàn bộ dữ liệu
        initialize();
    }

    @FXML
    private void onSearch() {

        // TODO: Viết logic lọc dữ liệu theo các trường tìm kiếm
        // Ví dụ: load lại dữ liệu với điều kiện lọc
        initialize();

        // Có thể ẩn panel sau khi tìm (tùy ý)
        // searchPanel.setVisible(false);
        // searchPanel.setManaged(false);
    }

    // xóa các lịch đặt trong tương lai
    private void setupDeleteColumn() {
        colThaoTac.setCellValueFactory(param -> null);
        colThaoTac.setSortable(false);

        Callback<TableColumn<Booking, Void>, TableCell<Booking, Void>> cellFactory = param -> new TableCell<>() {
            private final Button deleteButton = new Button("Xóa");

            {
                deleteButton.getStyleClass().add("delete-button");
                deleteButton.setFocusTraversable(false);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                    return;
                }

                Booking booking = getTableRow().getItem();
                boolean canDelete = isBookingInFuture(booking);

                // Thay đổi style class theo trạng thái
                deleteButton.getStyleClass().removeAll("delete-button", "delete-button-disabled");
                if (canDelete) {
                    deleteButton.getStyleClass().add("delete-button");
                    deleteButton.setDisable(false);
                    deleteButton.setOnAction(e -> confirmAndDelete(booking));
                } else {
                    deleteButton.getStyleClass().add("delete-button-disabled");
                    deleteButton.setDisable(true);
                    deleteButton.setOnAction(null); // bỏ sự kiện
                }

                setGraphic(deleteButton);
                setAlignment(Pos.CENTER);
            }
        };

        colThaoTac.setCellFactory(cellFactory);
    }

    private boolean isBookingInFuture(Booking booking) {
        try {
            LocalDate date = LocalDate.parse(booking.getBookingDate(), DATE_FORMAT);
            LocalTime startTime = LocalTime.parse(booking.getStartTime(), TIME_FORMAT);
            LocalDateTime bookingDateTime = LocalDateTime.of(date, startTime);
            return LocalDateTime.now().isBefore(bookingDateTime);
        } catch (Exception e) {
            return false;
        }
    }

    private void confirmAndDelete(Booking booking) {
        // Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        // alert.setTitle("Xác nhận hủy đặt sân");
        // alert.setHeaderText("Bạn có chắc chắn muốn hủy lịch đặt này?");
        // alert.setContentText(
        // booking.getCourtName() + "\n" +
        // booking.getBookingDate() + " từ " +
        // booking.getStartTime() + " đến " + booking.getEndTime()
        // );
        // if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
        BookingDAO dao = new BookingDAO();
        boolean success = dao.delete(booking.getId()); // Giả sử có method này và booking có getId()

        if (success) {
            tableHistory.getItems().remove(booking);
            showAlert("Thành công", "Đã hủy đặt sân thành công!");
        } else {
            showAlert("Lỗi", "Không thể hủy đặt sân. Vui lòng thử lại.");
        }
        // }
    }

    // load thông tin sân trừ nút xóa
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

    private void loadBooking(String name, LocalDate selectedDate) {
        BookingDAO booking = new BookingDAO();
        List<Booking> bookingList = booking.getBooking(name, selectedDate);
        tableHistory.getItems().clear();
        tableHistory.getItems().addAll(bookingList);
    }
}
