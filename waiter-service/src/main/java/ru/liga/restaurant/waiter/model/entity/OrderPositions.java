package ru.liga.restaurant.waiter.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Сущность позиции в заказе
 */
@Entity
@Getter
@Setter
@Table(name = "order_positions", schema = "waiter")
public class OrderPositions {
    /**
     * Уникальный идентификатор позиции
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_positions_seq")
    @SequenceGenerator(name = "order_positions_seq", sequenceName = "waiter.order_positions_seq", allocationSize = 1)
    @Column(name = "composition_id")
    private Long compositionId;

    /**
     * Количество данного блюда в заказе
     */
    @Column(name = "dish_num", nullable = false)
    private Long dishNum;

    /**
     * Заказ, к которому относится позиция
     */
    @ManyToOne
    @JoinColumn(name = "order_no", nullable = false)
    private WaiterOrder waiterOrder;

    /**
     * Блюдо из меню
     */
    @ManyToOne
    @JoinColumn(name = "menu_position_id", nullable = false)
    private Menu menu;
}
