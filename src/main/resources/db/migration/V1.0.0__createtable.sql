-- Roles Table
CREATE TABLE roles (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       insert_date_time TIMESTAMP NOT NULL,
                       insert_user_id BIGINT NOT NULL,
                       last_update_date_time TIMESTAMP NOT NULL,
                       last_update_user_id BIGINT NOT NULL,
                       is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
                       description VARCHAR(255) NOT NULL
);
-- Addresses Table
CREATE TABLE addresses (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           insert_date_time TIMESTAMP NOT NULL,
                           insert_user_id BIGINT NOT NULL,
                           last_update_date_time TIMESTAMP NOT NULL,
                           last_update_user_id BIGINT NOT NULL,
                           is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
                           address_line1 VARCHAR(255) NOT NULL,
                           address_line2 VARCHAR(255),
                           city VARCHAR(255) NOT NULL,
                           state VARCHAR(255) NOT NULL,
                           country VARCHAR(255) NOT NULL,
                           zip_code VARCHAR(20) NOT NULL
);
-- Companies Table
CREATE TABLE companies (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           insert_date_time TIMESTAMP NOT NULL,
                           insert_user_id BIGINT NOT NULL,
                           last_update_date_time TIMESTAMP NOT NULL,
                           last_update_user_id BIGINT NOT NULL,
                           is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
                           title VARCHAR(255) NOT NULL,
                           phone VARCHAR(20) NOT NULL,
                           website VARCHAR(255),
                           address_id BIGINT NOT NULL,
                           company_status VARCHAR(50) NOT NULL
);
-- Users Table
CREATE TABLE users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       insert_date_time TIMESTAMP NOT NULL,
                       insert_user_id BIGINT NOT NULL,
                       last_update_date_time TIMESTAMP NOT NULL,
                       last_update_user_id BIGINT NOT NULL,
                       is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       firstname VARCHAR(255) NOT NULL,
                       lastname VARCHAR(255) NOT NULL,
                       phone VARCHAR(20) NOT NULL,
                       role_id BIGINT NOT NULL,
                       company_id BIGINT NOT NULL,
                       enabled BOOLEAN NOT NULL
);
-- Clients_Vendors Table
CREATE TABLE clients_vendors (
                                 id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                 insert_date_time TIMESTAMP NOT NULL,
                                 insert_user_id BIGINT NOT NULL,
                                 last_update_date_time TIMESTAMP NOT NULL,
                                 last_update_user_id BIGINT NOT NULL,
                                 is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
                                 client_vendor_type VARCHAR(50) NOT NULL,
                                 client_vendor_name VARCHAR(255) NOT NULL,
                                 phone VARCHAR(20) NOT NULL,
                                 website VARCHAR(255),
                                 address_id BIGINT NOT NULL,
                                 company_id BIGINT NOT NULL
);
-- Categories Table
CREATE TABLE categories (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            insert_date_time TIMESTAMP NOT NULL,
                            insert_user_id BIGINT NOT NULL,
                            last_update_date_time TIMESTAMP NOT NULL,
                            last_update_user_id BIGINT NOT NULL,
                            is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
                            description VARCHAR(255) NOT NULL,
                            company_id BIGINT NOT NULL
);
-- Products Table
CREATE TABLE products (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          insert_date_time TIMESTAMP NOT NULL,
                          insert_user_id BIGINT NOT NULL,
                          last_update_date_time TIMESTAMP NOT NULL,
                          last_update_user_id BIGINT NOT NULL,
                          is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
                          name VARCHAR(255) NOT NULL,
                          quantity_in_stock INT NOT NULL,
                          low_limit_alert INT NOT NULL,
                          product_unit VARCHAR(20) NOT NULL,
                          category_id BIGINT NOT NULL
);
-- Invoices Table
CREATE TABLE invoices (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          insert_date_time TIMESTAMP NOT NULL,
                          insert_user_id BIGINT NOT NULL,
                          last_update_date_time TIMESTAMP NOT NULL,
                          last_update_user_id BIGINT NOT NULL,
                          is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
                          date DATE NOT NULL,
                          invoice_no VARCHAR(50) NOT NULL,
                          invoice_type VARCHAR(50) NOT NULL,
                          invoice_status VARCHAR(50) NOT NULL,
                          client_vendor_id BIGINT NOT NULL,
                          company_id BIGINT NOT NULL
);
-- Invoice_Products Table
CREATE TABLE invoice_products (
                                  id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                  insert_date_time TIMESTAMP NOT NULL,
                                  insert_user_id BIGINT NOT NULL,
                                  last_update_date_time TIMESTAMP NOT NULL,
                                  last_update_user_id BIGINT NOT NULL,
                                  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
                                  price DECIMAL(10, 2) NOT NULL,
                                  quantity INT NOT NULL,
                                  remaining_quantity INT NOT NULL,
                                  tax DECIMAL(5, 2) NOT NULL,
                                  profit_loss DECIMAL(10, 2) NOT NULL,
                                  invoice_id BIGINT NOT NULL,
                                  product_id BIGINT NOT NULL
);
-- Insert data into products table
INSERT INTO products (insert_date_time, insert_user_id, is_deleted, last_update_date_time, last_update_user_id,
                      name, quantity_in_stock, low_limit_alert, product_unit, category_id)
