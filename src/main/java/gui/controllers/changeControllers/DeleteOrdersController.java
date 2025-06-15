package gui.controllers.changeControllers;

import dao.OrderDao;
import entities.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import javax.swing.event.ChangeListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DeleteOrdersController {
    @FXML
    private ComboBox<Manufacturer> manufacturerComboBox;

    @FXML
    private Button button;

    @FXML
    private TableView<Order> tableView;

    @FXML
    private Label quantityLabel;

    @FXML
    private TableColumn<Order, Long> idColumn;

    @FXML
    private TableColumn<Order, Long> medicineIdColumn;

    @FXML
    private TableColumn<Order, Long> pharmacyIdColumn;

    @FXML
    private TableColumn<Order, Long> workerIdColumn;

    @FXML
    private TableColumn<Order, LocalDate> dateColumn;

    @FXML
    private TableColumn<Order, BigDecimal> priceForOneColumn;

    @FXML
    private TableColumn<Order, Short> quantityColumn;

    private OrderDao orderDao = new OrderDao();

    @FXML
    private void initialize() {
        button.setDisable(true);
        button.disableProperty().bind(manufacturerComboBox.valueProperty().isNull());

        // настройка таблицы
        configureColumns();

        // заполнение производителями
        manufacturerComboBox.getItems().addAll(getAllManufacturers());
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
    void changeManufacturer(ActionEvent event) {
        int count = orderDao.getUnfulfilledOrders(manufacturerComboBox.getValue().getId()).size();
        quantityLabel.setText(String.valueOf(count));
    }

    // нажатие кнопки "Удалить"
    @FXML
    void onAction(ActionEvent event) {
        tableView.getItems().clear();
        List<Order> deletedOrders = orderDao.getUnfulfilledOrders(manufacturerComboBox.getValue().getId());
        if (orderDao.removeUnfulfilledOrders(manufacturerComboBox.getValue().getId())) {
            tableView.getItems().addAll(deletedOrders);
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