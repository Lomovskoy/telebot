CREATE TABLE message
(
    id      UUID DEFAULT gen_random_uuid(),
    command VARCHAR(25)  NOT NULL,
    message VARCHAR(500) NOT NULL,
    PRIMARY KEY (id)
);