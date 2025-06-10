package gui.controllers;

import dao.PharmacyDao;
import entities.Medicine;
import entities.Pharmacy;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class PharmacyTableController {
    @FXML
    private TableView<Pharmacy> tableView;

    @FXML
    private TableColumn<Pharmacy, Long> idColumn;

    @FXML
    private TableColumn<Pharmacy, String> nameColumn;

    @FXML
    private TableColumn<Pharmacy, String> phoneNumberColumn;
    
    @FXML
    private TableColumn<Pharmacy, String> addressColumn;

    @FXML
    private TableColumn<Pharmacy, Short> hallSquareColumn;

    @FXML
    private TableColumn<Pharmacy, Short> storageSquareColumn;

    private PharmacyDao pharmacyDao = new PharmacyDao();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        hallSquareColumn.setCellValueFactory(new PropertyValueFactory<>("hallSquare"));
        storageSquareColumn.setCellValueFactory(new PropertyValueFactory<>("storageSquare"));

        System.out.println("[DEBUG] Загрузка данных...");
        List<Pharmacy> pharmacys = pharmacyDao.getAllPharmacys();
        System.out.println("[DEBUG] Загружено записей: " + pharmacys.size());
        tableView.getItems().setAll(pharmacys);
        // чтобы столбцы занимали всю длину таблицы
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Загрузка данных
        loadData();
    }


    public void loadData() {
        tableView.getItems().setAll(pharmacyDao.getAllPharmacys());
    }

    

    

   

    
}
