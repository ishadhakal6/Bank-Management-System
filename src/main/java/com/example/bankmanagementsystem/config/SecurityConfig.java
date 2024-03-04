package com.example.bankmanagementsystem.config;

import com.example.bankmanagementsystem.model.entity.Customer;
import com.example.bankmanagementsystem.model.service.CustomerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder){
////        UserDetails admin= User.withUsername("Isha")
////                .password(encoder.encode("noooooo"))
////                .roles("ADMIN")
////                .build();
//        UserDetails user=User.withUsername("Pujan")
//                .password(encoder.encode("hiiiiii"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);

        return new CustomerService();
    }

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
       return http.csrf(csrf ->csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/bank/transactions").permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    }
}
