package ru.liga.restaurant.kitchen.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.restaurant.kitchen.custom.repository.DishCustomRepository;
import ru.liga.restaurant.kitchen.custom.repository.KitchenOrderCustomRepository;
import ru.liga.restaurant.kitchen.custom.repository.OrderToDishCustomRepository;
import ru.liga.restaurant.kitchen.exception.InsufficientStockException;
import ru.liga.restaurant.kitchen.exception.NotFoundException;
import ru.liga.restaurant.kitchen.exception.OrderCannotBeDeletedException;
import ru.liga.restaurant.kitchen.feign.WaiterFeignClient;
import ru.liga.restaurant.kitchen.grpc.GrpcService;
import ru.liga.restaurant.kitchen.mapper.DishMapper;
import ru.liga.restaurant.kitchen.mapper.KitchenOrderMapper;
import ru.liga.restaurant.kitchen.mapper.OrderToDishMapper;
import ru.liga.restaurant.kitchen.model.entity.Dish;
import ru.liga.restaurant.kitchen.model.entity.KitchenOrder;
import ru.liga.restaurant.kitchen.model.entity.OrderToDish;
import ru.liga.restaurant.kitchen.model.enums.KitchenStatus;
import ru.liga.restaurant.kitchen.model.request.OrderRequest;
import ru.liga.restaurant.kitchen.model.request.OrderToDishRequest;
import ru.liga.restaurant.kitchen.model.response.DishResponse;
import ru.liga.restaurant.kitchen.model.response.OrderToDishListResponse;
import ru.liga.restaurant.kitchen.model.response.OrderToDishResponse;
import ru.liga.restaurant.kitchen.model.response.OrderToDishResponseForKitchen;

import java.util.List;

