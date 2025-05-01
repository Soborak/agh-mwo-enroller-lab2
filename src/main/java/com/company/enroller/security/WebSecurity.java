package com.company.enroller.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;


@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Value("${security.secret}")
    private String secret;

    @Value("${security.issuer}")
    private String issuer;

    @Value("${security.tokenExpiration}")
    private int tokenExpiration;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("participants/register").permitAll() //rejestracja dla wszystkich
            .anyRequest().authenticated() //reszta po zalogowaniu
            .and()
            .formLogin() //domyślny formularz logowania
            .and()
            .addFilterBefore(new
                JWTAuthenticationFilter(authenticationManager(), secret, issuer,
                tokenExpiration), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
