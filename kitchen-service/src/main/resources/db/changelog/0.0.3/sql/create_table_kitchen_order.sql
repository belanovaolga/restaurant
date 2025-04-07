CREATE SCHEMA IF NOT EXISTS kitchen;
SET search_path TO kitchen;

CREATE SEQUENCE IF NOT EXISTS kitchen_order_seq start 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS kitchen.kitchen_order(
    kitchen_order_id BIGINT DEFAULT NEXTVAL('kitchen_order_seq') NOT NULL PRIMARY KEY,
    waiter_order_no BIGINT NOT NULL,
    status CHARACTER VARYING NOT NULL,
    create_date TIMESTAMP WITH TIME ZONE NOT NULL
);

COMMENT ON TABLE kitchen.kitchen_order IS 'Хранение заказов для кухни';
COMMENT ON COLUMN kitchen.kitchen_order.kitchen_order_id IS 'Уникальный идентификатор заказа на кухне';
COMMENT ON COLUMN kitchen.kitchen_order.waiter_order_no IS 'Ссылка на заказ официанта';
COMMENT ON COLUMN kitchen.kitchen_order.status IS 'Текущий статус приготовления';
COMMENT ON COLUMN kitchen.kitchen_order.create_date IS 'Дата и время создания заказа на кухне';
