package ru.bazzar.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bazzar.core.api.PurchaseHistoryDto;
import ru.bazzar.core.converters.PurchaseHistoryConverter;
import ru.bazzar.core.entities.PurchaseHistory;
import ru.bazzar.core.repositories.PurchaseHistoryRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
//история покупки
public class PurchaseHistoryService {
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final PurchaseHistoryConverter historyConverter;

    public void saveDto(PurchaseHistoryDto historyDto) {
        purchaseHistoryRepository.save(historyConverter.dtoToEntity(historyDto));
    }

    public List<PurchaseHistory> findAll() {
        return purchaseHistoryRepository.findAll();
    }

    public List<PurchaseHistory> findAllByEmail(String email) {
        return purchaseHistoryRepository.findAllByEmailIgnoreCase(email);
    }
}
