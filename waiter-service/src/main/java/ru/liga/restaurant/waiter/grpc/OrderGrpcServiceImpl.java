package ru.liga.restaurant.waiter.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import proto.OrderIdProto.VoidResponse;
import proto.OrderServiceGrpc.OrderServiceImplBase;
import ru.liga.restaurant.waiter.exception.NotFoundException;
import ru.liga.restaurant.waiter.service.OrderService;

/**
 * Реализация gRPC сервиса для обработки операций с заказами
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderGrpcServiceImpl extends OrderServiceImplBase {
    private final OrderService orderService;

    /**
     * Обрабатывает запрос на отклонение заказа
     *
     * @param request          запрос с идентификатором заказа
     * @param responseObserver наблюдатель для отправки ответа
     */
    @Override
    public void rejectOrder(
            proto.OrderIdProto.OrderIdProtoBuf request,
            StreamObserver<proto.OrderIdProto.VoidResponse> responseObserver) {
        log.info("Начало обработки запроса об удалении заказа");

        try {
            orderService.rejectOrder(request.getOrderId());
            VoidResponse voidResponse = VoidResponse.newBuilder()
                    .setSuccess(true)
                    .setErrorMessage("Успешно")
                    .setHttpStatus(200)
                    .build();
            responseObserver.onNext(voidResponse);
            log.debug("Заказ успешно помечен как удаленный");

        } catch (NotFoundException notFoundException) {
            log.debug("Невозможно удалить заказ. Заказ не найдет");
            VoidResponse voidResponse = VoidResponse.newBuilder()
                    .setSuccess(false)
                    .setErrorMessage("Заказ не найден")
                    .setHttpStatus(404)
                    .build();
            responseObserver.onNext(voidResponse);
        }

        log.info("Ответ об удалении заказа отправлен");
        responseObserver.onCompleted();
    }
}
