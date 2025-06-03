CREATE TABLE springapp.company (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    catch_phrase VARCHAR(255),
    bs VARCHAR(255)
); 