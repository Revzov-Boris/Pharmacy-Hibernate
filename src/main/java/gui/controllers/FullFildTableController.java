package gui.controllers;

import dao.FullfildDao;
import entities.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

public class FullFildTableController {
    @FXML
    private TableColumn<Fullfild, LocalDate> dateColumn;

    @FXML
    private TableColumn<Fullfild, Long> idColumn;

    @FXML
    private TableView<Fullfild> tableView;

    private FullfildDao fullfildDao = new FullfildDao();

    @FXML
    public void initialize() {
       // idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setCellValueFactory(cellData -> {
            Order order = cellData.getValue().getOrder();
            return new SimpleObjectProperty<>(order.getId());
        });
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        System.out.println("[DEBUG] Загрузка данных...");
        List<Fullfild> fullfilds = fullfildDao.getAllFullfild();
        System.out.println("[DEBUG] Загружено записей: " + fullfilds.size());
        tableView.getItems().setAll(fullfilds);
        // чтобы столбцы занимали всю длину таблицы
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Загрузка данных
        loadData();
    }

    public void loadData() {
        tableView.getItems().setAll(fullfildDao.getAllFullfild());
    }
}
