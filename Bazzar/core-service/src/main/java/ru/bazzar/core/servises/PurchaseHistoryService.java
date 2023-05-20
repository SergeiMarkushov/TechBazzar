package ru.bazzar.core.servises;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bazzar.api.PurchaseHistoryDto;
import ru.bazzar.core.converters.PurchaseHistoryConverter;
import ru.bazzar.core.entities.PurchaseHistory;
import ru.bazzar.core.repositories.PurchaseHistoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseHistoryService {
    private final PurchaseHistoryRepository repository;
    private final PurchaseHistoryConverter historyConverter;

    public void save(PurchaseHistoryDto historyDto) {
        repository.save(historyConverter.dtoToEntity(historyDto));
    }

    public List<PurchaseHistory> findAll() {
        return repository.findAll();
    }

    public List<PurchaseHistory> findAllByEmail(String email) {
        return repository.findAllByEmailIgnoreCase(email);
    }
}
