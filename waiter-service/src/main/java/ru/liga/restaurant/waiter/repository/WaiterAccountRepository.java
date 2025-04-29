package ru.liga.restaurant.waiter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.restaurant.waiter.model.entity.WaiterAccount;

/**
 * Репозиторий для работы с аккаунтами официантов
 * Предоставляет основные CRUD-операции для сущности {@link WaiterAccount}
 */
@Repository
public interface WaiterAccountRepository extends JpaRepository<WaiterAccount, Long> {
}
