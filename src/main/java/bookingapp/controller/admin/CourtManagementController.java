package bookingapp.controller.admin;

import bookingapp.dao.CourtDAO;
import bookingapp.model.Court;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class CourtManagementController {

    @FXML private TableView<Court> tableCourts;
    @FXML private TableColumn<Court, Integer> colId;
    @FXML private TableColumn<Court, String> colName;
    @FXML private TableColumn<Court, String> colStatus;

    private CourtDAO courtDAO = new CourtDAO();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        colName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        colStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));

        loadCourts();
    }

    private void loadCourts() {
        tableCourts.setItems(FXCollections.observableArrayList(courtDAO.getAllCourts()));
    }

    // --------------------------------------------------
    // Thêm sân
    // --------------------------------------------------
    @FXML
    public void handleAddCourt() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Thêm sân");
        dialog.setHeaderText("Nhập theo dạng: Tên sân,Trạng thái");
        dialog.setContentText("Thông tin:");

        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty()) return;

        String[] parts = result.get().split(",");
        if (parts.length != 2) {
            alert("Lỗi", "Sai định dạng! Hãy nhập: Tên sân,Trạng thái", Alert.AlertType.ERROR);
            return;
        }

        String name = parts[0].trim();
        String status = parts[1].trim();

        System.out.println("DEBUG: Thêm sân -> name='" + name + "' status='" + status + "'");

        boolean ok = courtDAO.addCourt(name, status);

        if (ok) {
            alert("Thành công", "Đã thêm sân!", Alert.AlertType.INFORMATION);
            loadCourts();
        } else {
            alert("Lỗi", "Không thể thêm sân! Kiểm tra log trên console.", Alert.AlertType.ERROR);
        }
    }

    // --------------------------------------------------
    // Xóa sân
    // --------------------------------------------------
    @FXML
    public void handleDeleteCourt() {
        Court selected = tableCourts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            alert("Lỗi", "Hãy chọn 1 sân!", Alert.AlertType.WARNING);
            return;
        }

        if (courtDAO.deleteCourt(selected.getId())) {
            alert("Thành công", "Đã xóa sân!", Alert.AlertType.INFORMATION);
            loadCourts();
        } else {
            alert("Lỗi", "Không thể xóa sân!", Alert.AlertType.ERROR);
        }
    }

    // --------------------------------------------------
    // Đổi trạng thái sân
    // --------------------------------------------------
    @FXML
    public void handleChangeStatus() {
        Court selected = tableCourts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            alert("Lỗi", "Hãy chọn sân!", Alert.AlertType.WARNING);
            return;
        }

        TextInputDialog dialog = new TextInputDialog(selected.getStatus());
        dialog.setTitle("Đổi trạng thái sân");
        dialog.setHeaderText("Sân: " + selected.getName());
        dialog.setContentText("Trạng thái mới:");

        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty()) return;

        boolean ok = courtDAO.updateStatus(selected.getId(), result.get());

        if (ok) {
            alert("Thành công", "Đã đổi trạng thái!", Alert.AlertType.INFORMATION);
            loadCourts();
        } else {
            alert("Lỗi", "Không thể đổi trạng thái!", Alert.AlertType.ERROR);
        }
    }

    // --------------------------------------------------
    private void alert(String title, String msg, Alert.AlertType type) {
        Alert al = new Alert(type);
        al.setTitle(title);
        al.setHeaderText(null);
        al.setContentText(msg);
        al.showAndWait();
    }
}
