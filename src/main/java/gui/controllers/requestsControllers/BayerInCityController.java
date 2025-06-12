package gui.controllers.requestsControllers;

import dao.requestDTO.BayerInCityDto;
import dao.requests.BayerInCityRequest;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class BayerInCityController {
    @FXML
    private TextField cityNameField;

    @FXML
    private TextField idField;
    
    @FXML
    private Button button;

    @FXML
    private TableView<BayerInCityDto> tableView;

    @FXML
    private TableColumn< BayerInCityDto, Long> idColumn;

    @FXML
    private TableColumn< BayerInCityDto, Long> bayerIdColumn;

    @FXML
    private TableColumn< BayerInCityDto, Long> pharmacyIdColumn;

    @FXML
    private TableColumn< BayerInCityDto, LocalDate> dateColumn;

    @FXML
    private TableColumn< BayerInCityDto, BigDecimal> sumPriceColumn;

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

        // Настройка кнопки: активна только при наличии текста в обоих textFiled, кроме одних пробелов, их удаляет trim()
        button.setDisable(true);
        ChangeListener<String> listener = (_obs, _old, _new) -> {
            button.setDisable(idField.getText().trim().isEmpty() || cityNameField.getText().trim().isEmpty());
        };
        idField.textProperty().addListener(listener);
        cityNameField.textProperty().addListener(listener);

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
        Long bayerId = Long.parseLong(idField.getText());
        String cityName = cityNameField.getText();
        List<BayerInCityDto> purchases = BayerInCityRequest.getPurchases(bayerId, cityName);
        if (purchases.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Не найдено");
            alert.setContentText("Нет покупок с таким параметрами");
            alert.showAndWait();
        }
        tableView.getItems().addAll(purchases);
    }

}
