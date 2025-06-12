package gui.controllers.requestsControllers;

import dao.requests.BigOrdersRequest;
import entities.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class BigOrdersController {
    @FXML
    private TextField minPriceField;

    @FXML
    private TextField manufacturerIdField;

    @FXML
    private Button button;

    @FXML
    private TableView<Order> tableView;

    @FXML
    private TableColumn<Order, LocalDate> dateColumn;

    @FXML
    private TableColumn<Order, Long> idColumn;

    @FXML
    private TableColumn<Order, Long> workerIdColumn;

    @FXML
    private TableColumn<Order, Long> medicineIdColumn;

    @FXML
    private TableColumn<Order, Long> pharmacyIdColumn;

    @FXML
    private TableColumn<Order, BigDecimal> priceForOneColumn;

    @FXML
    private TableColumn<Order, Short> quantityColumn;

    @FXML
    private void initialize() {
        // Настройка ограничения на ввод только цифр
        manufacturerIdField.setTextFormatter(makeOnlyDigitFormater());
        minPriceField.setTextFormatter(makeOnlyDigitFormater());

        // Настройка кнопки: активна только при наличии текста в обоих textFiled, кроме одних пробелов, их удаляет trim()
        button.setDisable(true);
        ChangeListener<String> listener = (_obs, _old, _new) -> {
            button.setDisable(manufacturerIdField.getText().trim().isEmpty() || minPriceField.getText().trim().isEmpty());
        };
        manufacturerIdField.textProperty().addListener(listener);
        minPriceField.textProperty().addListener(listener);

        // Настройка колонок таблицы
        configureColumns();

        // чтобы столбцы занимали всю длину таблицы
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private TextFormatter<String> makeOnlyDigitFormater() {
        return new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) { // Только цифры
                return change;
            }
            return null; // Блокируем ввод
        });
    }

    private void configureColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        medicineIdColumn.setCellValueFactory(cellData -> {
            Medicine medicine = cellData.getValue().getMedicine();
            return new SimpleObjectProperty<>(medicine.getId());
        });
        workerIdColumn.setCellValueFactory(cellData -> {
            Worker worker = cellData.getValue().getWorker();
            return new SimpleObjectProperty<>(worker.getId());
        });
        pharmacyIdColumn.setCellValueFactory(cellData -> {
            Pharmacy pharmacy = cellData.getValue().getPharmacy();
            return new SimpleObjectProperty<>(pharmacy.getId());
        });

        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceForOneColumn.setCellValueFactory(new PropertyValueFactory<>("priceForOne"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    @FXML
    void onAction(ActionEvent event) {
        tableView.getItems().clear();
        Long manufacturerId = Long.parseLong(manufacturerIdField.getText());
        BigDecimal minPrice = BigDecimal.valueOf(Long.parseLong(minPriceField.getText()));
        List<Order> orders = BigOrdersRequest.getOrders(manufacturerId, minPrice);
        if (orders.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Не найдено");
            alert.setContentText("Нет производителя с таким id или у него нет таких дорогих закупок");
            alert.showAndWait();
        }
        tableView.getItems().addAll(orders);
    }
}
