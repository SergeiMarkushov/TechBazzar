package ru.bazzar.core.services.impl;

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
public class PurchaseHistoryServiceImpl extends AbstractService<PurchaseHistory> {
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final PurchaseHistoryConverter historyConverter;

    @Override
    PurchaseHistory validSaveAndReturn(PurchaseHistory entity) {
        return purchaseHistoryRepository.save(entity);
    }

    public void saveDto(PurchaseHistoryDto historyDto) {
        validSaveAndReturn(historyConverter.dtoToEntity(historyDto));
    }

    public List<PurchaseHistory> findAll() {
        return purchaseHistoryRepository.findAll();
    }

    public List<PurchaseHistory> findAllByEmail(String email) {
        return purchaseHistoryRepository.findAllByEmailIgnoreCase(email);
    }
}
