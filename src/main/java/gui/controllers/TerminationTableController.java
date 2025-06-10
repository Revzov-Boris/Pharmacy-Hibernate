package gui.controllers;

import dao.TerminationDao;
import entities.Contract;
import entities.Termination;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

public class TerminationTableController {
    @FXML
    private TableView<Termination> tableView;

    @FXML
    private TableColumn<Termination, Long> idColumn;

    @FXML
    private TableColumn<Termination, LocalDate> dateColumn;

    private TerminationDao terminationDao = new TerminationDao();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> {
            Contract contract = cellData.getValue().getContract();
            return new SimpleObjectProperty<>(contract.getId());
        });
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        System.out.println("[DEBUG] Загрузка данных...");
        List<Termination> terminations = terminationDao.getAllTerminations();
        System.out.println("[DEBUG] Загружено записей: " + terminations.size());
        tableView.getItems().setAll(terminations);
        // чтобы столбцы занимали всю длину таблицы
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Загрузка данных
        loadData();
    }

    public void loadData() {
        tableView.getItems().setAll(terminationDao.getAllTerminations());
    }
}
