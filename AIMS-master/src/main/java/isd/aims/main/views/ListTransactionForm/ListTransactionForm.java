package isd.aims.main.views.ListTransactionForm;

import isd.aims.main.controller.TransactionService;
import isd.aims.main.entity.Transaction;
import isd.aims.main.views.BaseForm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ListTransactionForm extends BaseForm {

    @FXML
    private TableView<Transaction> tableView;

    @FXML
    private TableColumn<Transaction, String> orderIDColumn;

    @FXML
    private TableColumn<Transaction, String> createAtColumn;

    @FXML
    private TableColumn<Transaction, String> contentColumn;

    public ListTransactionForm(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    @Override
    public void show() {
        super.show();
        loadTransactions();
    }

    private void loadTransactions() {
        TransactionService transactionService = new TransactionService();
        List<Transaction> transactions = transactionService.getTransactions();

        // Chuyển đổi danh sách thành ObservableList để hiển thị trong TableView
        ObservableList<Transaction> transactionList = FXCollections.observableArrayList(transactions);

        // Đặt dữ liệu cho các cột
        orderIDColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        createAtColumn.setCellValueFactory(new PropertyValueFactory<>("createAt"));
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));

        // Hiển thị các giao dịch trong TableView
        tableView.setItems(transactionList);
    }
}
