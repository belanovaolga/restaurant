package ru.liga.restaurant.kitchen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.liga.restaurant.kitchen.repository.KitchenRepository;
import ru.liga.restaurant.kitchen.request.KitchenRequest;
import ru.liga.restaurant.kitchen.dto.KitchenDto;
import ru.liga.restaurant.kitchen.dto.Status;
import ru.liga.restaurant.kitchen.exception.OrderAlreadyExistException;
import ru.liga.restaurant.kitchen.exception.OrderNotFoundException;
import ru.liga.restaurant.kitchen.exception.OrderNotReadyException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KitchenServiceImpl implements KitchenService {
    private final KitchenRepository kitchenRepository;

    @Override
    public KitchenDto acceptOrder(KitchenRequest kitchenRequest) {
        if (kitchenRepository.findById(kitchenRequest.getId()).isPresent()) {
            throw OrderAlreadyExistException.builder().message("Заказ с таким идентификатором уже существует").httpStatus(HttpStatus.CONFLICT).build();
        }

        KitchenDto kitchenDTO = KitchenDto.builder()
                .id(kitchenRequest.getId())
                .dishes(kitchenRequest.getDishes())
                .waiterId(kitchenRequest.getWaiterId())
                .status(Status.AWAITING)
                .build();

        return kitchenRepository.save(kitchenDTO);
    }

    @Override
    public String rejectOrder(KitchenDto kitchenDTO) {
        return "Заказ " + kitchenDTO.getId() + " отклонен";
    }

    @Override
    public KitchenDto readyOrder(Long id) {
        KitchenDto kitchenDTO = findById(id);

        if (Status.READY.equals(kitchenDTO.getStatus())) {
            return kitchenDTO;
        }

        throw OrderNotReadyException.builder().message("Заказ еще не готов").httpStatus(HttpStatus.TOO_EARLY).build();
    }

    @Override
    public List<KitchenDto> getKitchenList() {
        return kitchenRepository.findAll();
    }

    private KitchenDto findById(Long id) {
        return kitchenRepository.findById(id).orElseThrow(() -> OrderNotFoundException.builder().message("Заказ не найден").httpStatus(HttpStatus.NOT_FOUND).build());
    }
}
