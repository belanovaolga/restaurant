package ru.liga.restaurant.waiter.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "order_positions", schema = "waiter")
public class OrderPositions {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_positions_seq")
    @SequenceGenerator(name = "order_positions_seq", sequenceName = "order_positions_seq", allocationSize = 1)
    @Column(name = "composition_id")
    private Long compositionId;

    @Column(name = "dish_num", nullable = false)
    private Long dishNum;

    @ManyToOne
    @JoinColumn(name = "order_no", nullable = false)
    private WaiterOrder orderNo;

    @ManyToOne
    @JoinColumn(name = "menu_position_id", nullable = false)
    private Menu menuPositionId;
}
