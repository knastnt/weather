package ru.knastnt.weather.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfiguration {

    @Bean
    @ConfigurationProperties("mail")
    public JavaMailSender mailSender() {
        return new JavaMailSenderImpl();
    }
}