package isd.aims.main.controller;

import isd.aims.main.entity.Transaction;
import isd.aims.main.entity.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    // Truy vấn dữ liệu từ bảng Transaction
    public List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT orderID, createAt, content FROM \"Transaction\"";  // Bao quanh tên bảng bằng dấu ngoặc kép
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String orderID = rs.getString("orderID");
                String createAt = rs.getString("createAt");
                String content = rs.getString("content");
                // Thêm giao dịch vào danh sách
                transactions.add(new Transaction(orderID, createAt, content));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

}
