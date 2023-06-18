package ru.bazzar.core.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDto {
    private Long id;
    private int dis;
    private LocalDateTime startDate;
    private LocalDateTime expiryDate;

    @Override
    public String toString() {
        return "DiscountDto{" +
                "id=" + id +
                ", dis=" + dis +
                ", startDate=" + startDate +
                ", expiryDate=" + expiryDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountDto that = (DiscountDto) o;
        return dis == that.dis && Objects.equals(id, that.id) && Objects.equals(startDate, that.startDate) && Objects.equals(expiryDate, that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dis, startDate, expiryDate);
    }
}
