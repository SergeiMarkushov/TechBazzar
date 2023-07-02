package ru.bazzar.core.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private String username;
    private String fullName;
    private String reviewText;
    private int mark;
    private Long productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewDto reviewDto)) return false;
        return getMark() == reviewDto.getMark() && Objects.equals(getId(), reviewDto.getId()) && Objects.equals(getUsername(), reviewDto.getUsername()) && Objects.equals(getFullName(), reviewDto.getFullName()) && Objects.equals(getReviewText(), reviewDto.getReviewText()) && Objects.equals(getProductId(), reviewDto.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getFullName(), getReviewText(), getMark(), getProductId());
    }
}
