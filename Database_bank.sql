CREATE DATABASE bankingsystem;

Use bankingsystem;

CREATE TABLE account_transaction (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(20),
    balance BIGINT NOT NULL,
    transaction_type VARCHAR(10),
    amount BIGINT NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (account_number) REFERENCES users_tb(account_number)

);
-- INSERT INTO account_transaction (account_number, balance, transaction_type, amount) VALUES ('123456', 1000, 'deposit', 1000);

select* from account_transaction;
CREATE TABLE trans (
    transaction_id VARCHAR(50) PRIMARY KEY,
    account_number VARCHAR(20),
    amount DOUBLE,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    type ENUM('Credit', 'Debit'),
	FOREIGN KEY (account_number) REFERENCES users_tb(account_number)
);
Select * from trans;


-- Create the users table
CREATE TABLE users (
    user_id VARCHAR(255) PRIMARY KEY,
    pin VARCHAR(4),
    address VARCHAR(255),
    email VARCHAR(255),
    aadhar VARCHAR(12),
    pan VARCHAR(10)
);

-- Insert multiple sample users

CREATE TABLE IF NOT EXISTS Customers (
    customerId VARCHAR(50) PRIMARY KEY,
    customerName VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    contactNumber VARCHAR(15),
    ssn VARCHAR(11) NOT NULL
);
Select * from Customers;

CREATE TABLE IF NOT EXISTS Cards (
    accountNumber VARCHAR(20) PRIMARY KEY,
    customerId VARCHAR(50),
    cardType ENUM('DEBIT', 'CREDIT') NOT NULL,
    isBlocked BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (customerId) REFERENCES Customers(customerId)
);
Select * from Cards;
CREATE TABLE loans (
   id INT AUTO_INCREMENT PRIMARY KEY,
   account_number VARCHAR(20),
   principal DOUBLE,
   interest_rate DOUBLE,
   term_in_years INT,
   FOREIGN KEY (account_number) REFERENCES users_tb(account_number)
);

Select * from loans;


CREATE TABLE users_tb (
     id INT AUTO_INCREMENT PRIMARY KEY,
     account_number VARCHAR(20) NOT NULL UNIQUE,
     name VARCHAR(100) NOT NULL,
     pin int NOT NULL,
     address VARCHAR(200) NOT NULL,
     pan VARCHAR(50) NOT NULL,
     aadhar long NOT NULL,
     phone_number long NOT NULL     
 );

-- ALTER TABLE account_transaction
-- ADD CONSTRAINT fk_account_number
-- FOREIGN KEY (account_number) REFERENCES users_tb(account_number);

Select * from users_tb;
