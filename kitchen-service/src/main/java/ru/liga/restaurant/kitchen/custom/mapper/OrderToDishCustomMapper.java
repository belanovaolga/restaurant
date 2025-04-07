package ru.liga.restaurant.kitchen.custom.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import ru.liga.restaurant.kitchen.model.entity.OrderToDish;

import java.util.List;

@Mapper
@Repository
public interface OrderToDishCustomMapper {
    void insert(OrderToDish orderToDish);

    List<OrderToDish> findById(Long id);

    List<OrderToDish> findAll();
}
