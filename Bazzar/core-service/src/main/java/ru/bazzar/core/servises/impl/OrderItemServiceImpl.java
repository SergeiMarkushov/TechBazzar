package ru.bazzar.core.servises.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.bazzar.core.entities.OrderItem;
import ru.bazzar.core.repositories.OrderItemRepository;
import ru.bazzar.core.servises.interf.OrderItemService;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl extends AbstractService<OrderItem, Long> implements OrderItemService {
    private final OrderItemRepository orderItemRepository;

    @Override
    JpaRepository<OrderItem, Long> getRepository() {
        return orderItemRepository;
    }

    public OrderItem saveAndReturnOrderItem(OrderItem orderItem) {
        return getRepository().save(orderItem);
    }
}
