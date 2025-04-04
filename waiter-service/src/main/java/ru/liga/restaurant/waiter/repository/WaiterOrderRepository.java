package ru.liga.restaurant.waiter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.restaurant.waiter.model.WaiterOrder;

@Repository
public interface WaiterOrderRepository extends JpaRepository<WaiterOrder, Long> {
}
