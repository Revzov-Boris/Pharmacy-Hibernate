package gui.controllers;

import dao.PositionDao;
import entities.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class PositionTableController {

    @FXML
    private TableColumn<Position, Long> chequeIdColumn;

    @FXML
    private TableColumn<Position, Long> medicineIdColumn;

    @FXML
    private TableColumn<Position, Short> quantityColumn;

    @FXML
    private TableView<Position> tableView;

    private PositionDao positionDao = new PositionDao();

    @FXML
    public void initialize() {
        medicineIdColumn.setCellValueFactory(cellData -> {
            Medicine medicine = cellData.getValue().getMedicine();
            return new SimpleObjectProperty<>(medicine.getId());
        });
        chequeIdColumn.setCellValueFactory(cellData -> {
            Cheque cheque = cellData.getValue().getCheque();
            return new SimpleObjectProperty<>(cheque.getId());
        });
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        System.out.println("[DEBUG] Загрузка данных...");
        List<Position> positions = positionDao.getAllPositions();
        System.out.println("[DEBUG] Загружено записей: " + positions.size());
        tableView.getItems().setAll(positions);
        // чтобы столбцы занимали всю длину таблицы
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Загрузка данных
        loadData();
    }

    public void loadData() {
        tableView.getItems().setAll(positionDao.getAllPositions());
    }

}
