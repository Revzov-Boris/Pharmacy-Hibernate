package gui.controllers;

import dao.ContractDao;
import entities.Contract;
import entities.Pharmacy;
import entities.Worker;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ContractTableController {
    @FXML
    private TableView<Contract> tableView;

    @FXML
    private TableColumn<Contract, Long> idColumn;

    @FXML
    private TableColumn<Contract, Long> workerIdColumn;
    
    @FXML
    private TableColumn<Contract, Long> pharmacyIdColumn;
    
    @FXML
    private TableColumn<Contract, LocalDate> dateColumn;
    
    @FXML
    private TableColumn<Contract, BigDecimal> wageColumn;

    private ContractDao contractDao = new ContractDao();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        workerIdColumn.setCellValueFactory(cellData -> {
            Worker worker = cellData.getValue().getWorker();
            return new SimpleObjectProperty<>(worker.getId());
        });
        pharmacyIdColumn.setCellValueFactory(cellData -> {
            Pharmacy pharmacy = cellData.getValue().getPharmacy();
            return new SimpleObjectProperty<>(pharmacy.getId());
        });
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        wageColumn.setCellValueFactory(new PropertyValueFactory<>("wage"));

        System.out.println("[DEBUG] Загрузка данных...");
        List<Contract> contracts = contractDao.getAllContracts();
        System.out.println("[DEBUG] Загружено записей: " + contracts.size());
        tableView.getItems().setAll(contracts);
        // чтобы столбцы занимали всю длину таблицы
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Загрузка данных
        loadData();
    }

    public void loadData() {
        tableView.getItems().setAll(contractDao.getAllContracts());
    }

    
}
