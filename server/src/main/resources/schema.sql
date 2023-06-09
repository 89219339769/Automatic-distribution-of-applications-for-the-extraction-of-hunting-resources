--
-- CREATE TABLE IF NOT EXISTS users (
--                                      id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
--                                      name VARCHAR(255) NOT NULL,
--     email VARCHAR(512) NOT NULL,
--     CONSTRAINT pk_user PRIMARY KEY (id),
--     CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
--     );
--
--
-- create table IF NOT EXISTS REQUESTS
-- (
--     id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
--     DESCRIPTION  CHARACTER VARYING(1000) not null,
--     REQUESTOR_ID BIGINT
--     references USERS
--     on delete cascade,
--     CREATED      TIMESTAMP,
--     constraint PK_REQUEST
--     primary key (id)
--     );
--
--
--
-- CREATE TABLE IF NOT EXISTS items (
--
--
--                                      id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
--                                      name VARCHAR(50) NOT NULL,
--     description VARCHAR(1000) NOT NULL,
--     is_available BOOLEAN,
--     id_owner BIGINT REFERENCES users (id) ON DELETE CASCADE NOT NULL,
--     request_id BIGINT REFERENCES requests (id) ON DELETE CASCADE,
--     CONSTRAINT pk_item PRIMARY KEY (id)
--
--
--
--     );
--
-- create table  IF NOT EXISTS bookings
-- (
--     id         bigint generated by default as identity
--     constraint pk_booking
--     primary key,
--     start_date timestamp not null,
--     end_date   timestamp not null,
--     item_id    bigint    not null
--     references items
--     on delete cascade,
--     booker_id  bigint    not null
--     references users
--     on delete cascade,
--     status     varchar(10)
--     );
--
--
-- create table   IF NOT EXISTS comments
-- (
--     id        bigint generated by default as identity
--     constraint pk_comments
--     primary key,
--     text      varchar(1000) not null,
--     item_id   bigint        not null
--     references items
--     on delete cascade,
--     author_id bigint        not null
--     references users
--     on delete cascade,
--     created   timestamp
--     );
CREATE TABLE IF NOT EXISTS resources (

                                         id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                         district VARCHAR(100) NOT NULL,
                                         name    VARCHAR(100)  NOT NULL,
                                         quantity int NOT NULL,
                                         application_status varchar(100) NOT NULL,
                                         CONSTRAINT pk_resources PRIMARY KEY (id)

);


create table IF NOT EXISTS applications
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    full_name CHARACTER VARYING(100)  not null,
    application_type varchar(10) not null,
    start_of_issue  timestamp not null,
    series          integer not null,
    number          integer not null,
    requested_resources_id   bigint        not null
        references resources
            on delete cascade,
    constraint PK_applications primary key (id),
    application_status varchar(100)
);
