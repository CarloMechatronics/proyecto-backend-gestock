package com.proyecto.gestock.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    // Bean para mappear solo atributos no nulos
    @Bean
    public ModelMapper nonNullMapper() {
        ModelMapper nonNullMapper = new ModelMapper();
        nonNullMapper.getConfiguration().setSkipNullEnabled(true);
        return nonNullMapper;
    }
}
