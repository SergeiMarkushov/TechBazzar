package ru.bazzar.core.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bazzar.core.entities.OrderItem;
import ru.bazzar.core.repositories.OrderItemRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderItemServiceImpl extends AbstractService<OrderItem> {
    private final OrderItemRepository orderItemRepository;

    @Override
    OrderItem validSaveAndReturn(OrderItem entity) {
        return orderItemRepository.save(entity);
    }
}
