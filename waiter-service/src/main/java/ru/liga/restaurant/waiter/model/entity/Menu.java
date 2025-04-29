package ru.liga.restaurant.waiter.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Сущность меню
 */
@Entity
@Getter
@Setter
@Table(name = "menu", schema = "waiter")
public class Menu {
    /**
     * Уникальный идентификатор блюда
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_seq")
    @SequenceGenerator(name = "menu_seq", sequenceName = "waiter.menu_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    /**
     * Название блюда
     */
    @Column(name = "dish_name", nullable = false)
    private String dishName;

    /**
     * Стоимость блюда
     */
    @Column(name = "dish_cost", nullable = false)
    private Double dishCost;

    /**
     * Список позиций заказов, содержащих это блюдо
     */
    @OneToMany(mappedBy = "menu")
    private List<OrderPositions> orderPositions;
}
