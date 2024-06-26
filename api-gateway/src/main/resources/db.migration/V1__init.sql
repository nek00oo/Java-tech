CREATE TYPE COLOR AS ENUM
    (
        'WHITE',
        'BLACK',
        'ORANGE',
        'GRAY',
        'BROWN',
        'CREAM',
        'BLUE',
        'CHOCOLATE',
        'LILAC'
        );

CREATE TABLE IF NOT EXISTS Owners
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR NOT NULL,
    birth_date DATE    NOT NULL,
    password   VARCHAR NOT NULL,
    roles      VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS Cats
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR NOT NULL,
    birth_date DATE    NOT NULL,
    breed      VARCHAR NOT NULL,
    color      COLOR NOT NULL,
    owner_id   BIGINT REFERENCES Owners (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS Cat_Friends
(
    cat_id    BIGINT REFERENCES Cats (id) ON DELETE CASCADE,
    friend_id BIGINT REFERENCES Cats (id) ON DELETE CASCADE,
    PRIMARY KEY (cat_id, friend_id)
);

CREATE CAST (character varying as color) WITH INOUT AS IMPLICIT;

--drop table owners, cats, cat_friends;
--drop type COLOR;


