CREATE TABLE springapp.address (
    id BIGSERIAL PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    suite VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    zipcode VARCHAR(255) NOT NULL,
    geo_id BIGINT REFERENCES springapp.geo(id)
); 