package ru.liga.restaurant.waiter.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "menu", schema = "waiter")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_seq")
    @SequenceGenerator(name = "menu_seq", sequenceName = "menu_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "dish_name", nullable = false)
    private String dishName;

    @Column(name = "dish_cost", nullable = false)
    private Double dishCost;

    @OneToMany(mappedBy = "menuPositionId")
    private List<OrderPositions> orderPositions;
}
