create SCHEMA IF NOT EXISTS waiter;
SET search_path TO waiter;

create sequence IF NOT EXISTS waiter_account_seq start 1 INCREMENT BY 1;

create TABLE IF NOT EXISTS waiter.waiter_account(
    waiter_id BIGINT DEFAULT NEXTVAL('waiter_account_seq') NOT NULL PRIMARY KEY,
    name CHARACTER VARYING NOT NULL,
    employment_date TIMESTAMP WITH TIME ZONE NOT NULL,
    sex CHARACTER VARYING NOT NULL
);

create sequence IF NOT EXISTS menu_seq start 1 INCREMENT BY 1;

create TABLE IF NOT EXISTS waiter.menu(
    id BIGINT DEFAULT NEXTVAL('menu_seq') NOT NULL PRIMARY KEY,
    dish_name CHARACTER VARYING NOT NULL,
    dish_cost NUMERIC NOT NULL
);

create sequence IF NOT EXISTS waiter_order_seq start 1 INCREMENT BY 1;

create TABLE IF NOT EXISTS waiter.waiter_order(
    order_no BIGINT DEFAULT NEXTVAL('waiter_order_seq') NOT NULL PRIMARY KEY,
    status CHARACTER VARYING NOT NULL,
    create_date TIMESTAMP WITH TIME ZONE NOT NULL,
    waiter_id BIGINT NOT NULL,
    table_no CHARACTER VARYING NOT NULL,
    CONSTRAINT fk_waiter FOREIGN KEY (waiter_id) REFERENCES waiter_account(waiter_id)
);

create sequence IF NOT EXISTS order_positions_seq start 1 INCREMENT BY 1;

create TABLE IF NOT EXISTS waiter.order_positions(
    composition_id BIGINT DEFAULT NEXTVAL('order_positions_seq') NOT NULL PRIMARY KEY,
    dish_num BIGINT NOT NULL,
    order_no BIGINT NOT NULL,
    menu_position_id BIGINT NOT NULL,
    CONSTRAINT fk_order FOREIGN KEY (order_no) REFERENCES waiter_order(order_no),
    CONSTRAINT fk_menu FOREIGN KEY (menu_position_id) REFERENCES menu(id)
);

create TABLE IF NOT EXISTS waiter.payment(
    order_no BIGINT PRIMARY KEY,
    payment_type CHARACTER VARYING,
    payment_date TIMESTAMP WITH TIME ZONE,
    payment_sum NUMERIC,
    CONSTRAINT fk_payment_order FOREIGN KEY (order_no) REFERENCES waiter_order(order_no)
);
