-- DROP IF EXISTS Table product;

CREATE TABLE product(
                        id serial Primary KEY,
                        title TEXT NOT NULL UNIQUE,
                        price int NOT NULL
);

-- Create INDEX product_title_idx

INSERT into product (title, price)
values ('Product1', 40),
       ('Product2', 90),
       ('Product3', 200),
       ('Product4', 210),
       ('Product5', 50),
       ('Product6', 70),
       ('Product7', 90),
       ('Product8', 220),
       ('Product9', 150),
       ('Product10', 135),
       ('Product11', 210),
       ('Product12', 50),
       ('Product13', 70),
       ('Product14', 210),
       ('Product15', 50),
       ('Product16', 70),
       ('Product17', 90),
       ('Product18', 220),
       ('Product19', 150),
       ('Product20', 135),
       ('Product21', 210),
       ('Product22', 50),
       ('Product23', 70),
       ('Product24', 210),
       ('Product25', 50),
       ('Product26', 70),
       ('Product27', 90),
       ('Product28', 220),
       ('Product29', 150),
       ('Product30', 135),
       ('Product31', 210),
       ('Product32', 50),
       ('Product33', 70),
       ('Product34', 210),
       ('Product35', 50),
       ('Product36', 70),
       ('Product37', 90),
       ('Product38', 220),
       ('Product39', 150),
       ('Product40', 135),
       ('Product41', 210),
       ('Product42', 50),
       ('Product43', 70),
       ('Product44', 210),
       ('Product45', 50),
       ('Product46', 70),
       ('Product47', 90),
       ('Product48', 220),
       ('Product49', 150),
       ('Product50', 135);


Alter table product
    add column isEditable BOOLEAN DEFAULT (False);


-- CREATE TABLE products (id serial, title varchar(100), price int);
--
-- INSERT INTO products (title, price) VALUES ('Bread', 40), ('Milk', 80);
--
-- CREATE TABLE users (
--     username VARCHAR(50) NOT NULL,
--     password VARCHAR(100) NOT NULL,
--     enabled boolean NOT NULL,
--     PRIMARY KEY (username)
-- );
--
-- INSERT INTO users
-- VALUES
-- ('user1', '{noop}123', true),
-- ('user2', '{noop}123', true);
--
-- CREATE TABLE authorities (
--     username varchar(50) NOT NULL,
--     authority varchar(50) NOT NULL,
--
--     CONSTRAINT authorities_idx UNIQUE (username, authority),
--
--     CONSTRAINT authorities_ibfk_1
--     FOREIGN KEY (username)
--     REFERENCES users (username)
-- );
--
-- INSERT INTO authorities
-- VALUES
-- ('user1', 'ROLE_ADMIN'),
-- ('user1', 'ROLE_USER'),
-- ('user2', 'ROLE_USER');