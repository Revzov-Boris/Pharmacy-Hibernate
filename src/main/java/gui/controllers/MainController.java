package gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class MainController {
    private Map<String, String> pathsToTableFxml = Map.ofEntries(
            Map.entry("Покупатели", "/fxml/tables/bayer.fxml"),
            Map.entry("Аптеки", "/fxml/tables/pharmacy.fxml"),
            Map.entry("Сотрудники", "/fxml/tables/worker.fxml"),
            Map.entry("Договоры", "/fxml/tables/contract.fxml"),
            Map.entry("Расторжения", "/fxml/tables/termination.fxml"),
            Map.entry("Производители", "/fxml/tables/manufacturer.fxml"),
            Map.entry("Лекарства", "/fxml/tables/medicine.fxml"),
            Map.entry("Закупки", "/fxml/tables/order.fxml"),
            Map.entry( "Выполненные", "/fxml/tables/fullfild.fxml"),
            Map.entry ("Чеки", "/fxml/tables/cheque.fxml"),
            Map.entry ("Позиции", "/fxml/tables/position.fxml")
    );

    private Map<String, String> pathsToParameterizedRequestFxml = Map.ofEntries(
            Map.entry("Покупки в аптеке", "/fxml/requests/purchasesInPharmacy.fxml"),
            Map.entry("Покупатель в городе", "/fxml/requests/bayerInCity.fxml"),
            Map.entry("Работники аптеки", "/fxml/requests/currentContractsInPharmacy.fxml"),
            Map.entry("Дорогие закупки", "/fxml/requests/bigOrders.fxml"),
            Map.entry("Чеки с БАД", "/fxml/requests/chequesWith.fxml"),
            Map.entry("Агрегатные запросы", "/fxml/requests/aggregateSearch.fxml"),
            Map.entry("Удалить невыполненные закупки", "/fxml/changeRequests/deleteUnfulfulledOrders.fxml"),
            Map.entry("Повысить зарплаты", "/fxml/changeRequests/RaiseWage.fxml"),
            Map.entry("Купить", "/fxml/changeRequests/addBuy.fxml")
    );

    @FXML
    private AnchorPane rightPane;

    @FXML
    private ListView<String> tableListView;

    @FXML
    private ListView<String> searchListView;


    private void loadContent(String path) {
        try {
            System.out.println("Загрузка: " + path);
            URL url = getClass().getResource(path);
            if (url == null) {
                System.err.println("ERROR: Ресурс не найден: " + path);
                return;
            }
            System.out.println("Полный путь: " + url.getPath());

            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Node content = loader.load();
            rightPane.getChildren().setAll(content);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка загрузки: " + e.getMessage());
            // вывод ошибки в окне
            rightPane.getChildren().setAll(new Label("Ошибка загрузки: " + e.getMessage()));
        }
    }

    @FXML
    private void initialize() {
        tableListView.getItems().addAll(pathsToTableFxml.keySet());
        searchListView.getItems().addAll(pathsToParameterizedRequestFxml.keySet());
        tableListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadContent(pathsToTableFxml.get(newVal));
            }
        });
        searchListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadContent(pathsToParameterizedRequestFxml.get(newVal));
            }
        });

    }

}