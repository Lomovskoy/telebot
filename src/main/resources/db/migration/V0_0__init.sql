CREATE TABLE menu_answer
(
    id      UUID DEFAULT gen_random_uuid(),
    command VARCHAR(25)  NOT NULL,
    message VARCHAR(500) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_data
(
    chat_id            BIGINT,
    first_name    VARCHAR(100) NOT NULL,
    last_name     VARCHAR(100) NOT NULL,
    user_name     VARCHAR(100) NOT NULL,
    registered_at TIMESTAMP,
    PRIMARY KEY (chat_id)
);