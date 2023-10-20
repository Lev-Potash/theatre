DROP Table IF EXISTS users CASCADE;

CREATE TABLE users (
                        id          serial PRIMARY KEY,
                        username    varchar(50) NOT NULL UNIQUE,
                        password    varchar(80) NOT NULL,
                        first_name  varchar(50) NOT NULL,
                        last_name   varchar(50) NOT NULL,
                        email       varchar(50) NOT NULL,
                        phone       varchar(15) NOT NULL
);

DROP TABLE IF EXISTS roles CASCADE;

CREATE TABLE roles (
                       id          serial NOT NULL PRIMARY KEY,
                       name        varchar(50) DEFAULT NULL
);


DROP TABLE IF EXISTS users_roles CASCADE;

CREATE TABLE users_roles (
                       user_id          int NOT NULL,
                       role_id          int NOT NULL,
                       PRIMARY KEY (user_id, role_id),

                       CONSTRAINT FK_USER_ID FOREIGN KEY (user_id)
                       REFERENCES users(id)
                       ON DELETE NO ACTION ON UPDATE NO ACTION,

                       CONSTRAINT FK_ROLE_ID FOREIGN KEY (role_id)
                       REFERENCES roles(id)
                       ON DELETE NO ACTION ON UPDATE NO ACTION
);


INSERT INTO roles (name)
VALUES
('ROLE_USER'), ('ROLE_ADMIN');

INSERT INTO users(username, password, first_name, last_name, email, phone)
VALUES
('admin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'Admin', 'Admin', 'admin@gmail.com', '+79002554139');

-- password: 100

INSERT INTO users_roles (user_id, role_id)
VALUES
(1,1),
(1,2);
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
