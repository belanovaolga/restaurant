package ru.liga.restaurant.waiter.model;

import jakarta.persistence.*;
import lombok.*;

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
    private WaiterOrder order;
}
