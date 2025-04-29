package ru.liga.restaurant.waiter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.restaurant.waiter.model.entity.Payment;

/**
 * Репозиторий для работы с платежами
 * Предоставляет основные CRUD-операции для сущности {@link Payment}
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
