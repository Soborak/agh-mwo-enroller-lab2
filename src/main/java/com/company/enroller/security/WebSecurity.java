package com.company.enroller.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                .antMatchers("/participants/register").permitAll() //rejestracja dla wszystkich
                .antMatchers(HttpMethod.POST, "/participants").permitAll()
                .antMatchers("/tokens").permitAll()
                .anyRequest().authenticated() //reszta po zalogowaniu
                .and()
                .formLogin() //domyślny formularz logowania
                .and()
                // .logout()   // (lab. 06)
                // .permiALL() // (lab. 06)
                .addFilterBefore(
                        new JWTAuthenticationFilter(authenticationManager(), secret, issuer, tokenExpiration),
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilter(
                        new JWTAuthorizationFilter(authenticationManager(), secret)
                );
    }

//    //konfigurowanie użytkownika „w pamięci” na czas testów (lab. 06)
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//            .withUser("user").password(passwordEncoder().encode("password")).roles("USER");
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
