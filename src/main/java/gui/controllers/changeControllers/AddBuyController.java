package gui.controllers.changeControllers;

import dao.*;
import dao.requests.BayerInCityRequest;
import dao.requests.QuantityMedicineInPharmacy;
import entities.Bayer;
import entities.Cheque;
import entities.Medicine;
import entities.Position;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddBuyController {

    @FXML
    private Button addMedicineButton;

    @FXML
    private TextField bayerIdField;

    @FXML
    private Button buyButton;

    @FXML
    private ComboBox<Medicine> medicineComboBox;

    @FXML
    private TextField pharmacyIdField;

    @FXML
    private ListView<Product> productListView;


    @FXML
    private void initialize() {
        // ограничение на ввод только цифр
        bayerIdField.setTextFormatter(makeOnlyDigitFormater());
        pharmacyIdField.setTextFormatter(makeOnlyDigitFormater());

        //кнопка добавить доступна только если введена аптека
        //кнопка купить доступна только если есть аптека и покупатель
        addMedicineButton.setDisable(true);
        buyButton.setDisable(true);
        ChangeListener<String> listener = (_obs, _old, _new) -> {
            addMedicineButton.setDisable(pharmacyIdField.getText().trim().isEmpty());
        };
        ChangeListener<String> listenerFroBuy = (_obs, _old, _new) -> {
            buyButton.setDisable(bayerIdField.getText().isEmpty());
        };
        pharmacyIdField.textProperty().addListener(listener);
        bayerIdField.textProperty().addListener(listenerFroBuy);

        // добавление лекарств
        medicineComboBox.getItems().addAll(new MedicineDao().getAllMedicines());


        // Очистка списка при изменении аптеки
        pharmacyIdField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!Objects.equals(oldVal, newVal)) {
                productListView.getItems().clear();
            }
        });

        // настройка отображения элементов в ListView
        productListView.setCellFactory(lv -> new ListCell<Product>() {
            @Override
            protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    // Создаем HBox для размещения названия лекарства и Spinner
                    HBox hbox = new HBox(10);
                    hbox.setAlignment(Pos.CENTER_LEFT);

                    // Название лекарства
                    Label nameLabel = new Label(item.getMedicine().getName());

                    // Spinner
                    Spinner<Integer> spinner = item.getSpinner();
                    spinner.setEditable(false);

                    hbox.getChildren().addAll(nameLabel, spinner);
                    setGraphic(hbox);
                }
            }
        });
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


    @FXML
    void addMedicine(ActionEvent event) {
        if (medicineComboBox.getValue() != null) {
            // есть ли уже в корзине
            boolean isInBasket = productListView.getItems().stream()
                    .anyMatch(product -> product.getMedicine().getId() == medicineComboBox.getValue().getId());

            // есть ли в этой аптеки (и есть ли такая аптека)
            long quantityInThePharmacy = QuantityMedicineInPharmacy.
                    getQuantity(Long.parseLong(pharmacyIdField.getText()), medicineComboBox.getValue().getId());
            if (quantityInThePharmacy == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Не найдено");
                alert.setContentText(String.format("В этой аптеке нет такого лекарства или нет аптеки с id = %s", pharmacyIdField.getText()));
                alert.showAndWait();
                return;
            }

            if (!isInBasket) {
                int maxCountMedicine;
                if (quantityInThePharmacy < 10) {
                    maxCountMedicine = (int) quantityInThePharmacy;
                } else {
                    maxCountMedicine = 10;
                }
                Product product = new Product(medicineComboBox.getValue(), new Spinner<Integer>(1, maxCountMedicine, 1, 1));
                productListView.getItems().add(product);
                System.out.println("Добавлен " + product + ". Есть в аптеке: " + quantityInThePharmacy);
            } else {
                System.out.println("Товар уже есть в корзине");
            }

        } else {
            System.out.println("Товар не выбран");
        }
    }

    @FXML
    private void buy(ActionEvent event) {
        // если есть товары в корзине и есть bayer с введенным id
        long bayerId = Long.parseLong(bayerIdField.getText());
        long pharmacyId = Long.parseLong(pharmacyIdField.getText());
        if (!productListView.getItems().isEmpty() && BayerDao.isThereBayer(bayerId)) {
            // создание чека
            Cheque cheque = new Cheque();
            cheque.setBayer(BayerDao.getBayer(bayerId));
            cheque.setPharmacy(PharmacyDao.getPharmacy(pharmacyId));
            cheque.setDate(LocalDate.now());

            List<Position> positions = new ArrayList<>();
            for (Product product : productListView.getItems()) {
                Position position = new Position();
                position.setCheque(cheque);
                position.setMedicine(product.getMedicine());
                position.setQuantity(Short.valueOf(String.valueOf(product.getSpinner().getValue())));
                positions.add(position);
                System.out.println("Эта позиция будет загружена: " + position);
            }
            if (ChequeDao.saveChequeWithPositions(cheque, positions)) {
                showAlert("Успех", "Покупка успешно добавлена в БД");
                System.out.println("Чек и позиции добавлен в БД, ID чека: " + cheque.getId());
                productListView.getItems().clear();
            } else {
                showAlert("Ошибка", "Произошла шибка при загрузке в БД");
                System.out.println("Чек или позиции не загрузились в БД");
            }
        } else {
            if (productListView.getItems().isEmpty()) {
                showAlert("Корзина", "Корзина пуста, добавьте товары");
                System.out.println("Корзина пустая");
            } else {
                showAlert("Ошибка", String.format("Нет покупателя с id = %s", bayerId));
            }
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}


@AllArgsConstructor
@Getter
class Product {
    private Medicine medicine;
    private Spinner<Integer> spinner;

    @Override
    public String toString() {
        return String.format("%s [%s] %s", medicine.getName(), medicine.getShape(), spinner.getValue());
    }
}
