package ru.bazzar.core.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bazzar.core.api.OrderDto;
import ru.bazzar.core.api.ResourceNotFoundException;
import ru.bazzar.core.converters.OrderConverter;
import ru.bazzar.core.services.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Log4j2
public class OrderController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestHeader String username) {
        System.out.println(username + " оформил заказ!");
        orderService.create(username);
    }

    @GetMapping
    public List<OrderDto> get(@RequestHeader String username) {
        log.info(orderService.getOrder(username).stream().map(orderConverter::entityToDto).collect(Collectors.toList()));
        return orderService.getOrder(username).stream().map(orderConverter::entityToDto).collect(Collectors.toList());
    }
    @GetMapping("/is_refund/{id}")
    public boolean isRefundOrder(@PathVariable Long id) {
        return orderService.isRefundOrder(id);
    }

    @GetMapping("/refund/{id}")
    public void refundOrder(@RequestHeader String username, @PathVariable Long id) {
        orderService.refundPayment(username, id);
    }

    @GetMapping("/payment/{id}")
    public void payment(@RequestHeader String username, @PathVariable Long id) throws ResourceNotFoundException {
        orderService.payment(username, id);
    }

}
