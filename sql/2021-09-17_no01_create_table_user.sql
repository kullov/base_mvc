DROP TABLE IF EXISTS public."tbl_user";

CREATE TABLE public."tbl_user"
(
    id               serial PRIMARY KEY,
    username         character varying(15)       COLLATE pg_catalog."default"   NOT NULL,
    password         text                        COLLATE pg_catalog."default"   NOT NULL,
    authority        character varying(10)       COLLATE pg_catalog."default"   NOT NULL DEFAULT 'USER',
    first_name       character varying(15)       COLLATE pg_catalog."default",
    last_name        character varying(15)       COLLATE pg_catalog."default",
    address          character varying(250)      COLLATE pg_catalog."default",
    phone            character varying(15)       COLLATE pg_catalog."default",
    mail             character varying(50)       COLLATE pg_catalog."default",
    language         character varying(10)       COLLATE pg_catalog."default"   NOT NULL DEFAULT 'English',
    is_deleted       boolean                                                    NOT NULL DEFAULT false,
    last_login_time  timestamp without time zone,
    created_time     timestamp with time zone                                   NOT NULL,
    updated_time     timestamp with time zone
);