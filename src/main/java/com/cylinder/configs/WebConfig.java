package com.cylinder.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

/**
 * @author Ryan Piper
 * Configures beans that are used globally.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    /**
     * Creates a BCryptPasswordEncoder instance.
     *
     * @return the password encoder.
     */
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    /**
     * Enables thymeleaf security tags.
     *
     * @return the spring security dialect.
     */
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }
}
