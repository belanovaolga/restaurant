package ru.liga.restaurant.waiter.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Сущность аккаунта официанта
 */
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "waiter_account", schema = "waiter")
public class WaiterAccount {
    /**
     * Уникальный идентификатор официанта
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "waiter_account_seq")
    @SequenceGenerator(name = "waiter_account_seq", sequenceName = "waiter.waiter_account_seq", allocationSize = 1)
    @Column(name = "waiter_id")
    private Long waiterId;

    /**
     * Имя официанта
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Дата приема на работу
     */
    @Column(name = "employment_date", nullable = false)
    private ZonedDateTime employmentDate;

    /**
     * Пол официанта
     */
    @Column(name = "sex", nullable = false)
    private String sex;

    /**
     * Список заказов, обслуживаемых официантом
     */
    @OneToMany(mappedBy = "waiterAccount", fetch = FetchType.LAZY)
    private List<WaiterOrder> orders;
}
