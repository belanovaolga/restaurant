CREATE TABLE IF NOT EXISTS kitchen.order_to_dish(
    kitchen_order_id BIGINT NOT NULL,
    dish_id BIGINT NOT NULL,
    dishes_number BIGINT NOT NULL,
    PRIMARY KEY (kitchen_order_id, dish_id),
    CONSTRAINT fk_kitchen_order FOREIGN KEY (kitchen_order_id) REFERENCES kitchen_order(kitchen_order_id),
    CONSTRAINT fk_dish FOREIGN KEY (dish_id) REFERENCES dish(dish_id)
);