package com.smartbanking.dao;

import com.smartbanking.model.User;
import com.smartbanking.util.DBConnection;
import com.smartbanking.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public User authenticate(String username, String password) {
        String sql = "SELECT id, username, full_name, role FROM users " +
                     "WHERE username = ? AND password_hash = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, PasswordUtil.sha256(password));

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return new User(
                            result.getInt("id"),
                            result.getString("username"),
                            result.getString("full_name"),
                            result.getString("role")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
