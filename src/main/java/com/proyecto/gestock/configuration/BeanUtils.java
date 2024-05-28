package com.proyecto.gestock.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanUtils {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
