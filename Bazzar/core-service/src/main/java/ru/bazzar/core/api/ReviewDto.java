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
    private String reviewText;
    private int mark;

    @Override
    public String toString() {
        return "ReviewDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", reviewText='" + reviewText + '\'' +
                ", mark=" + mark +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewDto reviewDto = (ReviewDto) o;
        return mark == reviewDto.mark && Objects.equals(id, reviewDto.id) && Objects.equals(username, reviewDto.username) && Objects.equals(reviewText, reviewDto.reviewText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, reviewText, mark);
    }
}
