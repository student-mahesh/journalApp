package com.edigest.journalApp.Config;

import com.edigest.journalApp.Service.UserDetailsServiceImpl;
import com.edigest.journalApp.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import com.edigest.journalApp.service.UserDetailsServiceImpl;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Profile("dev")
public class SpringSecurity{

    @Autowired
    private JwtFilter jwtFilter;

@Autowired
private UserDetailsServiceImpl userDetailsService;


@Bean
public SecurityFilterChain securityFilterChanin(HttpSecurity http)throws Exception{

    return http.authorizeHttpRequests(request->request
            .requestMatchers("/public/**")
            .permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN")//.permitAll()
            .requestMatchers("/journal/**","/user/**")
            .authenticated().anyRequest()
            .permitAll())
            .csrf(AbstractHttpConfigurer::disable)
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .build();

}
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

}

/*Authentication verifies who the user is, while authorization determines what that authenticated user is allowed to access.
JWT provides a token-based mechanism commonly used to carry authentication claims between the client and server in stateless REST APIs.*/