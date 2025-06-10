package gui.controllers;

import dao.ManufacturerDao;
import entities.*;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


import java.util.List;

public class ManufacturerTableController {
    @FXML
    private TableView<Manufacturer> tableView;

    @FXML
    private TableColumn<Manufacturer, Long> idColumn;

    @FXML
    private TableColumn<Manufacturer, String> nameColumn;

    @FXML
    private TableColumn<Manufacturer, String> phoneColumn;

    @FXML
    private TableColumn<Manufacturer, String> addressColumn;

    private ManufacturerDao manufacturerDao = new ManufacturerDao();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        System.out.println("[DEBUG] Загрузка данных...");
        List<Manufacturer> manufacturers = manufacturerDao.getAllManufacturers();
        System.out.println("[DEBUG] Загружено записей: " + manufacturers.size());
        tableView.getItems().setAll(manufacturers);
        // чтобы столбцы занимали всю длину таблицы
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Загрузка данных
        loadData();
    }

    public void loadData() {
        tableView.getItems().setAll(manufacturerDao.getAllManufacturers());
    }









}
