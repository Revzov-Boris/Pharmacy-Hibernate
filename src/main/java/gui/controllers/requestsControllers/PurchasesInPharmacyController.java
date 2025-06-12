package gui.controllers.requestsControllers;

import dao.requestDTO.PurchasesInPharmacyDto;

import dao.requests.PurchasesInPharmacyRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PurchasesInPharmacyController {
    @FXML
    private TextField textField;

    @FXML
    private Button button;

    @FXML
    private TableView<PurchasesInPharmacyDto> tableView;

    @FXML
    private TableColumn<PurchasesInPharmacyDto, Long> idColumn;

    @FXML
    private TableColumn<PurchasesInPharmacyDto, Long> pharmacyIdColumn;

    @FXML
    private TableColumn<PurchasesInPharmacyDto, Long> bayerIdColumn;

    @FXML
    private TableColumn<PurchasesInPharmacyDto, LocalDate> dateColumn;

    @FXML
    private TableColumn<PurchasesInPharmacyDto, BigDecimal> sumPriceColumn;

    @FXML
    private void initialize() {
        // ограничения на ввод: только цифры
        textField.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) { // Только цифры
                return change;
            }
            return null; // Блокируем ввод
        }));

        // Настройка кнопки: активна только при наличии текста в textField, кроме одних пробелов, их удаляет trim()
        button.setDisable(true);
        textField.textProperty().addListener((_obs, _oldVal, newVal) -> {
            button.setDisable(newVal.trim().isEmpty());
        });

        // Настройка колонок таблицы
        configureColumns();

        // чтобы столбцы занимали всю длину таблицы
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void configureColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        bayerIdColumn.setCellValueFactory(new PropertyValueFactory<>("bayerId"));
        pharmacyIdColumn.setCellValueFactory(new PropertyValueFactory<>("pharmacyId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        sumPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sumPrice"));
    }

    @FXML
    void onAction(ActionEvent event) {
        tableView.getItems().clear();
        Long pharmacyId = Long.parseLong(textField.getText());
        List<PurchasesInPharmacyDto> purchases = PurchasesInPharmacyRequest.getPurchases(pharmacyId);
        if (purchases.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Не найдено");
            alert.setContentText("Нет аптеки с таким id или в этой аптеки еще нет покупок");
            alert.showAndWait();
        }
        tableView.getItems().addAll(purchases);
    }
}
