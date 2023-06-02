package ru.bazzar.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.bazzar.core.api.PurchaseHistoryDto;
import ru.bazzar.core.entities.PurchaseHistory;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Component
@RequiredArgsConstructor
public class PurchaseHistoryConverter {
    public PurchaseHistoryDto entityToDto(PurchaseHistory history) {
        PurchaseHistoryDto historyDto = new PurchaseHistoryDto();
        historyDto.setId(history.getId());
        historyDto.setEmail(history.getEmail());
        historyDto.setProductTitle(history.getProductTitle());
        historyDto.setOrganizationTitle(history.getOrganization());
        historyDto.setQuantity(history.getQuantity());
        historyDto.setDatePurchase(history.getDatePurchase().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        return historyDto;
    }

    public PurchaseHistory dtoToEntity(PurchaseHistoryDto historyDto) {
        PurchaseHistory history = new PurchaseHistory();
        history.setEmail(historyDto.getEmail());
        history.setProductTitle(historyDto.getProductTitle());
        history.setOrganization(historyDto.getOrganizationTitle());
        history.setQuantity(historyDto.getQuantity());
        return history;
    }
}
