-- Roles Table
CREATE TABLE roles (
                       id BIGSERIAL PRIMARY KEY,
                       insert_date_time TIMESTAMP NOT NULL,
                       insert_user_id BIGINT NOT NULL,
                       last_update_date_time TIMESTAMP NOT NULL,
                       last_update_user_id BIGINT NOT NULL,
                       is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
                       description VARCHAR(255) NOT NULL
);
-- Addresses Table
CREATE TABLE addresses (
                           id BIGSERIAL PRIMARY KEY,
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
                           id BIGSERIAL PRIMARY KEY,
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
                       id BIGSERIAL PRIMARY KEY,
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
                                 id BIGSERIAL PRIMARY KEY,
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
                            id BIGSERIAL PRIMARY KEY,
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
                          id BIGSERIAL PRIMARY KEY,
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
                          id BIGSERIAL PRIMARY KEY,
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
                                  id BIGSERIAL PRIMARY KEY,
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
