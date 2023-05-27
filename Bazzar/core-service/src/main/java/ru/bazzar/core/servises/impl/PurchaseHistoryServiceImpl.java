package ru.bazzar.core.servises.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bazzar.core.api.PurchaseHistoryDto;
import ru.bazzar.core.converters.PurchaseHistoryConverter;
import ru.bazzar.core.entities.PurchaseHistory;
import ru.bazzar.core.repositories.PurchaseHistoryRepository;
import ru.bazzar.core.servises.interf.PurchaseHistoryService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
//история покупки
public class PurchaseHistoryServiceImpl extends AbstractService<PurchaseHistory, Long> implements PurchaseHistoryService {
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final PurchaseHistoryConverter historyConverter;

    @Override
    JpaRepository<PurchaseHistory, Long> getRepository() {
        return purchaseHistoryRepository;
    }

    public void saveDto(PurchaseHistoryDto historyDto) {
        getRepository().save(historyConverter.dtoToEntity(historyDto));
    }

    public List<PurchaseHistory> findAll() {
        return purchaseHistoryRepository.findAll();
    }

    public List<PurchaseHistory> findAllByEmail(String email) {
        return purchaseHistoryRepository.findAllByEmailIgnoreCase(email);
    }

}
