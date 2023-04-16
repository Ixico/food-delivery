package com.sigmamales.sigmafoodserver.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationListener {

    private final JavaMailSender javaMailSender;

    @EventListener
    public void handleRegistrationEvent(RegistrationEvent registrationEvent) {
        var message = new SimpleMailMessage();
        message.setFrom("noreply@sigmafood.com");
        message.setTo(registrationEvent.getEmail());
        message.setSubject("Activate your account!");
        message.setText(registrationEvent.getToken());
        javaMailSender.send(message);
    }
}
