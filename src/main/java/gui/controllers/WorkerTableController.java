package gui.controllers;

import dao.MedicineDao;
import dao.WorkerDao;
import entities.Medicine;
import entities.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

public class WorkerTableController {
    @FXML
    private TableView<Worker> tableView;

    @FXML
    private TableColumn<Worker, Long> idColumn;

    @FXML
    private TableColumn<Worker, LocalDate> dateBirthColumn;

    @FXML
    private TableColumn<Worker, String> educationColumn;

    @FXML
    private TableColumn<Worker, String> emailColumn;

    @FXML
    private TableColumn<Worker, String> firstNameColumn;

    @FXML
    private TableColumn<Worker, String> secondNameColumn;

    @FXML
    private TableColumn<Worker, ?> thirdNameColumn;

    @FXML
    private TableColumn<Worker, String> genderColumn;

    @FXML
    private TableColumn<Worker, String> phoneNumberColumn;

    private WorkerDao workerDao = new WorkerDao();

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
        dateBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        educationColumn.setCellValueFactory(new PropertyValueFactory<>("education"));

        System.out.println("[DEBUG] Загрузка данных...");
        List<Worker> workers = workerDao.getAllWorkers();
        System.out.println("[DEBUG] Загружено записей: " + workers.size());
        tableView.getItems().setAll(workers);
        // чтобы столбцы занимали всю длину таблицы
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Загрузка данных
        loadData();
    }

    public void loadData() {
        tableView.getItems().setAll(workerDao.getAllWorkers());
    }






}
