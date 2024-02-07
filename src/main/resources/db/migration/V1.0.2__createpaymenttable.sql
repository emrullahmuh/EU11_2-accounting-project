-- Payment Table
CREATE TABLE payments (
                          id BIGSERIAL PRIMARY KEY,
                          insert_date_time TIMESTAMP NOT NULL,
                          insert_user_id BIGINT NOT NULL,
                          last_update_date_time TIMESTAMP NOT NULL,
                          last_update_user_id BIGINT NOT NULL,
                          is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
                          is_paid BOOLEAN NOT NULL DEFAULT FALSE,
                          year INT NOT NULL,
                          amount DECIMAL(10, 2) NOT NULL,
                          payment_date DATE,
                          company_stripe_id VARCHAR(150) NOT NULL,
                          month VARCHAR(50) NOT NULL,
                          company_id BIGINT
);