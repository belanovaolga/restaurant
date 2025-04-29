package ru.liga.restaurant.waiter.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

/**
 * Сущность платежа
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment", schema = "waiter")
public class Payment {
    /**
     * Идентификатор заказа
     */
    @Id
    @Column(name = "order_no")
    private Long orderNo;

    /**
     * Тип платежа
     */
    @Column(name = "payment_type")
    private String paymentType;

    /**
     * Дата и время платежа
     */
    @Column(name = "payment_date")
    private ZonedDateTime paymentDate;

    /**
     * Сумма
     */
    @Column(name = "payment_sum")
    private Double paymentSum;

    /**
     * Связанный заказ
     */
    @OneToOne
    @MapsId
    @JoinColumn(name = "order_no")
    private WaiterOrder waiterOrder;
}
