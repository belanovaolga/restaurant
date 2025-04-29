package ru.liga.restaurant.waiter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.liga.restaurant.waiter.model.entity.WaiterOrder;
import ru.liga.restaurant.waiter.model.enums.WaiterStatus;

import java.util.Optional;

/**
 * Репозиторий для работы с заказами
 * Предоставляет основные CRUD-операции для работы с сущностью {@link WaiterOrder}
 */
@Repository
public interface WaiterOrderRepository extends JpaRepository<WaiterOrder, Long> {

    /**
     * Обновляет статус заказа по идентификатору
     *
     * @param orderNo   идентификатор заказа
     * @param newStatus новый статус заказа
     */
    @Modifying
    @Query("UPDATE WaiterOrder w SET w.status = :newStatus WHERE w.orderNo = :orderNo")
    void updateStatus(@Param("orderNo") Long orderNo, @Param("newStatus") WaiterStatus newStatus);

    @Query("SELECT DISTINCT wo FROM WaiterOrder wo " +
            "LEFT JOIN FETCH wo.waiterAccount " +
            "LEFT JOIN FETCH wo.payment " +
            "LEFT JOIN FETCH wo.positions p " +
            "LEFT JOIN FETCH p.menu " +
            "WHERE wo.orderNo = :orderNo")
    Optional<WaiterOrder> findByOrderNo(@Param("orderNo") Long orderNo);

    /**
     * Возвращает статус заказа по идентификатору
     *
     * @param orderNo идентификатор заказа
     * @return статус заказа
     */
    @Query("SELECT w.status FROM WaiterOrder w WHERE w.orderNo = :orderNo")
    Optional<WaiterStatus> findStatusByOrderNo(@Param("orderNo") Long orderNo);
}