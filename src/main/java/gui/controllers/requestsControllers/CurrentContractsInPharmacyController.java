package gui.controllers.requestsControllers;

import dao.requestDTO.BayerInCityDto;
import dao.requests.BayerInCityRequest;
import dao.requests.CurrentContractsInPharmacyRequest;
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
import java.util.List;

public class CurrentContractsInPharmacyController {
    @FXML
    private TextField idField;

    @FXML
    private Button button;

    @FXML
    private TableView<Contract> tableView;

    @FXML
    private TableColumn<Contract, Long> idColumn;

    @FXML
    private TableColumn<Contract, Long> pharmacyIdColumn;

    @FXML
    private TableColumn<Contract, Long> workerIdColumn;

    @FXML
    private TableColumn<Contract, LocalDate> dateColumn;

    @FXML
    private TableColumn<Contract, BigDecimal> wageColumn;

    @FXML
    private void initialize() {
        // Настройка ограничения на ввод только цифр
        idField.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) { // Только цифры
                return change;
            }
            return null; // Блокируем ввод
        }));

        // Настройка кнопки: активна только при наличии текста в textField, кроме одних пробелов, их удаляет trim()
        button.setDisable(true);
        idField.textProperty().addListener((_obs, _oldVal, newVal) -> {
            button.setDisable(newVal.trim().isEmpty());
        });

        // настройка столбцов
        configColumns();

        // чтобы столбцы занимали всю длину таблицы
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void configColumns() {
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
    }

    @FXML
    void onAction(ActionEvent event) {
        tableView.getItems().clear();
        Long pharmacyId = Long.parseLong(idField.getText());
        List<Contract> contracts = CurrentContractsInPharmacyRequest.getCurrentContracts(pharmacyId);
        if (contracts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Не найдено");
            alert.setContentText("Нет аптеки с таким id или в ней нет действующих контрактов");
            alert.showAndWait();
        }
        tableView.getItems().addAll(contracts);
    }
}
