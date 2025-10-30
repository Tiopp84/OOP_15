package bookingapp.controller;

import bookingapp.dao.BookingDAO;
import bookingapp.model.Booking;
import bookingapp.model.Court;
import bookingapp.model.User;
import bookingapp.util.Session;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Controller cho cửa sổ pop-up BookingDetailView.fxml.
 * Xử lý logic chọn ngày, giờ và xác nhận đặt sân.
 */
public class BookingDetailController {

    @FXML private Label courtNameLabel;
    @FXML private Label priceLabel;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<Integer> timeSlotCombo; // ComboBox cho các khung giờ (vd: 6, 7, 8...)
    @FXML private Label messageLabel;
    @FXML private Button confirmButton;
    @FXML private Button cancelButton;

    private Court selectedCourt;
    private Stage dialogStage;
    private BookingDAO bookingDAO;

    public BookingDetailController() {
        this.bookingDAO = new BookingDAO();
    }

    /**
     * Hàm này được gọi ngay sau khi FXML được tải.
     */
    @FXML
    private void initialize() {
        // Thêm các khung giờ (ví dụ: từ 6h sáng đến 21h tối)
        timeSlotCombo.setItems(FXCollections.observableArrayList(
                6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21
        ));

        // Đặt ngày mặc định là hôm nay
        datePicker.setValue(LocalDate.now());
    }

    /**
     * Hàm này được MainWindowController gọi để truyền dữ liệu (Sân và Stage) vào.
     * @param court Sân đã được chọn.
     * @param dialogStage Cửa sổ (Stage) của pop-up này.
     */
    public void setBookingData(Court court, Stage dialogStage) {
        this.selectedCourt = court;
        this.dialogStage = dialogStage;

        // Cập nhật giao diện với thông tin sân
        courtNameLabel.setText(court.getName());
        priceLabel.setText(String.format("Giá: %,.0f đ/giờ", court.getPricePerHour()));
    }

    /**
     * Xử lý khi nhấn nút "Xác nhận Đặt Sân".
     */
    @FXML
    private void handleConfirmBooking() {
        LocalDate date = datePicker.getValue();
        Integer startTime = timeSlotCombo.getValue();
        User currentUser = Session.getCurrentUser();

        // 1. Kiểm tra thông tin
        if (date == null || startTime == null) {
            showMessage("Vui lòng chọn ngày và giờ.", true);
            return;
        }
        if (date.isBefore(LocalDate.now())) {
            showMessage("Không thể đặt sân cho ngày trong quá khứ.", true);
            return;
        }
        if (currentUser == null) {
            showAlert(Alert.AlertType.ERROR, "Lỗi Phiên", "Không tìm thấy người dùng. Vui lòng đăng nhập lại.");
            return;
        }

        // 2. Kiểm tra xem sân có bị trùng lịch không
        if (bookingDAO.isCourtBooked(selectedCourt.getId(), date.toString(), startTime)) {
            showMessage("Khung giờ này đã bị đặt. Vui lòng chọn giờ khác.", true);
            return;
        }

        // 3. Tạo đối tượng Booking mới
        Booking newBooking = new Booking();
        newBooking.setUserId(currentUser.getId());
        newBooking.setCourtId(selectedCourt.getId());
        newBooking.setDate(date.toString());
        newBooking.setStartTime(startTime);
        newBooking.setEndTime(startTime + 1); // Giả sử mỗi lượt đặt là 1 giờ
        newBooking.setTotalPrice(selectedCourt.getPricePerHour());

        // 4. Thêm vào CSDL
        try {
            if (bookingDAO.addBooking(newBooking)) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đặt sân thành công!");
                dialogStage.close(); // Đóng cửa sổ pop-up
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi CSDL", "Không thể thêm lượt đặt sân.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi CSDL", "Lỗi SQL: " + e.getMessage());
        }
    }

    /**
     * Xử lý khi nhấn nút "Hủy".
     */
    @FXML
    private void handleCancel() {
        if (dialogStage != null) {
            dialogStage.close();
        }
    }

    // --- Hàm tiện ích ---
    private void showMessage(String message, boolean isError) {
        messageLabel.setText(message);
        messageLabel.setTextFill(isError ? Color.RED : Color.GREEN);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
