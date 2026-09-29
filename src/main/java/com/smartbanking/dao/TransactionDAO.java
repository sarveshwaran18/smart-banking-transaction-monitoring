package com.smartbanking.dao;

import com.smartbanking.model.Transaction;
import com.smartbanking.util.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public List<Transaction> findByAccountId(int accountId) {
        String sql = "SELECT t.id, a.account_number, t.transaction_type, t.amount, " +
                     "t.description, t.is_flagged, t.alert_reason, t.transaction_time " +
                     "FROM transactions t JOIN accounts a ON t.account_id = a.id " +
                     "WHERE t.account_id = ? ORDER BY t.transaction_time DESC";

        List<Transaction> transactions = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, accountId);

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    transactions.add(map(result));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public List<Transaction> findFlaggedTransactions() {
        String sql = "SELECT t.id, a.account_number, t.transaction_type, t.amount, " +
                     "t.description, t.is_flagged, t.alert_reason, t.transaction_time " +
                     "FROM transactions t JOIN accounts a ON t.account_id = a.id " +
                     "WHERE t.is_flagged = TRUE ORDER BY t.transaction_time DESC";

        List<Transaction> transactions = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                transactions.add(map(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public String createTransaction(int accountId, String type, BigDecimal amount, String description) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return "Enter a valid transaction amount.";
        }

        String balanceSql = "SELECT balance FROM accounts WHERE id = ? FOR UPDATE";
        String countSql = "SELECT COUNT(*) FROM transactions " +
                          "WHERE account_id = ? AND transaction_time >= NOW() - INTERVAL 10 MINUTE";
        String insertSql = "INSERT INTO transactions " +
                           "(account_id, transaction_type, amount, description, is_flagged, alert_reason) " +
                           "VALUES (?, ?, ?, ?, ?, ?)";
        String updateSql = "UPDATE accounts SET balance = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);

            try {
                BigDecimal balance;

                try (PreparedStatement statement = connection.prepareStatement(balanceSql)) {
                    statement.setInt(1, accountId);
                    try (ResultSet result = statement.executeQuery()) {
                        if (!result.next()) {
                            connection.rollback();
                            return "Account not found.";
                        }
                        balance = result.getBigDecimal("balance");
                    }
                }

                if ("WITHDRAWAL".equals(type) && balance.compareTo(amount) < 0) {
                    connection.rollback();
                    return "Insufficient balance.";
                }

                BigDecimal newBalance = "DEPOSIT".equals(type)
                        ? balance.add(amount)
                        : balance.subtract(amount);

                int recentCount = 0;
                try (PreparedStatement statement = connection.prepareStatement(countSql)) {
                    statement.setInt(1, accountId);
                    try (ResultSet result = statement.executeQuery()) {
                        if (result.next()) {
                            recentCount = result.getInt(1);
                        }
                    }
                }

                boolean flagged = amount.compareTo(new BigDecimal("100000")) >= 0
                        || recentCount >= 2;

                String reason = null;
                if (amount.compareTo(new BigDecimal("100000")) >= 0) {
                    reason = "Large transaction amount";
                } else if (recentCount >= 2) {
                    reason = "Multiple transactions within 10 minutes";
                }

                try (PreparedStatement statement = connection.prepareStatement(updateSql)) {
                    statement.setBigDecimal(1, newBalance);
                    statement.setInt(2, accountId);
                    statement.executeUpdate();
                }

                try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
                    statement.setInt(1, accountId);
                    statement.setString(2, type);
                    statement.setBigDecimal(3, amount);
                    statement.setString(4, description);
                    statement.setBoolean(5, flagged);
                    statement.setString(6, reason);
                    statement.executeUpdate();
                }

                connection.commit();

                return flagged
                        ? "Transaction completed and flagged for monitoring: " + reason
                        : "Transaction completed successfully.";

            } catch (Exception e) {
                connection.rollback();
                e.printStackTrace();
                return "Transaction failed.";
            } finally {
                connection.setAutoCommit(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Database connection failed.";
        }
    }

    private Transaction map(ResultSet result) throws Exception {
        return new Transaction(
                result.getInt("id"),
                result.getString("account_number"),
                result.getString("transaction_type"),
                result.getBigDecimal("amount"),
                result.getString("description"),
                result.getBoolean("is_flagged"),
                result.getString("alert_reason"),
                result.getTimestamp("transaction_time")
        );
    }
}
