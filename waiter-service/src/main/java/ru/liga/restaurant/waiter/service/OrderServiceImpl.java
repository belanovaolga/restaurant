package ru.liga.restaurant.waiter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.restaurant.waiter.exception.NotFoundException;
import ru.liga.restaurant.waiter.feign.KitchenFeignClient;
import ru.liga.restaurant.waiter.kafka.KafkaSender;
import ru.liga.restaurant.waiter.mapper.OrderPositionsMapper;
import ru.liga.restaurant.waiter.mapper.WaiterOrderMapper;
import ru.liga.restaurant.waiter.model.entity.Menu;
import ru.liga.restaurant.waiter.model.entity.OrderPositions;
import ru.liga.restaurant.waiter.model.entity.Payment;
import ru.liga.restaurant.waiter.model.entity.WaiterAccount;
import ru.liga.restaurant.waiter.model.entity.WaiterOrder;
import ru.liga.restaurant.waiter.model.enums.WaiterStatus;
import ru.liga.restaurant.waiter.model.request.DishRequest;
import ru.liga.restaurant.waiter.model.request.OrderRequest;
import ru.liga.restaurant.waiter.model.request.OrderToDishRequest;
import ru.liga.restaurant.waiter.model.response.OrderResponse;
import ru.liga.restaurant.waiter.model.response.OrderResponseList;
import ru.liga.restaurant.waiter.model.response.StatusResponse;
import ru.liga.restaurant.waiter.repository.MenuRepository;
import ru.liga.restaurant.waiter.repository.OrderPositionsRepository;
import ru.liga.restaurant.waiter.repository.PaymentRepository;
import ru.liga.restaurant.waiter.repository.WaiterAccountRepository;
import ru.liga.restaurant.waiter.repository.WaiterOrderRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для работы с заказами
 * Реализует бизнес-логику создания и управления заказами
 * Взаимодействует с kitchen module через Feign Client и Kafka.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final WaiterOrderRepository waiterOrderRepository;
    private final WaiterAccountRepository waiterAccountRepository;
    private final OrderPositionsRepository orderPositionsRepository;
    private final MenuRepository menuRepository;
    private final PaymentRepository paymentRepository;
    private final WaiterOrderMapper waiterOrderMapper;
    private final OrderPositionsMapper orderPositionsMapper;
    private final KitchenFeignClient kitchenFeignClient;
    private final KafkaSender kafkaSender;

    /**
     * Получает пагинированный список заказов
     *
     * @param pageNumber номер страницы
     * @param size       количество элементов на странице
     * @return список заказов с оберткой {@link OrderResponseList}
     * @throws IllegalArgumentException, если pageNumber или size некорректны
     */
    @Override
    public OrderResponseList getOrderList(Integer pageNumber, Integer size) {
        log.info("Получение списка заказов. Номер страницы: {}, размер: {}", pageNumber, size);
        List<OrderResponse> orderResponseList = waiterOrderRepository.findAll(
                        PageRequest.of(pageNumber - 1, size)).stream()
                .map(waiterOrderMapper::toOrderResponse)
                .toList();

        log.info("Возвращен список из {} заказов", orderResponseList.size());
        return OrderResponseList.builder().orderResponsesList(orderResponseList).build();
    }

    /**
     * Получает детальную информацию о заказе
     *
     * @param id идентификатор заказа
     * @return данные заказа в формате {@link OrderResponse}
     * @throws NotFoundException если заказ не найден
     */
    @Override
    public OrderResponse getOrder(Long id) {
        log.info("Получение заказа по ID: {}", id);
        OrderResponse orderResponse = waiterOrderMapper.toOrderResponse(findByOrderNo(id));
        log.debug("Заказ получен: {}", orderResponse);
        return orderResponse;
    }

    /**
     * Проверяет возможность создания заказа, создает заказ,
     * отправляет сообщение об этом в Kafka
     *
     * @param orderRequest DTO с данными для создания заказа
     * @return созданный заказ
     * @throws NotFoundException если:
     *                           <ul>
     *                             <li>Официант не найден</li>
     *                             <li>Блюдо не найдено</li>
     *                           </ul>
     */
    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        log.info("Создание нового заказа: {}", orderRequest);

        log.debug("Проверка возможности создания заказа через KitchenService");
        kitchenFeignClient.checkOrder(orderRequest);

        log.debug("Поиск официанта по ID: {}", orderRequest.getWaiterId());
        WaiterAccount waiterAccount = waiterAccountRepository.findById(orderRequest.getWaiterId())
                .orElseThrow(() -> {
                    log.error("Официант не найден: {}", orderRequest.getWaiterId());
                    return NotFoundException.builder()
                            .message("Официант не найден")
                            .httpStatus(HttpStatus.NOT_FOUND)
                            .build();
                });

        log.debug("Создание и сохранение объекта заказа");
        WaiterOrder waiterOrder = waiterOrderMapper.toWaiterOrder(orderRequest, waiterAccount);
        WaiterOrder savedOrder = waiterOrderRepository.save(waiterOrder);
        log.info("Создан новый заказ с ID: {}", savedOrder.getOrderNo());

        log.debug("Добавление позиций заказа");
        List<OrderPositions> positions = orderRequest.getPositions().stream()
                .map(orderPositionsRequest -> {
                    log.debug("Обработка позиции: {}", orderPositionsRequest);
                    OrderPositions orderPositions = orderPositionsMapper.toOrderPositions(
                            orderPositionsRequest, savedOrder);
                    orderPositions.setMenu(findMenuById(orderPositionsRequest.getMenuPositionId()));
                    return orderPositionsRepository.save(orderPositions);
                })
                .collect(Collectors.toList());
        savedOrder.setPositions(positions);
        log.debug("Добавлено {} позиций", positions.size());

        Double sum = calculateTotalSum(positions);
        Payment payment = createPayment(savedOrder, orderRequest.getPaymentType(), sum);
        savedOrder.setPayment(payment);

        WaiterOrder finalOrder = waiterOrderRepository.save(savedOrder);

        log.debug("Подготовка данных для сервиса кухни");
        List<DishRequest> dishRequest = finalOrder.getPositions().stream()
                .map(orderPositions -> DishRequest.builder()
                        .dishId(orderPositions.getMenu().getId())
                        .dishesNumber(orderPositions.getDishNum())
                        .build())
                .toList();
        OrderToDishRequest orderToDishRequest = OrderToDishRequest.builder()
                .kitchenOrderId(finalOrder.getOrderNo())
                .waiterOrderNo(finalOrder.getWaiterAccount().getWaiterId())
                .dishRequest(dishRequest)
                .build();

        log.debug("Отправка заказа в kafka");
        kafkaSender.sendOrder(orderToDishRequest);

        return waiterOrderMapper.toOrderResponse(finalOrder);
    }

    /**
     * Получает статус заказа
     *
     * @param id идентификатор заказа
     * @return DTO с текущим статусом
     * @throws NotFoundException если заказ не найден
     */
    @Override
    public StatusResponse getStatus(Long id) {
        log.info("Получение статуса заказа по ID: {}", id);
        WaiterStatus waiterStatus = waiterOrderRepository.findStatusByOrderNo(id)
                .orElseThrow(() -> {
                    log.error("Заказ не найден: {}", id);
                    return NotFoundException.builder().message("Заказ не найден").httpStatus(HttpStatus.NOT_FOUND).build();
                });
        log.debug("Статус заказа {}: {}", id, waiterStatus);
        return StatusResponse.builder().waiterStatus(waiterStatus).build();
    }

    /**
     * Изменяет статус заказа на "ГОТОВ"
     *
     * @param id идентификатор заказа
     * @throws NotFoundException если заказ не найден
     */
    @Override
    @Transactional
    public void orderReady(Long id) {
        log.info("Изменение статуса заказа на \"ГОТОВ\" по ID: {}", id);
        waiterOrderRepository.updateStatus(id, WaiterStatus.READY);
        log.info("Статус заказа {} изменен на \"ГОТОВ\"", id);
    }

    /**
     * Изменяет статус заказа на "УДАЛЕН"
     *
     * @param id идентификатор заказа
     * @throws NotFoundException если заказ не найден
     */
    @Override
    @Transactional
    public void rejectOrder(Long id) {
        log.info("Изменение статуса заказа на \"УДАЛЕН\" по ID: {}", id);
        waiterOrderRepository.updateStatus(id, WaiterStatus.DELETED);
        log.info("Статус заказа {} изменен на \"УДАЛЕН\"", id);
    }

    /**
     * Находит заказ по идентификатору
     * @param id идентификатор заказа
     * @return найденный заказ
     * @throws NotFoundException если заказ не существует
     */
    private WaiterOrder findByOrderNo(Long id) {
        log.debug("Поиск заказа по ID: {}", id);
        return waiterOrderRepository.findByOrderNo(id).orElseThrow(() -> {
            log.error("Заказ не найден: {}", id);
            return NotFoundException.builder().message("Заказ не найден").httpStatus(HttpStatus.NOT_FOUND).build();
        });
    }

    /**
     * Находит блюдо по идентификатору
     * @param id идентификатор блюда
     * @return найденное блюдо
     * @throws NotFoundException если блюдо не существует
     */
    private Menu findMenuById(Long id) {
        log.debug("Поиск блюда по ID: {}", id);
        return menuRepository.findById(id).orElseThrow(() -> {
            log.error("Блюдо не найдено: {}", id);
            return NotFoundException.builder()
                    .message("Блюдо " + id + " не найдено")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        });
    }

    /**
     * Рассчитывает итоговую сумму заказа
     * @param positions список позиций заказа
     * @return общая сумма
     */
    private Double calculateTotalSum(List<OrderPositions> positions) {
        log.debug("Расчет суммы для {} позиций заказа", positions.size());
        return positions.stream()
                .mapToDouble(orderPositions ->
                        orderPositions.getMenu().getDishCost() * orderPositions.getDishNum())
                .sum();
    }

    /**
     * Создает и сохраняет платеж заказа
     * @param order заказ
     * @param paymentType тип платежа ("CASH", "CARD")
     * @param sum сумма платежа
     * @return платеж заказа
     */
    private Payment createPayment(WaiterOrder order, String paymentType, Double sum) {
        log.debug("Создание платежа для заказа {}. Тип: {}, сумма: {}", order.getOrderNo(), paymentType, sum);
        Payment payment = Payment.builder()
                .waiterOrder(order)
                .paymentType(paymentType)
                .paymentDate(ZonedDateTime.now())
                .paymentSum(sum)
                .build();
        log.debug("Создан платеж с ID: {}", payment.getOrderNo());
        return paymentRepository.save(payment);
    }
}
