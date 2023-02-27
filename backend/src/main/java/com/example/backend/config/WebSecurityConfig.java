package com.example.backend.config;

import com.example.backend.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Slf4j
public class WebSecurityConfig{

  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors()
        .and()
        .csrf().disable()
        .httpBasic().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests().antMatchers("/", "/auth/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);

        return http.build();
  }

//  @Override
//  protected void configure(final HttpSecurity http) throws Exception {
//    http.cors()
//        .and()
//        .csrf().disable()
//        .httpBasic().disable()
//        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        .and()
//        .authorizeRequests().antMatchers("/", "/auth/**").permitAll()
//        .anyRequest().authenticated()
//        .and()
//        .addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
//  }
}
