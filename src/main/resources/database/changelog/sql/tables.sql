-- liquibase formatted sql

-- changeset akl_group5:1

CREATE TABLE IF NOT EXISTS guest
(
    id		BIGSERIAL PRIMARY KEY,
    telegram_id	VARCHAR,
    last_visit 	TIMESTAMP,
    last_menu	INT
);

-- changeset akl_group5:2

CREATE TABLE IF NOT EXISTS adopter
(
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR,
    last_name VARCHAR,
    passport VARCHAR,
    age INT,
    phone1 VARCHAR,
    email VARCHAR,
    telegram VARCHAR,
    volunteer_id INT,
    on_probation BOOL,
    active BOOL
);

-- changeset akl_group5:3

CREATE TABLE IF NOT EXISTS volunteer
(
    id	BIGSERIAL PRIMARY KEY,
    name VARCHAR,
    telegram_id VARCHAR,
    photo BYTEA
);

-- changeset akl_group5:4

CREATE TABLE IF NOT EXISTS pet_report
(
    id BIGSERIAL PRIMARY KEY,
    adopter_id	BIGINT,
    report_date TIMESTAMP,
    pet_id INT,
    photo BYTEA,
    diet VARCHAR,
    life_status VARCHAR,
    behavior VARCHAR
);

-- changeset akl_group5:5

CREATE TABLE IF NOT EXISTS branch_params
(
    id INT PRIMARY KEY,
    name VARCHAR,
    country VARCHAR,
    city VARCHAR,
    zip VARCHAR,
    address VARCHAR,
    work_hours VARCHAR,
    map BYTEA,
    info TEXT,
    prob_period INT,
    prob_extend INT
);

CREATE TABLE IF NOT EXISTS pet
(
    id          BIGSERIAL PRIMARY KEY,
    nick_name   VARCHAR,
    pet_type    INT,
    pet_color   INT,
    photo       BYTEA,
    adopter_id	BIGINT

)

