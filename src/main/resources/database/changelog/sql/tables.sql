-- liquibase formatted sql

-- changeset akl_group5:1

CREATE TABLE IF NOT EXISTS guest
(
    id		BIGSERIAL PRIMARY KEY,
    chat_id	BIGINT,
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
    chat_id BIGINT,
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
    adopter_id	BIGSERIAL,
    report_date DATE,
    photo BYTEA,
    diet VARCHAR,
    life_status VARCHAR,
    behavior VARCHAR
);

-- changeset akl_group5:5

CREATE TABLE IF NOT EXISTS branch_params
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR,
    country VARCHAR,
    city VARCHAR,
    zip VARCHAR,
    address VARCHAR,
    work_hours VARCHAR,
    map BYTEA,
    info TEXT
);

CREATE TABLE IF NOT EXISTS pet
(
    id          BIGSERIAL PRIMARY KEY,
    nick_name   VARCHAR,
    pet_type    INT,
    pet_color   INT,
    avatar_id BIGINT,
    adopter_id	BIGINT

);

CREATE TABLE service_level2 (
                              branch_id BIGSERIAL,
                              meet_pet TEXT,
                              adoption_docs TEXT,
                              transport_pet TEXT,
                              house_puppy TEXT,
                              house_big_dog TEXT,
                              house_handicapped TEXT,
                              advice_specialist TEXT,
                              contacts_specialist TEXT,
                              refusal_reasons TEXT
);

CREATE TABLE IF NOT EXISTS pet_avatar
(
    id          BIGSERIAL PRIMARY KEY,
    file_path   TEXT,
    file_size    BIGINT,
    media_type   TEXT,
    data BYTEA

);

