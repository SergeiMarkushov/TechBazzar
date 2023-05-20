package ru.bazzar.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bazzar.api.PurchaseHistoryDto;
import ru.bazzar.core.converters.PurchaseHistoryConverter;
import ru.bazzar.core.servises.PurchaseHistoryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/history")
public class PurchaseHistoryController {
    private final PurchaseHistoryService historyService;
    private final PurchaseHistoryConverter historyConverter;

    @GetMapping("/all")
    public List<PurchaseHistoryDto> findAll() {
        return historyService.findAll()
                .stream()
                .map(historyConverter::entityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<PurchaseHistoryDto> findAllByEmail(@RequestHeader String username) {
        return historyService.findAllByEmail(username)
                .stream()
                .map(historyConverter::entityToDto)
                .collect(Collectors.toList());
    }
}
