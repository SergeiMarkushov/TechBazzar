package ru.bazzar.core.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bazzar.core.api.OrderDto;
import ru.bazzar.core.api.ResourceNotFoundException;
import ru.bazzar.core.converters.OrderConverter;
import ru.bazzar.core.entities.Order;
import ru.bazzar.core.servises.impl.OrderServiceImpl;
import ru.bazzar.core.servises.interf.SimpleService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Log4j2
public class OrderController extends AbstractRestController<Order, Long> {
    private final OrderServiceImpl orderServiceImpl;
    private final OrderConverter orderConverter;

    @Override
    SimpleService<Order, Long> getService() {
        return orderServiceImpl;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestHeader String username) {
        System.out.println(username + " оформил заказ!");
        orderServiceImpl.create(username);
    }

    @GetMapping
    public List<OrderDto> get(@RequestHeader String username) {
        log.info(orderServiceImpl.getOrder(username).stream().map(orderConverter::entityToDto).collect(Collectors.toList()));
        return orderServiceImpl.getOrder(username).stream().map(orderConverter::entityToDto).collect(Collectors.toList());
    }
    @GetMapping("/is_refund/{id}")
    public boolean isRefundOrder(@PathVariable Long id) {
        return orderServiceImpl.isRefundOrder(id);
    }

    @GetMapping("/refund/{id}")
    public void refundOrder(@RequestHeader String username, @PathVariable Long id) {
        orderServiceImpl.refundPayment(username, id);
    }

    @GetMapping("/payment/{id}")
    public void payment(@RequestHeader String username, @PathVariable Long id) throws ResourceNotFoundException {
        orderServiceImpl.payment(username, id);
    }
    //deleteById(рабочий эндпоинт)


//    @ExceptionHandler({ResourceNotFoundException.class})
//    private ResponseEntity<String> handleNotFound(Exception e) {
//        log.error(e.getMessage());
//        return ResponseEntity.badRequest().body(e.getMessage());
//    }
}
