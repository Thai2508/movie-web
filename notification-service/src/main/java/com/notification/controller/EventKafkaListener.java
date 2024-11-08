package com.notification.controller;

import com.event.dto.NotificationEvent;
import com.notification.dto.request.Recipient;
import com.notification.dto.request.SendEmailRequest;
import com.notification.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
@KafkaListener(groupId = "email-authentication", topics = {"notification-delivery"})
@Slf4j
public class EventKafkaListener {
    EmailService emailService;

    @KafkaHandler
    public void listenNotificationDelivery(NotificationEvent notificationEvent) {
        emailService.sendEmail(SendEmailRequest.builder()
                        .to(List.of(Recipient.builder().email(notificationEvent.getRecipient()).build()))
                        .subject(notificationEvent.getSubject())
                        .htmlContent(notificationEvent.getBody())
                .build());
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        log.info("Unknown Event {}",object);
    }
}
