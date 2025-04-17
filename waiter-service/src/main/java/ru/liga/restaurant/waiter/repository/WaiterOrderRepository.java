package ru.liga.restaurant.waiter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.liga.restaurant.waiter.model.WaiterOrder;
import ru.liga.restaurant.waiter.model.enums.WaiterStatus;

@Repository
public interface WaiterOrderRepository extends JpaRepository<WaiterOrder, Long> {

    @Modifying
    @Query("UPDATE WaiterOrder w SET w.status = :newStatus WHERE w.orderNo = :orderNo")
    void updateStatus(@Param("orderNo") Long orderNo, @Param("newStatus") WaiterStatus newStatus);
}
