package ru.liga.restaurant.waiter.model;

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

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment", schema = "waiter")
public class Payment {
    @Id
    @Column(name = "order_no")
    private Long orderNo;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "payment_date")
    private ZonedDateTime paymentDate;

    @Column(name = "payment_sum")
    private Double paymentSum;

    @OneToOne
    @MapsId
    @JoinColumn(name = "order_no")
    private WaiterOrder waiterOrder;
}
