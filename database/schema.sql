CREATE DATABASE IF NOT EXISTS smart_banking;
USE smart_banking;

DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(64) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role ENUM('ADMIN', 'CUSTOMER') NOT NULL DEFAULT 'CUSTOMER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE accounts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL UNIQUE,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE transactions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT NOT NULL,
    transaction_type ENUM('DEPOSIT', 'WITHDRAWAL') NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    description VARCHAR(255),
    is_flagged BOOLEAN NOT NULL DEFAULT FALSE,
    alert_reason VARCHAR(255),
    transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
);

INSERT INTO users (username, password_hash, full_name, role) VALUES
('admin', SHA2('admin123', 256), 'System Administrator', 'ADMIN'),
('customer1', SHA2('customer123', 256), 'Demo Customer', 'CUSTOMER');

INSERT INTO accounts (user_id, account_number, balance) VALUES
(2, 'SB10000001', 50000.00);

INSERT INTO transactions
(account_id, transaction_type, amount, description, is_flagged, alert_reason)
VALUES
(1, 'DEPOSIT', 50000.00, 'Initial account deposit', FALSE, NULL);
