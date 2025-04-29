package ru.liga.restaurant.waiter.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.liga.restaurant.waiter.model.enums.WaiterStatus;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Сущность заказа
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "waiter_order", schema = "waiter")
public class WaiterOrder {
    /**
     * Идентификатор заказа
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "waiter_order_seq")
    @SequenceGenerator(name = "waiter_order_seq", sequenceName = "waiter.waiter_order_seq", allocationSize = 1)
    @Column(name = "order_no")
    private Long orderNo;

    /**
     * Текущий статус заказа
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private WaiterStatus status;

    /**
     * Дата и время создания заказа
     */
    @Column(name = "create_date", nullable = false)
    private ZonedDateTime createDate;

    /**
     * Официант, обслуживающий заказ
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waiter_id", nullable = false)
    private WaiterAccount waiterAccount;

    /**
     * Номер стола
     */
    @Column(name = "table_no", nullable = false)
    private String tableNo;

    /**
     * Информация об оплате заказа
     */
    @OneToOne(mappedBy = "waiterOrder")
    private Payment payment;

    /**
     * Позиции в заказе
     */
    @OneToMany(mappedBy = "waiterOrder", fetch = FetchType.LAZY)
    private List<OrderPositions> positions;
}