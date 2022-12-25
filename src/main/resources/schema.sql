DELETE  FROM USERS;
CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                    name VARCHAR(255) NOT NULL,
    UNIQUE email VARCHAR(512) NOT NULL,
                                    CONSTRAINT pk_user PRIMARY KEY (id)
                                   CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);


ALTER TABLE users ALTER COLUMN ID RESTART WITH 1;



DELETE  FROM items;
CREATE TABLE IF NOT EXISTS items (
                                     id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                     name VARCHAR(50) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    is_available BOOLEAN,
    id_owner BIGINT REFERENCES users (id) ON DELETE CASCADE NOT NULL,
    request_id BIGINT REFERENCES requests (id) ON DELETE CASCADE,
    CONSTRAINT pk_item PRIMARY KEY (id)
    );

ALTER TABLE items ALTER COLUMN ID RESTART WITH 1;



DELETE  FROM bookings;
CREATE TABLE IF NOT EXISTS bookings (
                                        id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                        start_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                        end_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                        item_id BIGINT REFERENCES items (id) ON DELETE CASCADE NOT NULL,
    booker_id BIGINT REFERENCES users(id) ON DELETE CASCADE NOT NULL,
    status VARCHAR(10),
    CONSTRAINT pk_booking PRIMARY KEY (id)
    );

ALTER TABLE bookings ALTER COLUMN ID RESTART WITH 1;





DELETE  FROM comments;
CREATE TABLE IF NOT EXISTS comments (
                                        id INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                        text_comment varchar (455) NOT NULL,
    item_id INTEGER NOT NULL,
    author_id INTEGER NOT NULL,
    CONSTRAINT pk_comments_id PRIMARY KEY (id),
    CONSTRAINT fk_items_id FOREIGN KEY (item_id) REFERENCES items (id),
    CONSTRAINT fk_author_id FOREIGN KEY (author_id) REFERENCES users (id)
    );

ALTER TABLE comments ALTER COLUMN ID RESTART WITH 1;