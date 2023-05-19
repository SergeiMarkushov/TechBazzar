package ru.bazzar.core.servises;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bazzar.core.entities.OrderItem;
import ru.bazzar.core.repositories.OrderItemRepository;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
