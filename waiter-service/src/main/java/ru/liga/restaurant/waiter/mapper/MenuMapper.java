package ru.liga.restaurant.waiter.mapper;

import org.mapstruct.Mapper;
import ru.liga.restaurant.waiter.model.Menu;
import ru.liga.restaurant.waiter.model.response.MenuResponse;

@Mapper(componentModel = "spring")
public interface MenuMapper {
    MenuResponse toMenuResponse(Menu menu);
}
