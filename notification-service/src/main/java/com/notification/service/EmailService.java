package com.notification.service;

import com.notification.dto.request.EmailRequest;
import com.notification.dto.request.SendEmailRequest;
import com.notification.dto.request.Sender;
import com.notification.dto.response.EmailResponse;
import com.notification.repository.EmailClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class EmailService {

//    @Value("${app.api-key}")
    @NonFinal
    private String API_KEY=null;

    EmailClient emailClient;

    public EmailResponse sendEmail(SendEmailRequest request) {
        Sender sender = Sender.builder()
                .email("trinhvanthai2508@gmail.com")
                .name("Thai")
                .build();

        EmailRequest emailRequest = EmailRequest.builder()
                .senderRequest(sender)
                .sendEmailRequest(request)
                .build();

        return emailClient.toEmailResponse(API_KEY, emailRequest);
    }

}
