create SCHEMA IF NOT EXISTS waiter;
SET search_path TO waiter;

create sequence IF NOT EXISTS waiter_account_seq start 1 INCREMENT BY 1;

create TABLE IF NOT EXISTS waiter.waiter_account(
    waiter_id BIGINT DEFAULT NEXTVAL('waiter_account_seq') NOT NULL PRIMARY KEY,
    name CHARACTER VARYING NOT NULL,
    employment_date TIMESTAMP WITH TIME ZONE NOT NULL,
    sex CHARACTER VARYING NOT NULL
);

COMMENT ON TABLE waiter.waiter_account IS 'Хранение информации об официантах';
COMMENT ON COLUMN waiter.waiter_account.waiter_id IS 'Уникальный идентификатор официанта';
COMMENT ON COLUMN waiter.waiter_account.name IS 'Полное имя официанта';
COMMENT ON COLUMN waiter.waiter_account.employment_date IS 'Дата приема на работу';
COMMENT ON COLUMN waiter.waiter_account.sex IS 'Пол официанта';

create sequence IF NOT EXISTS menu_seq start 1 INCREMENT BY 1;

create TABLE IF NOT EXISTS waiter.menu(
    id BIGINT DEFAULT NEXTVAL('menu_seq') NOT NULL PRIMARY KEY,
    dish_name CHARACTER VARYING NOT NULL,
    dish_cost NUMERIC NOT NULL
);

COMMENT ON TABLE waiter.menu IS 'Хранение меню ресторана';
COMMENT ON COLUMN waiter.menu.id IS 'Уникальный идентификатор блюда';
COMMENT ON COLUMN waiter.menu.dish_name IS 'Название блюда';
COMMENT ON COLUMN waiter.menu.dish_cost IS 'Цена блюда';

create sequence IF NOT EXISTS waiter_order_seq start 1 INCREMENT BY 1;

create TABLE IF NOT EXISTS waiter.waiter_order(
    order_no BIGINT DEFAULT NEXTVAL('waiter_order_seq') NOT NULL PRIMARY KEY,
    status CHARACTER VARYING NOT NULL,
    create_date TIMESTAMP WITH TIME ZONE NOT NULL,
    waiter_id BIGINT NOT NULL,
    table_no CHARACTER VARYING NOT NULL,
    CONSTRAINT fk_waiter FOREIGN KEY (waiter_id) REFERENCES waiter_account(waiter_id)
);

COMMENT ON TABLE waiter.waiter_order IS 'Хранение информации о заказах клиентов';
COMMENT ON COLUMN waiter.waiter_order.order_no IS 'Уникальный номер заказа';
COMMENT ON COLUMN waiter.waiter_order.status IS 'Текущий статус заказа';
COMMENT ON COLUMN waiter.waiter_order.create_date IS 'Дата создания заказа';
COMMENT ON COLUMN waiter.waiter_order.waiter_id IS 'Ссылка на официанта';
COMMENT ON COLUMN waiter.waiter_order.table_no IS 'Номер столика';

create sequence IF NOT EXISTS order_positions_seq start 1 INCREMENT BY 1;

create TABLE IF NOT EXISTS waiter.order_positions(
    composition_id BIGINT DEFAULT NEXTVAL('order_positions_seq') NOT NULL PRIMARY KEY,
    dish_num BIGINT NOT NULL,
    order_no BIGINT NOT NULL,
    menu_position_id BIGINT NOT NULL,
    CONSTRAINT fk_order FOREIGN KEY (order_no) REFERENCES waiter_order(order_no),
    CONSTRAINT fk_menu FOREIGN KEY (menu_position_id) REFERENCES menu(id)
);

COMMENT ON TABLE waiter.order_positions IS 'Хранение отдельных блюд в составе заказа';
COMMENT ON COLUMN waiter.order_positions.composition_id IS 'Уникальный идентификатор позиции';
COMMENT ON COLUMN waiter.order_positions.dish_num IS 'Количество заказанных блюд';
COMMENT ON COLUMN waiter.order_positions.order_no IS 'Ссылка на заказ';
COMMENT ON COLUMN waiter.order_positions.menu_position_id IS 'Ссылка на блюдо из меню';

create TABLE IF NOT EXISTS waiter.payment(
    order_no BIGINT PRIMARY KEY,
    payment_type CHARACTER VARYING,
    payment_date TIMESTAMP WITH TIME ZONE,
    payment_sum NUMERIC,
    CONSTRAINT fk_payment_order FOREIGN KEY (order_no) REFERENCES waiter_order(order_no)
);

COMMENT ON TABLE waiter.payment IS 'Хранение информации об оплате заказов';
COMMENT ON COLUMN waiter.payment.order_no IS 'Ссылка на заказ';
COMMENT ON COLUMN waiter.payment.payment_type IS 'Способ оплаты';
COMMENT ON COLUMN waiter.payment.payment_date IS 'Дата оплаты';
COMMENT ON COLUMN waiter.payment.payment_sum IS 'Сумма оплаты';
