package ru.liga.restaurant.waiter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.restaurant.waiter.model.entity.Menu;

/**
 * Репозиторий для работы с меню
 * Предоставляет CRUD-операции для сущности {@link Menu}
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
