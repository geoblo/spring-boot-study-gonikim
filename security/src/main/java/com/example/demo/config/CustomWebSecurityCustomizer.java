package com.example.demo.config;

import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.stereotype.Component;

//@Component
public class CustomWebSecurityCustomizer implements WebSecurityCustomizer {
    @Override
    public void customize(WebSecurity web) {
        web.ignoring().requestMatchers("");
    }
}