/**
 * Реализация сервиса для работы с заказами на кухне
 * Предоставляет методы для создания, отклонения заказов,
 * изменения их статуса и получения полного списка
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class KitchenServiceImpl implements KitchenService {
    private final DishCustomRepository dishCustomRepository;
    private final KitchenOrderCustomRepository kitchenOrderCustomRepository;
    private final OrderToDishCustomRepository orderToDishCustomRepository;
    private final DishMapper dishMapper;
    private final KitchenOrderMapper kitchenOrderMapper;
    private final OrderToDishMapper orderToDishMapper;
    private final WaiterFeignClient waiterFeignClient;
    private final GrpcService grpcService;

    /**
     * Создает заказ на кухне и обновляет баланс блюд
     *
     * @param orderToDishRequest запрос с информацией о заказе
     * @return ответ с информацией о принятом заказе
     * @throws NotFoundException          если блюдо не найдено
     * @throws InsufficientStockException если недостаточно продуктов
     */
    @Override
    @Transactional
    public OrderToDishResponse acceptOrder(OrderToDishRequest orderToDishRequest) {
        log.info("Принятие заказа на кухне: {}", orderToDishRequest);
        KitchenOrder kitchenOrder = kitchenOrderMapper.toKitchenOrder(orderToDishRequest);
        kitchenOrderCustomRepository.insert(kitchenOrder);
        log.info("Создан заказ с ID: {}", kitchenOrder.getKitchenOrderId());

        List<DishResponse> dishResponseList = orderToDishRequest.getDishRequest().stream()
                .map(dishRequest -> {
                    log.debug("Обработка позиции заказа: {}", dishRequest);
                    Dish dish = findDishById(dishRequest.getDishId());

                    Long currentBalance = dish.getBalance();
                    Long balance = dishRequest.getDishesNumber();
                    ensureSufficientProducts(dish, balance);

                    dish.setBalance(currentBalance - balance);
                    dishCustomRepository.update(dish);
                    log.debug("Баланс блюда обновлен: {}", dish.getBalance());

                    OrderToDish orderToDish = orderToDishMapper.toOrderToDish(kitchenOrder, dish, dishRequest);
                    orderToDishCustomRepository.insert(orderToDish);
                    log.debug("Создана связь заказ-блюдо: {}", orderToDish);

                    return dishMapper.toDishResponse(dish);
                })
                .toList();

        log.info("Принят заказ с ID {} и количеством позиций {}",
                kitchenOrder.getKitchenOrderId(), dishResponseList.size());
        return orderToDishMapper.toOrderToDishResponse(
                kitchenOrderMapper.toKitchenOrderResponse(kitchenOrder), dishResponseList);
    }

    /**
     * Отклоняет(удаляет) заказ и изменяет баланс блюд
     *
     * @param id идентификатор заказа для отклонения
     * @throws NotFoundException             если заказ не найден
     * @throws OrderCannotBeDeletedException если заказ со статусом "ГОТОВ"
     */
    @Override
    @Transactional
    public void rejectOrder(Long id) {
        log.info("Начато отклонение (удаление) заказа");
        KitchenOrder kitchenOrder = findById(id);
        log.debug("Найден заказ для отклонения: id={}, статус={}", id, kitchenOrder.getStatus());

        KitchenStatus status = kitchenOrder.getStatus();
        if (KitchenStatus.READY.equals(status)) {
            log.warn("Заказ {} невозможно отклонить (удалить), потому что его статус: {}", id, status);
            throw OrderCannotBeDeletedException.builder()
                    .message("Заказ " + id + " невозможно отклонить (удалить), потому что его статус: " + status)
                    .httpStatus(HttpStatus.CONFLICT)
                    .build();
        }

        List<OrderToDish> orderToDishList = orderToDishCustomRepository.findByKitchenOrderId(
                kitchenOrder.getKitchenOrderId());
        orderToDishList.forEach(orderToDish -> {
            Dish dish = orderToDish.getDish();
            log.debug("Обновление баланса блюда: {}", dish);

            Long currentBalance = dish.getBalance();
            Long balance = orderToDish.getDishesNumber();

            dish.setBalance(currentBalance + balance);
            dishCustomRepository.update(dish);
            log.debug("Баланс блюда обновлен после отмены заказа {} : {}", id, dish.getBalance());
        });

        kitchenOrderCustomRepository.delete(kitchenOrder);
        log.info("Заказ {} отклонен (удален)", id);

        grpcService.rejectOrder(kitchenOrder.getWaiterOrderNo());
    }

    /**
     * Проверяет возможность создания заказа
     *
     * @param orderRequest запрос с информацией о заказе
     * @throws NotFoundException если блюдо не найдено
     * @throws InsufficientStockException если недостаточно продуктов
     */
    @Override
    public void checkOrder(OrderRequest orderRequest) {
        log.info("Проверка возможности создания заказа: {}", orderRequest);
        orderRequest.getPositions()
                .forEach(orderPositionsRequest -> {
                    Dish dish = findDishById(orderPositionsRequest.getMenuPositionId());
                    ensureSufficientProducts(dish, orderPositionsRequest.getDishNum());
                });
        log.info("Заказ доступен для создания: {}", orderRequest);
    }

    /**
     * Изменяет статус заказа на "ГОТОВ" и отправляет информацию об этом в waiter module
     *
     * @param id идентификатор заказа
     * @throws NotFoundException если заказ не найден
     */
    @Override
    @Transactional
    public void readyOrder(Long id) {
        log.info("Изменение статуса заказа на \"ГОТОВ\" по ID: {}", id);
        KitchenOrder kitchenOrder = findById(id);
        kitchenOrder.setStatus(KitchenStatus.READY);
        kitchenOrderCustomRepository.updateStatus(kitchenOrder);
        log.info("Статус заказа {} изменен на \"ГОТОВ\"", id);

        waiterFeignClient.orderReady(kitchenOrder.getWaiterOrderNo());
        log.info("Информация о готовности заказа отправлена официанту: {}", kitchenOrder.getWaiterOrderNo());
    }

    /**
     * Возвращает список заказов с пагинацией
     *
     * @param pageNumber номер страницы
     * @param size количество элементов на странице
     * @return пагинированный список заказов
     */
    @Override
    public OrderToDishListResponse getKitchenList(Integer pageNumber, Integer size) {
        log.info("Получение списка заказов на кухне. Номер страницы: {}, размер: {}", pageNumber, size);
        List<OrderToDish> orderToDishCustomMapperAll = orderToDishCustomRepository.findAll(
                (pageNumber - 1) * size, size);
        log.debug("Найдено {} заказов", orderToDishCustomMapperAll.size());

        List<OrderToDishResponseForKitchen> orderToDishResponseList = orderToDishCustomMapperAll.stream()
                .map(orderToDish -> orderToDishMapper.toOrderToDishResponseForKitchen(
                        kitchenOrderMapper.toKitchenOrderResponse(orderToDish.getKitchenOrder()),
                        dishMapper.toDishResponse(orderToDish.getDish())
                ))
                .toList();

        log.info("Возвращен список из {} заказов", orderToDishResponseList.size());
        return OrderToDishListResponse.builder()
                .orderToDishResponseForKitchenList(orderToDishResponseList)
                .build();
    }

    /**
     * Проверяет наличие достаточного количества продуктов
     *
     * @param dish    блюдо для проверки
     * @param balance требуемое количество
     * @throws InsufficientStockException если недостаточно продуктов
     */
    private void ensureSufficientProducts(Dish dish, Long balance) {
        if (dish.getBalance() < balance) {
            log.warn("Недостаточно продуктов для блюда {} (осталось: {}, требуется: {})",
                    dish.getDishId(), dish.getBalance(), balance);
            throw InsufficientStockException.builder()
                    .message("Недостаточно продуктов для блюда " + dish.getShortName())
                    .httpStatus(HttpStatus.CONFLICT)
                    .build();
        }
    }

    /**
     * Находит блюдо по идентификатору
     *
     * @param dishId идентификатор блюда
     * @return блюдо по идентификатору
     * @throws NotFoundException если блюдо не найдено
     */
    private Dish findDishById(Long dishId) {
        log.debug("Поиск блюда по ID: {}", dishId);
        return dishCustomRepository.findById(dishId)
                .orElseThrow(() -> {
                    log.error("Блюдо не найдено: {}", dishId);
                    return NotFoundException.builder().message("Блюда " + dishId + " не существует")
                            .httpStatus(HttpStatus.NOT_FOUND).build();
                });
    }

    /**
     * Находит заказ по идентификатору
     *
     * @param id идентификатор заказа
     * @return заказ по идентификатору
     * @throws NotFoundException если заказ не найден
     */
    private KitchenOrder findById(Long id) {
        log.debug("Поиск заказа по ID: {}", id);
        return kitchenOrderCustomRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Заказ не найден: {}", id);
                    return NotFoundException.builder().message("Заказа " + id + " не существует")
                            .httpStatus(HttpStatus.NOT_FOUND).build();
                });
    }
}
