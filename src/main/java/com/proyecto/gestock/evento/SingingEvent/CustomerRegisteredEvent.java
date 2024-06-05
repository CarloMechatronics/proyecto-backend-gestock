package com.proyecto.gestock.evento.SingingEvent;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CustomerRegisteredEvent extends ApplicationEvent {
    private final String email;
    private final String name;

    public CustomerRegisteredEvent(String email, String name) {
        super(email);
        this.email = email;
        this.name = name;
    }
}
