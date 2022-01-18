CREATE TABLE IF NOT EXISTS MEMBER(
    member_id UUID PRIMARY KEY,
    firstname VARCHAR(25) NOT NULL,
    lastname VARCHAR(25) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(64) NOT NULL,
    company VARCHAR(25),
    role VARCHAR(25) NOT NULL
);