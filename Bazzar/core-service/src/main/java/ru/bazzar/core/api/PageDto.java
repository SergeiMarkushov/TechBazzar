package ru.bazzar.core.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageDto<E> {
    private List<E> items;
    private int page;
    private int totalPages;

    @Override
    public String toString() {
        return "PageDto{" +
                "items=" + items +
                ", page=" + page +
                ", totalPages=" + totalPages +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageDto<?> pageDto = (PageDto<?>) o;
        return page == pageDto.page && totalPages == pageDto.totalPages && Objects.equals(items, pageDto.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, page, totalPages);
    }
}
