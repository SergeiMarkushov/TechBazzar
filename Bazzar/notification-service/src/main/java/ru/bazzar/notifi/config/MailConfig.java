package ru.bazzar.notifi.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import ru.bazzar.api.NotificationDto;
import ru.bazzar.notifi.servises.NotificationServiceImpl;

@Configuration
@Log4j2
@RequiredArgsConstructor
public class MailConfig {
    private final NotificationServiceImpl notificationServiceImpl;
    private final SimpleMailMessage mailMessage = new SimpleMailMessage();
    private final MailSender mailSender;
    @Value("${spring.mail.username}")
    private String email_from;

    public void sendMailNotification(NotificationDto notificationDto) {
        mailMessage.setFrom(email_from);
        mailMessage.setTo(notificationDto.getSendTo());
        mailMessage.setSubject(notificationDto.getTitle());
        mailMessage.setText(notificationDto.getContent());
        mailSender.send(mailMessage);
        notificationServiceImpl.save(notificationDto);
        log.info("Отправлено уведомление: {}, получатель: {}", notificationDto.getContent(), notificationDto.getSendTo());
    }
}
