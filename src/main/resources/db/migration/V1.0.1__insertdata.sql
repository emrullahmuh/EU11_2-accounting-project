INSERT INTO roles (insert_date_time, insert_user_id, is_deleted, last_update_date_time, last_update_user_id, description)
VALUES
    ('2022-09-09 00:00:00', 1, false, '2022-09-09 00:00:00', 1, 'Root User'),
    ('2022-09-09 00:00:00', 1, false, '2022-09-09 00:00:00', 1, 'Admin'),
    ('2022-09-09 00:00:00', 1, false, '2022-09-09 00:00:00', 1, 'Manager'),
    ('2022-09-09 00:00:00', 1, false, '2022-09-09 00:00:00', 1, 'Employee');
-- Insert data into addresses table
INSERT INTO addresses (insert_date_time, insert_user_id, is_deleted, last_update_date_time, last_update_user_id,
                       address_line1, address_line2, city, state, country, zip_code)
VALUES
    ('2022-09-15 00:00:00', 1, false, '2022-09-15 00:00:00', 1,
     '7925 Jones Branch Dr, #3300', 'Tysons', 'Virginia', 'VA', 'United States', '22102-1234'),
    ('2022-09-15 00:00:00', 1, false, '2022-09-15 00:00:00', 1,
     'Future Street', 'Times Square', 'Atlanta', 'Alabama', 'United States', '54321-4321'),
    ('2022-09-15 00:00:00', 1, false, '2022-09-15 00:00:00', 1,
     'North Street', 'Circle Square', 'San Francisco', 'California', 'United States', '65245-8546'),
    ('2022-09-15 00:00:00', 1, false, '2022-09-15 00:00:00', 1,
     'West Street', 'Triangle Square', 'Los Angeles', 'California', 'United States', '54782-5214'),
    ('2022-09-15 00:00:00', 1, false, '2022-09-15 00:00:00', 1,
     'East Street', 'Cube Square', 'Los Angeles', 'California', 'United States', '54782-5214'),
    ('2022-09-15 00:00:00', 1, false, '2022-09-15 00:00:00', 1,
     'South Street', 'Times Square', 'Los Angeles', 'California', 'United States', '54782-5214'),
    ('2022-09-15 00:00:00', 1, false, '2022-09-15 00:00:00', 1,
     'SouthWest Street', 'Puzzle Square', 'Los Angeles', 'California', 'United States', '65654-8989'),
    ('2022-09-15 00:00:00', 1, false, '2022-09-15 00:00:00', 1,
     'OwerWest Street', 'Android Square', 'Los Angeles', 'Phoneix', 'United States', '65654-8989');
-- Insert data into companies table
INSERT INTO companies (insert_date_time, insert_user_id, is_deleted, last_update_date_time, last_update_user_id,
                       title, phone, website, address_id, company_status)
VALUES
    ('2022-09-15 00:00:00', 1, false, '2022-09-15 00:00:00', 1, 'CYDEO', '+1 (652) 852-8888', 'https://www.cydeo.com', 1, 'ACTIVE'),
    ('2022-09-15 00:00:00', 1, false, '2022-09-15 00:00:00', 1, 'Green Tech', '+1 (652) 852-3246', 'https://www.greentech.com', 2, 'ACTIVE'),
    ('2022-09-15 00:00:00', 1, false, '2022-09-15 00:00:00', 1, 'Blue Tech', '+1 (215) 654-5268', 'https://www.bluetech.com', 3, 'ACTIVE'),
    ('2022-09-15 00:00:00', 1, false, '2022-09-15 00:00:00', 1, 'Red Tech', '+1 (215) 846-2642', 'https://www.redtech.com', 4, 'PASSIVE');

-- Insert data into users table
INSERT INTO users (insert_date_time, insert_user_id, is_deleted, last_update_date_time, last_update_user_id,
                   username, password, firstname, lastname, phone, role_id, company_id, enabled)
VALUES
    ('2022-09-09 00:00:00', 1, false, '2022-09-09 00:00:00', 1,
     'root@cydeo.com', '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK',
     'Robert', 'Martin', '+1 (852) 564-5874', 1, 1, true),
    ('2022-09-09 00:00:00', 1, false, '2022-09-09 00:00:00', 1,
     'admin@greentech.com', '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK',
     'Mary', 'Grant', '+1 (234) 345-4362', 2, 2, true),
    ('2022-09-09 00:00:00', 1, false, '2022-09-09 00:00:00', 1,
     'admin2@greentech.com', '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK',
     'Garrison', 'Short', '+1 (234) 356-7865', 2, 2, true),
    ('2022-09-09 00:00:00', 1, false, '2022-09-09 00:00:00', 1,
     'manager@greentech.com', '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK',
     'Robert', 'Noah', '+1 (234) 564-5874', 3, 2, true),
    ('2022-09-09 00:00:00', 1, false, '2022-09-09 00:00:00', 1,
     'employee@greentech.com', '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK',
     'Mike', 'Times', '+1 (234) 741-8569', 4, 2, true),
    ('2022-09-09 00:00:00', 1, false, '2022-09-09 00:00:00', 1,
     'admin@bluetech.com', '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK',
     'Chris', 'Brown', '+1 (356) 258-3544', 2, 3, true),
    ('2022-09-09 00:00:00', 1, false, '2022-09-09 00:00:00', 1,
     'manager@bluetech.com', '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK',
     'Tom', 'Hanks', '+1 (356) 258-3544', 3, 3, true),
    ('2022-09-09 00:00:00', 1, false, '2022-09-09 00:00:00', 1,
     'admin@redtech.com', '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK',
     'John','Doe', '+1 (659) 756-1265', 2, 4, true);
