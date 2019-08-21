package com.baeldung.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan({ "com.baeldung.web.controller" })
public class ResourceServerWebConfig implements WebMvcConfigurer {
    //

    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .regexMatchers(".+/users/test.+")
            .permitAll()
            .anyRequest()
            .authenticated();
    }
}
