create table role (
                      id int8 not null,
                      name varchar(255),
                      primary key (id)
);

create table user_entity
(
    id                  bigint not null primary key,
    email               varchar(255),
    password            varchar(255),
    full_name           varchar(255),
    persist_date        timestamp,
    city                varchar(255),
    image_link          varchar(255),
    is_deleted          boolean,
    is_enabled          boolean,
    last_redaction_date timestamp not null,
    link_github         varchar(255),
    link_site           varchar(255),
    link_vk             varchar(255),
    nickname            varchar(255),
    about               varchar(255),
    role_id             bigint not null references role
);

INSERT INTO role (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO role (id, name) VALUES (2, 'ROLE_ADMIN');


INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled,
                                last_redaction_date, link_github, link_site, link_vk, nickname, password, persist_date,
                                role_id)
VALUES (1, 'About example', 'Msk', 'user@mail.ru', 'Novikov', 'https://image', false, true,
        '2022-07-20 18:13:50.000000', 'https://guthub', 'https://site',
        'https://vk', 'alex', 'pass', '2022-07-20 18:14:43.000000', 1);


