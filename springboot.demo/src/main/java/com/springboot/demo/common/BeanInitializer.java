package com.springboot.demo.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Component
public class BeanInitializer {

    @Bean
    public LocaleResolver getLocaleResolver(){
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.US);
        return sessionLocaleResolver;
    }

//    @Bean
//    public ResourceBundleMessageSource messageSource(){
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasename("messages");
//        return messageSource;
//    }

//    @Bean
//    public SecurityWen securityWebFilterChain(
//            ServerHttpSecurity http) {
//        return http.authorizeExchange()
//                .pathMatchers("/actuator/**").permitAll()
//                .anyExchange().authenticated()
//                .and().build();
//    }

}
