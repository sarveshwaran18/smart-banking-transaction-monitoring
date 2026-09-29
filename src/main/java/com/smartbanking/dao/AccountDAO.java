package com.smartbanking.dao;

import com.smartbanking.model.Account;
import com.smartbanking.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDAO {

    public Account findByUserId(int userId) {
        String sql = "SELECT id, user_id, account_number, balance " +
                     "FROM accounts WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return new Account(
                            result.getInt("id"),
                            result.getInt("user_id"),
                            result.getString("account_number"),
                            result.getBigDecimal("balance")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateBalance(Connection connection, int accountId, java.math.BigDecimal newBalance)
            throws Exception {
        String sql = "UPDATE accounts SET balance = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, newBalance);
            statement.setInt(2, accountId);
            return statement.executeUpdate() == 1;
        }
    }
}
