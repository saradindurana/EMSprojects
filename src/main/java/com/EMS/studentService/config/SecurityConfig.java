package com.EMS.studentService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.EMS.studentService.security.JwtAuthEntryPoint;
import com.EMS.studentService.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {


    @Autowired
    private JwtAuthEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;
    @Autowired
    private UserDetailsService userdetailservice;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    	http.csrf(csrf -> csrf.disable())
    	.cors(cors->cors.disable())
    	.authorizeHttpRequests(auth->auth.
    			requestMatchers("/test").authenticated().
    			requestMatchers("/auth/login").permitAll()
    			.requestMatchers("/h2-console/*").permitAll()
    			.requestMatchers("/h2-console").permitAll()
    			.requestMatchers("/h2/**").permitAll()
    			.requestMatchers("/student/register").permitAll()
    			.anyRequest().authenticated()
    			)
    	
    	.exceptionHandling(ex -> ex.authenticationEntryPoint(point))
    	.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    	;
    	http.headers(headers -> headers.frameOptions(FrameOptionsConfig::disable));
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
    	DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
    	provider.setUserDetailsService(userdetailservice);
    	provider.setPasswordEncoder(passwordEncoder);
    	return provider;
    }

}
