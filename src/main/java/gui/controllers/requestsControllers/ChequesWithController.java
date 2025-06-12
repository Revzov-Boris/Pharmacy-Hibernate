package gui.controllers.requestsControllers;

import dao.ChequeDao;
import dao.requests.ChequesWithDietarySupplements;
import entities.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

public class ChequesWithController {

    @FXML
    private TableColumn<Cheque, Long> bayerIdColumn;

    @FXML
    private TableColumn<Cheque, LocalDate> dateColumn;

    @FXML
    private TableColumn<Cheque, Long> idColumn;

    @FXML
    private TableColumn<Cheque, Long> pharmacyIdColumn;

    @FXML
    private TableView<Cheque> tableView;

    private ChequeDao chequeDao = new ChequeDao();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        pharmacyIdColumn.setCellValueFactory(cellData -> {
            Pharmacy pharmacy = cellData.getValue().getPharmacy();
            return new SimpleObjectProperty<>(pharmacy.getId());
        });
        bayerIdColumn.setCellValueFactory(cellData -> {
            Bayer bayer = cellData.getValue().getBayer();
            return new SimpleObjectProperty<>(bayer.getId());
        });
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        System.out.println("[DEBUG] Загрузка данных...");
        List<Cheque> cheques = ChequesWithDietarySupplements.getCheque();
        System.out.println("[DEBUG] Загружено записей: " + cheques.size());
        tableView.getItems().setAll(cheques);
        // чтобы столбцы занимали всю длину таблицы
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Загрузка данных
        loadData();
    }

    public void loadData() {
        tableView.getItems().setAll(ChequesWithDietarySupplements.getCheque());
    }

}
