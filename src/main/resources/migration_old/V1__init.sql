-- DROP Table IF EXISTS product;

CREATE TABLE client(
                       id serial Primary KEY,
                       name TEXT NOT NULL UNIQUE,
                       surname TEXT NOT NULL,
                       email TEXT NOT NULL UNIQUE
);

-- Create INDEX product_title_idx

INSERT into client (name, surname, email)
values ('Alex', 'Frolov', 'lex@ru.ru'),
       ('Rex', 'Stepanov', 'rex@ru.ru'),
       ('Max', 'Gorbunov', 'gorb@ru.ru'),
       ('Oleg', 'Gorin', 'ol@ru.ru');

-- CREATE TABLE theatre (
--                     id serial PRIMARY KEY,
--                     theatre_name TEXT NOT NULL
-- );
--
-- INSERT into theatre (theatre_name)
-- values ('Вестник'),
--        ('Сатира'),
--        ('Малый театр'),
--        ('Большой театр');
--
-- CREATE TABLE performance (
--                              id serial PRIMARY KEY,
--                              performance_name TEXT NOT NULL
-- );
--
-- INSERT into performance (performance_name)
-- values ('Горе от ума'),
--        ('Гамлет'),
--        ('Вишневый сад'),
--        ('Не все коту масленница'),
--        ('Ромео и Джулиета'),
--        ('Большой перполох');
--
-- CREATE TABLE place(
--                        id serial Primary KEY,
--                        row int NOT NULL,
--                        place int NOT NULL
-- );
--
-- -- Create INDEX product_title_idx
--
-- INSERT into place (row, place)
-- values (1, 1),
--        (1, 2),
--        (1, 3),
--        (1, 4),
--        (1, 5),
--        (2, 1),
--        (2, 2),
--        (2, 3),
--        (2, 4),
--        (2, 5),
--        (3, 1),
--        (3, 2),
--        (3, 3),
--        (3, 4),
--        (3, 5);
--
--
-- CREATE TABLE Place_Theatre (
--                       id serial Primary KEY,
--                       theatre_id int NOT NULL,
--                       place_id int NOT NULL,
--                       FOREIGN KEY (theatre_id) REFERENCES theatre (id),
--                       FOREIGN KEY (place_id) REFERENCES place (id)
-- );
--
--
-- INSERT into Place_Theatre (theatre_id, place_id)
-- values (1, 1),
--        (1, 2),
--        (1, 3),
--        (1, 4);
--
-- CREATE TABLE Theatre_Performance (
--                                id serial Primary KEY,
--                                theatre_id int NOT NULL,
--                                performance_id int NOT NULL,
--                                FOREIGN KEY (theatre_id) REFERENCES theatre (id),
--                                FOREIGN KEY (performance_id) REFERENCES performance (id)
-- );
--
--
-- INSERT into Theatre_Performance (theatre_id, performance_id)
-- values (1, 1),
--        (1, 2),
--        (1, 3);
--
--
-- CREATE TABLE Schedule (
--                                      id serial Primary KEY,
--                                      theatre_performance_id int NOT NULL,
--                                      performance_date date NOT NULL,
--                                      FOREIGN KEY (theatre_performance_id) REFERENCES Theatre_Performance (id)
-- );
--
--
-- INSERT into Schedule (theatre_performance_id, performance_date)
-- values (1, '2022.07.01'),
--        (2, '2022.07.03'),
--        (2, '2023.06.10'),
--        (1, '2023.06.10');
--
--
-- CREATE TABLE Seating (
--                           id serial Primary KEY,
--                           schedule_id int NOT NULL,
--                           place_theatre_id int NOT NULL,
--                           isOccupied boolean NOT NULL,
--                           FOREIGN KEY (schedule_id) REFERENCES Schedule (id),
--                           FOREIGN KEY (place_theatre_id) REFERENCES Place_Theatre (id)
-- );
--
--
-- INSERT into Seating (schedule_id, place_theatre_id, isOccupied)
-- values (1, 1, false),
--        (2, 2, false),
--        (3, 3, true),
--        (4, 4, true);
--
-- CREATE TABLE Ticket (
--                          id serial Primary KEY,
--                          client_id int NOT NULL,
--                          schedule_id int NOT NULL,
--                          place_theatre_id int NOT NULL,
--                          cost int NOT NULL,
--                          FOREIGN KEY (client_id) REFERENCES client (id),
--                          FOREIGN KEY (schedule_id) REFERENCES Schedule (id),
--                          FOREIGN KEY (place_theatre_id) REFERENCES Place_Theatre (id)
-- );
--
--
--
-- INSERT into Ticket (client_id, schedule_id, place_theatre_id, cost)
-- values (1, 1, 1, 350),
--        (2, 2, 2, 350),
--        (3, 3, 3, 350),
--        (4, 3, 4, 350);














-- Alter table product
--     add column isEditable BOOLEAN DEFAULT (False);


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