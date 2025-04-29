package ru.liga.restaurant.kitchen.custom.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import ru.liga.restaurant.kitchen.model.entity.Dish;

import java.util.Optional;

/**
 * Репозиторий для работы с блюдами в базе данных
 * Обеспечивает основные CRUD-операции для сущности Dish
 */
@Mapper
@Repository
public interface DishCustomRepository {
    /**
     * Сохраняет новое блюдо
     *
     * @param dish блюдо для сохранения
     */
    void insert(Dish dish);

    /**
     * Находит блюдо по идентификатору
     *
     * @param id идентификатор блюда
     * @return Optional с блюдом или пустой, если блюдо не найдено
     */
    Optional<Dish> findById(Long id);

    /**
     * Обновляет информацию о блюде
     *
     * @param dish блюдо с обновленными данными
     */
    void update(Dish dish);
}
