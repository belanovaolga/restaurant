package ru.liga.restaurant.kitchen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.restaurant.kitchen.dao.KitchenDao;
import ru.liga.restaurant.kitchen.dto.KitchenDTO;
import ru.liga.restaurant.kitchen.dto.Status;
import ru.liga.restaurant.kitchen.exception.OrderAlreadyExist;
import ru.liga.restaurant.kitchen.exception.OrderNotFound;
import ru.liga.restaurant.kitchen.exception.OrderNotReady;
import ru.liga.restaurant.kitchen.repository.KitchenRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KitchenService {
    private final KitchenRepository kitchenRepository;

    public KitchenDTO acceptOrder(KitchenDao kitchenDao) {
        if (kitchenRepository.findById(kitchenDao.getId()).isPresent()) {
            throw OrderAlreadyExist.builder().message("An order with this id already exists").code(409).build();
        }

        KitchenDTO kitchenDTO = KitchenDTO.builder()
                .id(kitchenDao.getId())
                .dishes(kitchenDao.getDishes())
                .waiterId(kitchenDao.getWaiterId())
                .status(Status.AWAITING)
                .build();

        return kitchenRepository.save(kitchenDTO);
    }

    public String rejectOrder(KitchenDTO kitchenDTO) {
        return "Order " + kitchenDTO.getId() + " rejected";
    }

    public KitchenDTO readyOrder(Long id) {
        KitchenDTO kitchenDTO = findById(id);

        if(kitchenDTO.getStatus().equals(Status.READY)) {
            return kitchenDTO;
        }

        throw OrderNotReady.builder().message("The order is not ready yet").code(425).build();
    }

    public List<KitchenDTO> getKitchenList() {
        return kitchenRepository.findAll();
    }

    private KitchenDTO findById(Long id) {
        return kitchenRepository.findById(id).orElseThrow(() -> OrderNotFound.builder().message("Order not found").code(404).build());
    }
}
