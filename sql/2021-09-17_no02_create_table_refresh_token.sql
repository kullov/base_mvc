DROP TABLE IF EXISTS public."tbl_refresh_token";

CREATE TABLE public."tbl_refresh_token"
(
    id              serial PRIMARY KEY,
    token           character varying(36)      COLLATE pg_catalog."default"    NOT NULL UNIQUE,
    user_id         int                                                        NOT NULL,
    is_active       boolean,
    expiry_date     date                                                       NOT NULL,
    created_time    timestamp with time zone                                   NOT NULL,
    updated_time    timestamp with time zone
);