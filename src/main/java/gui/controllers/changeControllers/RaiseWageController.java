package gui.controllers.changeControllers;

import dao.ContractDao;
import dao.TerminationDao;
import entities.Contract;
import entities.Pharmacy;
import entities.Worker;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RaiseWageController {
    @FXML
    private Label countContracts;

    @FXML
    private Spinner<Integer> yearSpinner;

    @FXML
    private Spinner<Integer> percentSpinner;

    @FXML
    private Button button;

    @FXML
    private TableView<Contract> aldContractsTable;

    @FXML
    private TableColumn<Contract, Long> idColumn;

    @FXML
    private TableColumn<Contract, Long> workerIdColumn;

    @FXML
    private TableColumn<Contract, Long> pharmacyIdColumn;

    @FXML
    private TableColumn<Contract, BigDecimal> wageColumn;

    @FXML
    private TableColumn<Contract, LocalDate> dateColumn;

    @FXML
    private TableView<Contract> newContractsTable;

    @FXML
    private TableColumn<Contract, Long> newIdColumn;

    @FXML
    private TableColumn<Contract, Long> newPharmacyIdColumn;

    @FXML
    private TableColumn<Contract, Long> newWorkerIdColumn;

    @FXML
    private TableColumn<Contract, BigDecimal> newWageColumn;

    @FXML
    private TableColumn<Contract, LocalDate> newDateColumn;

    private ContractDao contractDao = new ContractDao();

    private TerminationDao terminationDao = new TerminationDao();


    @FXML
    private void initialize() {
        System.out.println(yearSpinner.getValue());
        System.out.println(percentSpinner.getValue());

        // настройка значений spinner'ов
        SpinnerValueFactory.IntegerSpinnerValueFactory yearFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 80, 1, 1);
        yearSpinner.setValueFactory(yearFactory);
        yearFactory.setAmountToStepBy(1);


        SpinnerValueFactory.IntegerSpinnerValueFactory percentFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 300, 1, 1);
        percentSpinner.setValueFactory(percentFactory);

        // запрет ручного ввода
        yearSpinner.setEditable(false);
        percentSpinner.setEditable(false);

        // настройка действий Spinner'ов
        yearSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            System.out.println("Новое значение year: " + newValue);
            countContracts.setText(contractDao.oldContract(newValue).size() + " таких контрактов");
        });

        // настройка таблицы
        configureColumns();
    }


    //добавляем расторжения и создаем новые контракты
    @FXML
    void onAction(ActionEvent event) {
        // контракты, которые следует расторгнуть
        List<Contract> aldContracts = contractDao.oldContract(yearSpinner.getValue());
        // расторжение
        terminationDao.addTerminations(contractDao.oldContract(yearSpinner.getValue()));

        //создание новых контрактов, с повышенной зарплатой
        List<Contract> newContracts = new ArrayList<>();
        for (Contract aldContract : aldContracts) {
            BigDecimal newWage = aldContract.getWage().multiply(BigDecimal.valueOf(1.0 + percentSpinner.getValue()/100.0));
            Contract newContract = new Contract();
            newContract.setWorker(aldContract.getWorker());
            newContract.setPharmacy(aldContract.getPharmacy());
            newContract.setDate(LocalDate.now());
            newContract.setWage(newWage);
            newContracts.add(newContract);
        }
        // добавление контрактов
        contractDao.addContracts(newContracts);

        aldContractsTable.getItems().clear();
        aldContractsTable.getItems().addAll(aldContracts);
        newContractsTable.getItems().clear();
        newContractsTable.getItems().addAll(newContracts);
    }

    private void configureColumns() {
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

        newIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        newWorkerIdColumn.setCellValueFactory(cellData -> {
            Worker worker = cellData.getValue().getWorker();
            return new SimpleObjectProperty<>(worker.getId());
        });
        newPharmacyIdColumn.setCellValueFactory(cellData -> {
            Pharmacy pharmacy = cellData.getValue().getPharmacy();
            return new SimpleObjectProperty<>(pharmacy.getId());
        });
        newDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        newWageColumn.setCellValueFactory(new PropertyValueFactory<>("wage"));
    }

}