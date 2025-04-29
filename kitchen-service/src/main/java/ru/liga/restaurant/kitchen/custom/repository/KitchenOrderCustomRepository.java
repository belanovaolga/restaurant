package ru.liga.restaurant.kitchen.custom.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import ru.liga.restaurant.kitchen.model.entity.KitchenOrder;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с заказами на кухне
 * Обеспечивает основные CRUD-операции для сущности KitchenOrder
 */
@Mapper
@Repository
public interface KitchenOrderCustomRepository {
    /**
     * Сохраняет новый заказ
     *
     * @param kitchenOrder заказ для сохранения
     */
    void insert(KitchenOrder kitchenOrder);

    /**
     * Получает все заказы
     *
     * @return список всех заказов
     */
    List<KitchenOrder> findAll();

    /**
     * Находит заказ по идентификатору
     *
     * @param id идентификатор заказа
     * @return Optional с найденным заказом или пустой
     */
    Optional<KitchenOrder> findById(Long id);

    /**
     * Обновляет статус заказа
     *
     * @param kitchenOrder заказ с обновленным статусом
     */
    void updateStatus(KitchenOrder kitchenOrder);

    /**
     * Удаляет заказ
     *
     * @param kitchenOrder заказ для удаления
     */
    void delete(KitchenOrder kitchenOrder);
}