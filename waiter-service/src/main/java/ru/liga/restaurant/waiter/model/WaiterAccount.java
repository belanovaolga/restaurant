package ru.liga.restaurant.waiter.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "waiter_account", schema = "waiter")
public class WaiterAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "waiter_account_seq")
    @SequenceGenerator(name = "waiter_account_seq", sequenceName = "waiter_account_seq", allocationSize = 1)
    @Column(name = "waiter_id")
    private Long waiterId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "employment_date", nullable = false)
    private ZonedDateTime employmentDate;

    @Column(name = "sex", nullable = false)
    private String sex;

    @OneToMany(mappedBy = "waiterId", fetch = FetchType.LAZY)
    private List<WaiterOrder> orders;
}
