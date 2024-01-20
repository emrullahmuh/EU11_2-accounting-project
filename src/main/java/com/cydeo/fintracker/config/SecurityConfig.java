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
                        "/login",
                        "/fragments/**",
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
                .tokenValiditySeconds(86400)
                .key("cydeo")
                .userDetailsService(securityService)
                .and()
                .build();
    }

}
/*
Create "SecurityConfig" class under "config" folder:

   1. Configure requests based on security necessities ("authorizeRequests()" and "antMatchers()")

   2. Configure "Login" functionality
Login functionality will use formLogin() option
End point will be "/login"
After successful logins, user should land to appropriate pages of the application (will be handled in "AuthSuccess Handler" class)
For unsuccessful logins, the end point wil be "/login?error=true"

  3. Configure "Logout" functionality -- End point will be "/logout"

  4. Configure "Remember Me" functionality -- Token will be valid for 864000 seconds (240 hours)

As a user, I should be able to login with valid credentials:

1- User should be able to login with valid credentials
2- User should not be able to login with invalid credentials
   * When User enters invalid credentials and clicks on the "Login" button or "Enter" key, "Invalid username or password." message should be seen on the screen (only html, with the help of thymleaf)
 */