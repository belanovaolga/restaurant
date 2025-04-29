package ru.liga.restaurant.waiter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.restaurant.waiter.model.entity.OrderPositions;

/**
 * Репозиторий для работы с позициями заказов
 * Предоставляет основные CRUD-операции для сущности {@link OrderPositions}
 */
@Repository
public interface OrderPositionsRepository extends JpaRepository<OrderPositions, Long> {
}
