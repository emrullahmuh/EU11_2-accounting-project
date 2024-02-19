--db/migration/V1__create_invoice_summary_view.sql


CREATE OR REPLACE VIEW report AS
SELECT EXTRACT(YEAR FROM ip.insert_date_time)  AS year,
       EXTRACT(MONTH FROM ip.insert_date_time) AS month,
       i.company_id                            AS company_id,
       i.invoice_type                          AS invoice_type,
       SUM(ip.price)                           AS total_price,
       SUM(ip.profit_loss)                     AS total_profit_loss
FROM invoice_products ip
         join invoices i on i.id = ip.invoice_id
GROUP BY EXTRACT(YEAR FROM ip.insert_date_time),
         EXTRACT(MONTH FROM ip.insert_date_time),
         i.company_id,
         i.invoice_type;
