package ru.liga.restaurant.kitchen.custom.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import ru.liga.restaurant.kitchen.model.entity.OrderToDish;

import java.util.List;

/**
 * Репозиторий для работы с заказами и блюдами
 * Обеспечивает основные CRUD-операции для сущности OrderToDish
 */
@Mapper
@Repository
public interface OrderToDishCustomRepository {
    /**
     * Сохраняет новую сущность
     *
     * @param orderToDish сущность для сохранения
     */
    void insert(OrderToDish orderToDish);

    /**
     * Находит все сущности по идентификатору заказа
     *
     * @param kitchenOrderId идентификатор заказа
     * @return список сущностей по идентификатору заказа
     */
    List<OrderToDish> findByKitchenOrderId(Long kitchenOrderId);

    /**
     * Получает список сущностей с пагинацией
     *
     * @param offset   начальная позиция
     * @param pageSize количество элементов на странице
     * @return список всех сущностей на странице
     */
    List<OrderToDish> findAll(@Param("offset") int offset, @Param("pageSize") int pageSize);
}