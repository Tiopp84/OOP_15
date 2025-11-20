package bookingapp.controller.admin;

import bookingapp.dao.PriceTableDAO;
import bookingapp.model.PriceTable;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalTime;
import java.util.Optional;

public class PriceManagementController {

    @FXML private TableView<PriceTable> table;
    @FXML private TableColumn<PriceTable, Integer> colId;
    @FXML private TableColumn<PriceTable, String> colDay;
    @FXML private TableColumn<PriceTable, String> colStart;
    @FXML private TableColumn<PriceTable, String> colEnd;
    @FXML private TableColumn<PriceTable, Float> colPrice;

    private PriceTableDAO dao = new PriceTableDAO();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(p -> new javafx.beans.property.SimpleIntegerProperty(p.getValue().getId()).asObject());
        colDay.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getDay_in_week()));
        colStart.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getStart_time().toString()));
        colEnd.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getEnd_time().toString()));
        colPrice.setCellValueFactory(p -> new javafx.beans.property.SimpleFloatProperty(p.getValue().getPrice()).asObject());

        loadData();
    }

    private void loadData() {
        table.setItems(FXCollections.observableArrayList(dao.getAll()));
    }

    @FXML
    private void handleAdd() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Thêm bảng giá");
        dialog.setHeaderText("Nhập: Ngày,Bắt đầu,Kết thúc,Giá");
        dialog.setContentText("VD: Monday,08:00,10:00,100000");

        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty()) return;

        String[] parts = result.get().split(",");
        if (parts.length != 4) {
            showAlert("Bạn phải nhập dạng: Day,HH:mm,HH:mm,Price", Alert.AlertType.ERROR);
            return;
        }

        try {
            boolean ok = dao.add(
                    parts[0].trim(),
                    LocalTime.parse(parts[1].trim()),
                    LocalTime.parse(parts[2].trim()),
                    Float.parseFloat(parts[3].trim())
            );

            if (ok) {
                showAlert("Thêm thành công!", Alert.AlertType.INFORMATION);
                loadData();
            } else {
                showAlert("Không thể thêm!", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            showAlert("Sai định dạng thời gian hoặc giá!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleDelete() {
        PriceTable selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Hãy chọn một dòng để xóa!", Alert.AlertType.WARNING);
            return;
        }

        if (dao.delete(selected.getId())) {
            showAlert("Xóa thành công!", Alert.AlertType.INFORMATION);
            loadData();
        } else {
            showAlert("Không thể xóa!", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String msg, Alert.AlertType type) {
        Alert a = new Alert(type);
        a.setContentText(msg);
        a.show();
    }
}
