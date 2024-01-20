package com.cydeo.fintracker.config;

import com.cydeo.fintracker.service.SecurityService;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    private final SecurityService securityService;
    private final AuthSuccessHandler authSuccessHandler;

    public SecurityConfig(SecurityService securityService, AuthSuccessHandler authSuccessHandler) {
        this.securityService = securityService;
        this.authSuccessHandler = authSuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeRequests()
                .antMatchers("/users/**","/companies/**").hasAuthority("Root User")
                .antMatchers("/users/**","/reports/**","/payments/**").hasAuthority("Admin")
                .antMatchers("/clientVendors/**","/categories/**","/products/**").hasAnyAuthority("Admin","Manager","Employee")
                .antMatchers("/purchaseInvoices/**", "/salesInvoices/**").hasAnyAuthority("Admin","Manager","Employee")
                .antMatchers("/reports/profitLossData","/reports/stockData").hasAuthority("Manager")
                .antMatchers(
                        "/",
                        "login",
                        "/assets/**",
                        "/images/**"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                   .loginPage("/login")
                   .successHandler(authSuccessHandler)
                   .failureUrl("/login?error=true")
                   .permitAll()
                .and()
                .logout()
                   .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                   .logoutSuccessUrl("/login")
                .and()
                .rememberMe()
                    .tokenValiditySeconds(864000)
                    .key("cydeo")
                    .userDetailsService(securityService)
                .and()
                .build();
    }


}
