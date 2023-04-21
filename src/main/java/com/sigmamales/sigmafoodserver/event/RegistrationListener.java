package com.sigmamales.sigmafoodserver.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegistrationListener {

    private final JavaMailSender javaMailSender;

    @EventListener
    @Async
    public void handleRegistrationEvent(RegistrationEvent registrationEvent) {
        var message = new SimpleMailMessage();
        message.setFrom("noreply@sigmafood.com");
        message.setTo(registrationEvent.getEmail());
        message.setSubject("Activate your account!");
        message.setText(registrationEvent.getToken());
        try {
            javaMailSender.send(message);
        } catch (Exception ex) {
            log.error("Exception on sending mail to {}", registrationEvent.getEmail(), ex);
        }
    }
}
