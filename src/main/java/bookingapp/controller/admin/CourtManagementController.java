package bookingapp.controller.admin;

import bookingapp.dao.CourtDAO;
import bookingapp.model.Court;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class CourtManagementController {

    @FXML private TableView<Court> tableCourts;
    @FXML private TableColumn<Court, Integer> colId;
    @FXML private TableColumn<Court, String> colName;
    @FXML private TableColumn<Court, String> colStatus;
    @FXML private TableColumn<Court, Void> colActions;

    private CourtDAO courtDAO = new CourtDAO();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        colName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        colStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));

        addActionButtons();
        loadCourts();
    }

    private void loadCourts() {
        tableCourts.setItems(FXCollections.observableArrayList(courtDAO.getAllCourts()));
    }

    // ------------------- Tạo nút hành động cho mỗi hàng ---------------------
    private void addActionButtons() {
        colActions.setCellFactory(col -> new TableCell<>() {

            private final Button btnActive = new Button("Hoạt động");
            private final Button btnClose = new Button("Đóng");
            private final Button btnMaintain = new Button("Bảo trì");

            {
                // Giao diện đẹp hơn
                btnActive.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                btnClose.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");
                btnMaintain.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");

                btnActive.setOnAction(e -> updateStatus("HoatDong"));
                btnClose.setOnAction(e -> updateStatus("Dong"));
                btnMaintain.setOnAction(e -> updateStatus("BaoTri"));
            }

            private void updateStatus(String status) {
                Court court = getTableView().getItems().get(getIndex());
                boolean ok = courtDAO.updateStatus(court.getId(), status);

                if (ok) {
                    //alert("Thành công", "Đã cập nhật trạng thái sân!", Alert.AlertType.INFORMATION);
                    loadCourts();
                } else {
                    alert("Lỗi", "Không thể cập nhật trạng thái!", Alert.AlertType.ERROR);
                }
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox box = new HBox(5, btnActive, btnClose, btnMaintain);
                    setGraphic(box);
                }
            }
        });
    }

    private void alert(String title, String msg, Alert.AlertType type) {
        Alert al = new Alert(type);
        al.setTitle(title);
        al.setHeaderText(null);
        al.setContentText(msg);
        al.showAndWait();
    }
}