VALUES
    ('2022-09-15 00:00', 2, 'false', '2022-09-15 00:00', 2, 'HP Elite 800G1 Desktop Computer Package', 8, 5, 'PCS', 1),
    ('2022-09-15 00:00', 2, 'false', '2022-09-15 00:00', 2, '2021 Apple MacBook Pro', 0, 5, 'PCS', 1),
    ('2022-09-15 00:00', 2, 'false', '2022-09-15 00:00', 2, 'Apple iPhone-13', 0, 5, 'PCS', 2),
    ('2022-09-15 00:00', 2, 'false', '2022-09-15 00:00', 2, 'SAMSUNG Galaxy S22', 0, 5, 'PCS', 2),
    ('2022-09-15 00:00', 3, 'false', '2022-09-15 00:00', 3, 'Samsung Galaxy S20 (renewed)', 30, 5, 'PCS', 3),
    ('2022-09-15 00:00', 3, 'false', '2022-09-15 00:00', 3, 'Samsung Galaxy S22', 20, 5, 'PCS', 3),
    ('2022-09-15 00:00', 3, 'false', '2022-09-15 00:00', 3, 'Moto G Power', 0, 5, 'PCS', 3);
-- Insert data into invoices table
INSERT INTO invoices (insert_date_time, insert_user_id, is_deleted, last_update_date_time, last_update_user_id,
                      date, invoice_no, invoice_type, invoice_status, client_vendor_id, company_id)
VALUES
    ('2022-09-09 00:00', 2, 'false', '2022-09-09 00:00', 2, '2022-09-09', 'P-001', 'PURCHASE', 'APPROVED', 2, 2),
    ('2022-09-10 00:00', 2, 'false', '2022-09-10 00:00', 2, '2022-09-10', 'P-002', 'PURCHASE', 'APPROVED', 3, 2),
    ('2022-09-17 00:00', 2, 'false', '2022-09-17 00:00', 2, '2022-09-17', 'S-001', 'SALES', 'APPROVED', 1, 2),
    ('2022-10-19 00:00', 2, 'false', '2022-10-19 00:00', 2, '2022-10-19', 'S-002', 'SALES', 'AWAITING_APPROVAL', 1, 2),
    ('2022-11-20 00:00', 2, 'false', '2022-11-20 00:00', 2, '2022-11-20', 'S-003', 'SALES', 'AWAITING_APPROVAL', 1, 2),
    ('2022-09-09 00:00', 3, 'false', '2022-09-09 00:00', 3, '2022-09-09', 'P-001', 'PURCHASE', 'APPROVED', 5, 3),
    ('2022-09-10 00:00', 3, 'false', '2022-09-10 00:00', 3, '2022-09-10', 'P-002', 'PURCHASE', 'APPROVED', 5, 3),
    ('2022-09-13 00:00', 3, 'false', '2022-09-13 00:00', 3, '2022-09-13', 'S-001', 'SALES', 'APPROVED', 4, 3),
