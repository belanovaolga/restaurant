CREATE SCHEMA IF NOT EXISTS kitchen;
SET search_path TO kitchen;

CREATE SEQUENCE IF NOT EXISTS kitchen_order_seq start 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS kitchen.kitchen_order(
    kitchen_order_id BIGINT DEFAULT NEXTVAL('kitchen_order_seq') NOT NULL PRIMARY KEY,
    waiter_order_no BIGINT NOT NULL,
    status CHARACTER VARYING NOT NULL,
    create_date TIMESTAMP WITH TIME ZONE NOT NULL
);
