package com.proyecto.gestock.evento.SingingEvent;

import com.proyecto.gestock.email.domain.EmailMessage.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CustomerRegisteredEventListener {

    private final EmailService emailService;

    public CustomerRegisteredEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    @Async
    public void onCustomerRegisteredEvent(CustomerRegisteredEvent event) throws MessagingException {
        emailService.correoSing(event.getEmail(), event.getName());
    }
}
