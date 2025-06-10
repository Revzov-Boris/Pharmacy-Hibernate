package gui.controllers;

import dao.OrderDao;
import entities.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class OrderTableController {
    @FXML
    private TableView<Order> tableView;

    @FXML
    private TableColumn<Order, Long> idColumn;

    @FXML
    private TableColumn<Order, Long> workerIdColumn;

    @FXML
    private TableColumn<Order, Long> manufacturerIdColumn;

    @FXML
    private TableColumn<Order, Long> medicineIdColumn;

    @FXML
    private TableColumn<Order, Long> pharmacyIdColumn;

    @FXML
    private TableColumn<Order, BigDecimal> priceForOneColumn;

    @FXML
    private TableColumn<Order, Short> quantityColumn;

    @FXML
    private TableColumn<Order, LocalDate> dateColumn;

    private OrderDao orderDao = new OrderDao();

    @FXML
    public void initialize() {
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
        manufacturerIdColumn.setCellValueFactory(cellData -> {
            Manufacturer manufacturer = cellData.getValue().getManufacturer();
            return new SimpleObjectProperty<>(manufacturer.getId());
        });
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceForOneColumn.setCellValueFactory(new PropertyValueFactory<>("priceForOne"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        System.out.println("[DEBUG] Загрузка данных...");
        List<Order> orders = orderDao.getAllOrders();
        System.out.println("[DEBUG] Загружено записей: " + orders.size());
        tableView.getItems().setAll(orders);
        // чтобы столбцы занимали всю длину таблицы
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Загрузка данных
        loadData();
    }

    public void loadData() {
        tableView.getItems().setAll(orderDao.getAllOrders());
    }



}
