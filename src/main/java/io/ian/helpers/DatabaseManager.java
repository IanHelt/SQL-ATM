package io.ian.helpers;

import java.sql.*;
import java.time.LocalDateTime;

public class DatabaseManager {

    private Connection conn;
    private Statement statement;

    public DatabaseManager(Connection conn) throws SQLException {
        this.conn = conn;
        this.statement = conn.createStatement();
    }

    public void depositFunds(Integer id, Double amount) throws SQLException {
        ResultSet user = statement.executeQuery(String.format("SELECT * FROM users WHERE ID = %d;", id));
        Double userBalance = user.getDouble("balance");
        String date = LocalDateTime.now().toString();
        Double newBalance = userBalance + amount;
        if (id == null) {
            System.out.println("User not found");
        }
        else if (amount <= 0){
            System.out.println("Invalid deposit amount");
        } else {
            String sqlDeposit = String.format("UPDATE users SET balance = %s WHERE ID = %d", newBalance, id);
            String transactionInfo = String.format("INSERT INTO transactions (amount, date, userID) VALUES ('%s', '%s', %d)", amount, date, id);
            statement.executeUpdate(sqlDeposit);
            statement.executeUpdate(transactionInfo);
            System.out.println("Deposit successful");
        }
    }

    public void withdrawFunds(Integer id, Double amount) throws SQLException {
        ResultSet user = statement.executeQuery(String.format("SELECT * FROM users WHERE ID = %d;", id));
        Double userBalance = user.getDouble("balance");
        String date = LocalDateTime.now().toString();
        Double negativeAmount = (0 - amount);
        Double newBalance = userBalance + negativeAmount;
        if (user.getInt(id) != id) {
            System.out.println("User not found");
        }
        else if (newBalance < 0 || amount <= 0){
            System.out.println("Invalid withdrawal amount");
        } else {
            String sqlWithdraw = String.format("UPDATE users SET balance = %s WHERE ID = %d", newBalance, id);
            String transactionInfo = String.format("INSERT INTO transactions (amount, date, userID) VALUES ('%s', '%s', %d)", negativeAmount, date, id);
            statement.executeUpdate(sqlWithdraw);
            statement.executeUpdate(transactionInfo);
            System.out.println("Withdrawal successful");
        }
    }

    public void viewFunds(Integer id) throws SQLException {
        ResultSet user = statement.executeQuery(String.format("SELECT balance FROM users WHERE ID = %d;", id));
        Double userBalance = user.getDouble("balance");
        if (id == null) {
            System.out.println("User not found");
        } else {
            System.out.println(userBalance);
        }
    }
}
