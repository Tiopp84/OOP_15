package bookingapp.controller.admin;

import bookingapp.dao.LichNgoaiLeDAO;
import bookingapp.model.LichNgoaiLe;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ExceptionScheduleController {

    @FXML private TextField txtMaSan;
    @FXML private TextField txtNgay;
    @FXML private TextField txtStart;
    @FXML private TextField txtEnd;
    @FXML private TextField txtLoai;
    @FXML private TextField txtNote;

    @FXML private TableView<LichNgoaiLe> table;
    @FXML private TableColumn<LichNgoaiLe, Integer> colId;
    @FXML private TableColumn<LichNgoaiLe, Integer> colMaSan;
    @FXML private TableColumn<LichNgoaiLe, String> colNgay;
    @FXML private TableColumn<LichNgoaiLe, String> colBatDau;
    @FXML private TableColumn<LichNgoaiLe, String> colKetThuc;
    @FXML private TableColumn<LichNgoaiLe, String> colLoai;
    @FXML private TableColumn<LichNgoaiLe, String> colGhiChu;

    private final LichNgoaiLeDAO dao = new LichNgoaiLeDAO();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getMaHoatDong()).asObject());
        colMaSan.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getMaSan()).asObject());
        colNgay.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNgay()));
        colBatDau.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getThoiGianBatDau()));
        colKetThuc.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getThoiGianKetThuc()));
        colLoai.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getLoaiHoatDong()));
        colGhiChu.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getGhiChu()));

        loadData();
    }

    private void loadData() {
        table.setItems(FXCollections.observableArrayList(dao.getAll()));
    }

    @FXML
    private void handleAdd() {
        LichNgoaiLe item = new LichNgoaiLe();
        item.setMaSan(Integer.parseInt(txtMaSan.getText()));
        item.setNgay(txtNgay.getText());
        item.setThoiGianBatDau(txtStart.getText());
        item.setThoiGianKetThuc(txtEnd.getText());
        item.setLoaiHoatDong(txtLoai.getText());
        item.setGhiChu(txtNote.getText());

        dao.insert(item);
        loadData();
    }

    @FXML
    private void handleDelete() {
        LichNgoaiLe selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            dao.delete(selected.getMaHoatDong());
            loadData();
        }
    }
}
