package gui.controllers.requestsControllers;

import dao.requests.AggregationRequest;
import entities.*;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class AggregationController {
    @FXML
    private Label avgChequeLabel;

    @FXML
    private Label avgTimeLabel;

    @FXML
    private ComboBox<String> cityComboBox;

    @FXML
    private ComboBox<Manufacturer> manufacturerComboBox;

    @FXML
    private TextField pharmacyIdField;

    @FXML
    private Label summLabel;

    @FXML
    private Button button;

    @FXML
    private void initialize() {
        // ограничение на ввод pharmacyId: только цифры
        pharmacyIdField.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) { // Только цифры
                return change;
            }
            return null; // Блокируем ввод
        }));

        // Настройка кнопки: активна только при наличии текста в обоих textFiled, кроме одних пробелов
        button.setDisable(true);
        ChangeListener<String> listener = (_obs, _old, _new) -> {
            button.setDisable(pharmacyIdField.getText().isEmpty());
        };
        pharmacyIdField.textProperty().addListener(listener);

        // заполнение названиями городов
        cityComboBox.getItems().addAll(getAllCities());
        // заполнение производителей
        manufacturerComboBox.getItems().addAll(getAllManufacturers());
    }

    // при выборе города
    @FXML
    private void changeCity(ActionEvent event) {
        System.out.println("Выбран город: " + cityComboBox.getValue());
        BigDecimal avgCheque = AggregationRequest.avgCheque(cityComboBox.getValue());
        avgChequeLabel.setText(String.valueOf(avgCheque.setScale(2, RoundingMode.HALF_UP)));
    }

    // при выборе производителя
    @FXML
    private void changeManufacturer(ActionEvent event) {
        System.out.println("Выбран производитель: " + manufacturerComboBox.getValue());
        Double avg = AggregationRequest.avgTimeOfManufacturer(manufacturerComboBox.getValue().getId());
        avgTimeLabel.setText(String.format("%.2f", avg));
    }

    // при нажатии кнопки
    @FXML
    private void inputPharmacyId(ActionEvent event) {
        System.out.printf("Введён id = %s", pharmacyIdField.getText());
        Long pharmacyId = Long.parseLong(pharmacyIdField.getText());
        summLabel.setText(String.valueOf(AggregationRequest.revenueForDietarySupplements(pharmacyId)));
    }

    private List<String> getAllCities() {
        var factory = new Configuration()
                .addAnnotatedClass(Cheque.class)
                .addAnnotatedClass(Position.class)
                .addAnnotatedClass(Medicine.class)
                .addAnnotatedClass(Bayer.class)
                .addAnnotatedClass(Pharmacy.class)
                .buildSessionFactory();
        String hql = """
                SELECT ph.address
                FROM Pharmacy ph
                """;
        try (Session session = factory.openSession()){
            List<String> addresses = session.createQuery(hql, String.class).getResultList();
            List<String> cityNames = new ArrayList<>();
            for (String address : addresses) {
                int start = address.indexOf("г.") + 3;
                int end = start + address.substring(start).indexOf(" ") - 1;
                String cityName = address.substring(start, end);
                if (!cityNames.contains(cityName)) {
                    cityNames.add(cityName);
                }
            }
            System.out.println("Найдены города: " +  cityNames);
            return cityNames;
        }
    }

    private List<Manufacturer> getAllManufacturers() {
        var factory = new Configuration()
                .addAnnotatedClass(Manufacturer.class)
                .buildSessionFactory();
        String hql = """
                SELECT m
                FROM Manufacturer m
                """;
        try (Session session = factory.openSession()){
            List<Manufacturer> manufacturers = session.createQuery(hql, Manufacturer.class).getResultList();
            List<String> manufacturersStrings = new ArrayList<>();
            for (Manufacturer manufacturer : manufacturers) {
                manufacturersStrings.add(String.format("%s (id = %s)", manufacturer.getName(), manufacturer.getId()));
            }
            System.out.println("Найдены производители: " +  manufacturersStrings);
            return manufacturers;
        }
    }
}
