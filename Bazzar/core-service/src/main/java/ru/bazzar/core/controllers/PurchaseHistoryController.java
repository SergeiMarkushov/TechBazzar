package ru.bazzar.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bazzar.api.PurchaseHistoryDto;
import ru.bazzar.core.converters.PurchaseHistoryConverter;
import ru.bazzar.core.entities.PurchaseHistory;
import ru.bazzar.core.servises.impl.PurchaseHistoryServiceImpl;
import ru.bazzar.core.servises.interf.SimpleService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/history")
public class PurchaseHistoryController extends AbstractRestController<PurchaseHistory, Long> {
    private final PurchaseHistoryServiceImpl historyService;
    private final PurchaseHistoryConverter historyConverter;

    @Override
    SimpleService<PurchaseHistory, Long> getService() {
        return historyService;
    }

    @GetMapping("/all")
    public List<PurchaseHistoryDto> findAll() {
        return historyService.findAll()
                .stream()
                .map(historyConverter::entityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping//было String username, исправил на email
    public List<PurchaseHistoryDto> findAllByEmail(@RequestHeader String email) {
        return historyService.findAllByEmail(email)
                .stream()
                .map(historyConverter::entityToDto)
                .collect(Collectors.toList());
    }
    //deleteById
}
