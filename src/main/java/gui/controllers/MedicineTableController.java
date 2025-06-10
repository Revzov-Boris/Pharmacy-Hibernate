package gui.controllers;

import dao.MedicineDao;
import entities.Medicine;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.util.List;

public class MedicineTableController {

    @FXML
    private TableView<Medicine> tableView;
    @FXML
    private TableColumn<Medicine, Long> idColumn;
    @FXML
    private TableColumn<Medicine, String> nameColumn;
    @FXML
    private TableColumn<Medicine, Boolean> isPrescriptionColumn;
    @FXML
    private TableColumn<Medicine, Boolean> isSupplementsColumn;
    @FXML
    private TableColumn<Medicine, Boolean> shapeColumn;
    @FXML
    private TableColumn<Medicine, BigDecimal> priceColumn;

    private MedicineDao medicineDao = new MedicineDao();

    @FXML
    public void initialize() {
        // Настройка колонок
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        isPrescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("prescription")); // prescription, а не isPrescription
        isSupplementsColumn.setCellValueFactory(new PropertyValueFactory<>("dietarySupplements"));
        shapeColumn.setCellValueFactory(new PropertyValueFactory<>("shape"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        System.out.println("[DEBUG] Загрузка данных...");
        List<Medicine> medicines = medicineDao.getAllMedicines();
        System.out.println("[DEBUG] Загружено записей: " + medicines.size());
        tableView.getItems().setAll(medicines);
        // чтобы столбцы занимали всю длину таблицы
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Загрузка данных
        loadData();
    }

    public void loadData() {
        tableView.getItems().setAll(medicineDao.getAllMedicines());
    }

}
