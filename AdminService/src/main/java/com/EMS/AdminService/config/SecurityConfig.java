package com.EMS.AdminService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.EMS.AdminService.security.JwtAuthEntryPoint;
import com.EMS.AdminService.security.JwtAuthenticationFilter;

@Configuration
@EnableWebMvc
public class SecurityConfig {


    @Autowired
    private JwtAuthEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;
//    @Autowired
//    private UserDetailsService userdetailservice;
//    
//    @Autowired
//    private PasswordEncoder passwordEncoder;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    	http.csrf(csrf -> csrf.disable())
    	.cors(cors->cors.disable())
    	.authorizeHttpRequests(auth->auth.
    			requestMatchers("/test").authenticated().
    			requestMatchers("/admin/login").permitAll()
    			.requestMatchers("/h2-console/*").permitAll()
    			.requestMatchers("/h2-console").permitAll()
    			.requestMatchers("/h2/**").permitAll()
//    			.requestMatchers("/admin/getStudent/*").permitAll()
    			.requestMatchers("/admin/enroll/**").permitAll()
    			.requestMatchers("/v3/api-docs").permitAll()
    			.requestMatchers("/v2/api-docs").permitAll()
    			.requestMatchers("/swagger-resources/**").permitAll()
    			.requestMatchers("/swagger-ui/**").permitAll()
    			.requestMatchers("/webjars/**").permitAll()
    			.requestMatchers("/admin/allcourses").permitAll()
    			.anyRequest().authenticated()
    			)
    	
    	.exceptionHandling(ex -> ex.authenticationEntryPoint(point))
    	.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    	;
    	http.headers(headers -> headers.frameOptions(FrameOptionsConfig::disable));
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//    	DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
//    	provider.setUserDetailsService(userdetailservice);
//    	provider.setPasswordEncoder(passwordEncoder);
//    	return provider;
//    }

}
