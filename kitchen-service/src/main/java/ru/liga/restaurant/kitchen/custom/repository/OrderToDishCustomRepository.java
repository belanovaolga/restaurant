package ru.liga.restaurant.kitchen.custom.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import ru.liga.restaurant.kitchen.model.entity.OrderToDish;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface OrderToDishCustomRepository {
    void insert(OrderToDish orderToDish);

    Optional<OrderToDish> findByCompositeKey(Long kitchenOrderId, Long dishId);

    List<OrderToDish> findByKitchenOrderId(Long kitchenOrderId);

    List<OrderToDish> findAll(@Param("offset") int offset, @Param("pageSize") int pageSize);
}
