CREATE TABLE springapp.users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(255),
    website VARCHAR(255),
    address_id BIGINT REFERENCES springapp.address(id),
    company_id BIGINT REFERENCES springapp.company(id)
); 