CREATE TABLE user_data
(
    chat_id                 BIGINT,
    first_name              VARCHAR(100) NOT NULL,
    last_name               VARCHAR(100) NOT NULL,
    user_name               VARCHAR(100) NOT NULL,
    registered_at           TIMESTAMP,
    subscription_start_date TIMESTAMP,
    subscription_end_date   TIMESTAMP,
    active                  BOOLEAN NOT NULL DEFAULT true,
    PRIMARY KEY (chat_id)
);