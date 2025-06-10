package gui.controllers;

import dao.BayerDao;
import entities.Bayer;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class BayerTableController {
    @FXML
    private TableView<Bayer> tableView;

    @FXML
    private TableColumn<Bayer, Long> idColumn;

    @FXML
    private TableColumn<Bayer, String> firstNameColumn;

    @FXML
    private TableColumn<Bayer, String> secondNameColumn;

    @FXML
    private TableColumn<Bayer, ?> thirdNameColumn;

    @FXML
    private TableColumn<Bayer, String> genderColumn;

    @FXML
    private TableColumn<Bayer, String> emailColumn;

    @FXML
    private TableColumn<Bayer, String> phoneNumberColumn;

    private BayerDao bayerDao = new BayerDao();


    @FXML
    public void initialize() {
        // Настройка колонок
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        secondNameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));
        thirdNameColumn.setCellValueFactory(new PropertyValueFactory<>("thirdName"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        System.out.println("[DEBUG] Загрузка данных...");
        List<Bayer> bayers = bayerDao.getAllBayers();
        System.out.println("[DEBUG] Загружено записей: " + bayers.size());
        tableView.getItems().setAll(bayers);
        // чтобы столбцы занимали всю длину таблицы
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Загрузка данных
        loadData();
    }

    public void loadData() {
        tableView.getItems().setAll(bayerDao.getAllBayers());
    }
}
