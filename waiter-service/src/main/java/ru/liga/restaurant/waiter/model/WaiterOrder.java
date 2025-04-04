package ru.liga.restaurant.waiter.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.liga.restaurant.waiter.model.enums.Status;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "waiter_order", schema = "waiter")
public class WaiterOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "waiter_order_seq")
    @SequenceGenerator(name = "waiter_order_seq", sequenceName = "waiter_order_seq", allocationSize = 1)
    @Column(name = "order_no")
    private Long orderNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "create_date", nullable = false)
    private ZonedDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waiter_id", nullable = false)
    private WaiterAccount waiterId;

    @Column(name = "table_no", nullable = false)
    private String tableNo;

    @OneToOne(mappedBy = "order")
    private Payment payment;

    @OneToMany(mappedBy = "orderNo", fetch = FetchType.LAZY)
    private List<OrderPositions> positions;
}
