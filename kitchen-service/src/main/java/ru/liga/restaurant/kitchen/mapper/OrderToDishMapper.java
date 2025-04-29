package ru.liga.restaurant.kitchen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.restaurant.kitchen.model.entity.Dish;
import ru.liga.restaurant.kitchen.model.entity.KitchenOrder;
import ru.liga.restaurant.kitchen.model.entity.OrderToDish;
import ru.liga.restaurant.kitchen.model.request.DishRequest;
import ru.liga.restaurant.kitchen.model.response.DishResponse;
import ru.liga.restaurant.kitchen.model.response.KitchenOrderResponse;
import ru.liga.restaurant.kitchen.model.response.OrderToDishResponse;
import ru.liga.restaurant.kitchen.model.response.OrderToDishResponseForKitchen;

import java.util.List;

/**
 * Маппер для преобразования данных о заказах и блюдах
 */
@Mapper(componentModel = "spring")
public interface OrderToDishMapper {
    /**
     * Собирает полный ответ о заказе и блюдах
     *
     * @param kitchenOrderResponse DTO с информацией о заказе
     * @param dishResponse         список DTO с информацией о блюдах
     * @return комбинированный DTO ответа
     */
    OrderToDishResponse toOrderToDishResponse(KitchenOrderResponse kitchenOrderResponse,
                                              List<DishResponse> dishResponse);

    /**
     * Собирает ответ о заказе для кухни с информацией об блюде
     *
     * @param kitchenOrderResponse DTO с информацией о заказе
     * @param dishResponse DTO с информацией о блюде
     * @return комбинированный DTO ответа для кухни
     */
    OrderToDishResponseForKitchen toOrderToDishResponseForKitchen(KitchenOrderResponse kitchenOrderResponse,
                                                                  DishResponse dishResponse);

    /**
     * Создает сущность с заказом и блюдом
     *
     * @param kitchenOrder сущность заказа
     * @param dish сущность блюда
     * @param dishRequest DTO с запросом на блюдо
     * @return сущность заказа и блюда
     */
    @Mapping(target = "dishesNumber", source = "dishRequest.dishesNumber")
    OrderToDish toOrderToDish(KitchenOrder kitchenOrder, Dish dish, DishRequest dishRequest);
}
