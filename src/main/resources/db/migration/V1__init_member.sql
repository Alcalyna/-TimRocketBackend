CREATE TABLE IF NOT EXISTS members(
    id UUID PRIMARY KEY,
    firstname VARCHAR(25) NOT NULL,
    lastname VARCHAR(25) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL,
    company VARCHAR(25),
    role VARCHAR(25) NOT NULL,
    picture_url VARCHAR(100)
);