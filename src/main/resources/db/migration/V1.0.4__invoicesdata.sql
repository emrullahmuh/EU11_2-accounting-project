insert into invoices(insert_date_time, insert_user_id, is_deleted, last_update_date_time, last_update_user_id,
                     date,invoice_no, invoice_type, invoice_status, client_vendor_id, company_id)
values
-- COMPANY-2 / Green Tech
('2022-09-09 00:00', 2, 'false', '2022-09-09 00:00', 2, '2022-09-09', 'P-001', 'PURCHASE', 'APPROVED', 3, 2),
('2022-09-10 00:00', 2, 'false', '2022-09-10 00:00', 2, '2022-09-10', 'P-002', 'PURCHASE', 'APPROVED', 4, 2),
('2022-09-17 00:00', 2, 'false', '2022-09-17 00:00', 2, '2022-09-17', 'S-001', 'SALES', 'APPROVED', 1, 2),
('2022-10-19 00:00', 2, 'false', '2022-10-19 00:00', 2, '2022-10-19', 'S-002', 'SALES', 'AWAITING_APPROVAL', 2, 2),
('2022-11-20 00:00', 2, 'false', '2022-11-20 00:00', 2, '2022-11-20', 'S-003', 'SALES', 'AWAITING_APPROVAL', 2, 2),

-- COMPANY-3 / Blue Tech
('2022-09-09 00:00', 3, 'false', '2022-09-09 00:00', 3, '2022-09-09', 'P-001', 'PURCHASE', 'APPROVED', 6, 3),
('2022-09-10 00:00', 3, 'false', '2022-09-10 00:00', 3, '2022-09-10', 'P-002', 'PURCHASE', 'APPROVED', 6, 3),
('2022-09-13 00:00', 3, 'false', '2022-09-13 00:00', 3, '2022-09-13', 'S-001', 'SALES', 'APPROVED', 5, 3),
('2022-11-18 00:00', 3, 'false', '2022-11-18 00:00', 3, '2022-11-18', 'S-002', 'SALES', 'AWAITING_APPROVAL', 5, 3),
('2022-11-19 00:00', 3, 'false', '2022-11-19 00:00', 3, '2022-11-19', 'S-003', 'SALES', 'AWAITING_APPROVAL', 7, 3),
('2022-11-20 00:00', 3, 'false', '2022-11-20 00:00', 3, '2022-11-20', 'S-004', 'SALES', 'AWAITING_APPROVAL', 7, 3),
('2022-11-21 00:00', 3, 'false', '2022-11-21 00:00', 3, '2022-11-21', 'S-005', 'SALES', 'AWAITING_APPROVAL', 7, 3),
('2022-12-15 00:00', 3, 'false', '2022-12-15 00:00', 3, '2022-12-15', 'P-003', 'PURCHASE', 'AWAITING_APPROVAL', 8, 3);