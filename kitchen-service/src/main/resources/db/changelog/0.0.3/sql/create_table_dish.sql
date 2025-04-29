CREATE SEQUENCE IF NOT EXISTS dish_seq start 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS kitchen.dish(
    dish_id BIGINT DEFAULT NEXTVAL('dish_seq') NOT NULL PRIMARY KEY,
    balance BIGINT NOT NULL,
    short_name CHARACTER VARYING NOT NULL,
    dish_composition CHARACTER VARYING NOT NULL
);

COMMENT ON TABLE kitchen.dish IS 'Хранение информации о блюдах и их составе';
COMMENT ON COLUMN kitchen.dish.dish_id IS 'Уникальный идентификатор блюда';
COMMENT ON COLUMN kitchen.dish.balance IS 'Доступное количество блюд на складе';
COMMENT ON COLUMN kitchen.dish.short_name IS 'Краткое название блюда';
COMMENT ON COLUMN kitchen.dish.dish_composition IS 'Состав и ингредиенты блюда';