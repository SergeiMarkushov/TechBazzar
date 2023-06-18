package ru.bazzar.notification.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Long id;
    private String title;
    private String createdAt;
    private String content;
    private String sendTo;

    @Override
    public String toString() {
        return "NotificationDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", content='" + content + '\'' +
                ", sendTo='" + sendTo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationDto that = (NotificationDto) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(createdAt, that.createdAt) && Objects.equals(content, that.content) && Objects.equals(sendTo, that.sendTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, createdAt, content, sendTo);
    }
}