-- Insert data into clients_vendors table
INSERT INTO clients_vendors (insert_date_time, insert_user_id, is_deleted, last_update_date_time, last_update_user_id,
                             client_vendor_type, client_vendor_name, phone, website, address_id, company_id)
VALUES
    ('2022-09-15T00:00', 2, false, '2022-09-15T00:00', 2, 'CLIENT', 'Orange Tech', '+1 (251) 321-4155', 'https://www.orange.com', 5, 2),
    ('2022-09-15T00:00', 2, false, '2022-09-15T00:00', 2, 'CLIENT', 'Ower Tech', '+1 (251) 321-4141', 'https://www.ower.com', 8, 2),
    ('2022-09-15T00:00', 2, false, '2022-09-15T00:00', 2, 'VENDOR', 'Photobug Tech', '+1 (652) 852-3246', 'https://www.photobug.com', 6, 2),
    ('2022-09-15T00:00', 2, false, '2022-09-15T00:00', 2, 'VENDOR', 'Wordtune Tech', '+1 (652) 852-3246', 'https://www.wordtune.com', 7, 2),
    ('2022-09-15T00:00', 3, false, '2022-09-15T00:00', 3, 'CLIENT', 'Reallinks Tech', '+1 (652) 852-9544', 'https://www.reallinks.com', 3, 3),
    ('2022-09-15T00:00', 3, false, '2022-09-15T00:00', 3, 'VENDOR', 'Livetube Tech', '+1 (652) 852-2055', 'https://www.livetube.com', 4, 3),
    ('2022-09-15T00:00', 3, false, '2022-09-15T00:00', 3, 'CLIENT', 'Key Tech', '+1 (652) 852-7896', 'https://www.keytech.com', 1, 3),
    ('2022-09-15T00:00', 3, false, '2022-09-15T00:00', 3, 'VENDOR', 'Mod Tech', '+1 (652) 852-3648', 'https://www.modtech.com', 2, 3);
-- Insert data into categories table
INSERT INTO categories (insert_date_time, insert_user_id, is_deleted, last_update_date_time, last_update_user_id,
                        description, company_id)
VALUES
    ('2022-09-15 00:00:00', 2, false, '2022-09-15 00:00:00', 2, 'Computer', 2),
    ('2022-09-15 00:00:00', 2, false, '2022-09-15 00:00:00', 2, 'Phone', 2),
    ('2022-09-15 00:00:00', 3, false, '2022-09-15 00:00:00', 3, 'Phone', 3),
    ('2022-09-15 00:00:00', 3, false, '2022-09-15 00:00:00', 3, 'TV', 3),
    ('2022-09-15 00:00:00', 3, false, '2022-09-15 00:00:00', 3, 'Monitor', 3);
-- Insert data into invoice_products table
INSERT INTO invoice_products (insert_date_time, insert_user_id, is_deleted, last_update_date_time, last_update_user_id,
                              price, quantity, remaining_quantity, tax, profit_loss, invoice_id, product_id)
VALUES
    -- COMPANY-2 / Green Tech
    ('2022-09-15 00:00', 2, 'false', '2022-09-15 00:00', 2, 250, 5, 3, 10, 0, 1, 1),  -- Purchase APPROVED
    ('2022-09-15 00:00', 2, 'false', '2022-09-15 00:00', 2, 250, 5, 5, 10, 0, 2, 1),  -- Purchase APPROVED
    ('2022-09-15 00:00', 2, 'false', '2022-09-15 00:00', 2, 300, 2, 0, 10, 110, 3, 1), -- Sale APPROVED
    ('2022-09-15 00:00', 2, 'false', '2022-09-15 00:00', 2, 200, 2, 0, 10, 0, 4, 1),  -- Sale AWAITING_APPROVAL
    ('2022-09-15 00:00', 2, 'false', '2022-09-15 00:00', 2, 300, 5, 0, 10, 0, 5, 1),  -- Sale AWAITING_APPROVAL

    -- COMPANY-3 / Blue Tech
    ('2022-09-15 00:00', 3, 'false', '2022-09-15 00:00', 3, 200, 20, 0, 10, 0, 6, 5),  -- Purchase APPROVED
    ('2022-09-15 00:00', 3, 'false', '2022-09-15 00:00', 3, 200, 20, 10, 10, 0, 6, 5), -- Purchase APPROVED
    ('2022-09-15 00:00', 3, 'false', '2022-09-15 00:00', 3, 900, 10, 10, 10, 0, 7, 6), -- Purchase APPROVED
    ('2022-09-15 00:00', 3, 'false', '2022-09-15 00:00', 3, 1000, 10, 10, 10, 0, 7, 6),-- Purchase APPROVED
    ('2022-09-15 00:00', 3, 'false', '2022-09-15 00:00', 3, 300, 10, 0, 10, 1100, 8, 5),-- Sale APPROVED
    ('2022-09-15 00:00', 3, 'false', '2022-09-15 00:00', 3, 300, 20, 0, 10, 2200, 8, 5),-- Sale APPROVED
    ('2022-09-15 00:00', 3, 'false', '2022-09-15 00:00', 3, 1200, 6, 0, 10, 0, 9, 6),  -- Sale AWAITING_APPROVAL
    ('2022-09-15 00:00', 3, 'false', '2022-09-15 00:00', 3, 1200, 5, 0, 10, 0, 10, 6), -- Sale AWAITING_APPROVAL
    ('2022-09-15 00:00', 3, 'false', '2022-09-15 00:00', 3, 1200, 2, 0, 10, 0, 11, 6), -- Sale AWAITING_APPROVAL
    ('2022-12-15 00:00', 3, 'false', '2022-12-15 00:00', 3, 600, 4, 0, 10, 0, 13, 7); -- Purchase AWAITING_APPROVAL

