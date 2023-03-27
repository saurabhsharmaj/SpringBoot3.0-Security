package com.example.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	@Autowired
	private UserDetailsService uds;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
		.csrf(AbstractHttpConfigurer::disable)
		.authorizeHttpRequests()
//		.requestMatchers(OPTIONS).permitAll() 
		
		.requestMatchers("/home","/register").permitAll()
		.requestMatchers("/welcome").authenticated()
		.requestMatchers("/api/users").hasAuthority("NORMAL")
		.requestMatchers("/mgr").hasAuthority("Manager")
		.requestMatchers("/emp").hasAuthority("Employee")
		.requestMatchers("/hr").hasAuthority("HR")
		.requestMatchers("/common").hasAnyAuthority("Employeee,Manager,Admin")
		.anyRequest().authenticated()
		
		.and()
		.formLogin().disable()
		//.defaultSuccessUrl("/welcome",true)
		
		//.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		
		.and()
		.exceptionHandling()
		.accessDeniedPage("/accessDenied")
		
		.and()
		.authenticationProvider(authenticationProvider())
		.httpBasic();
		return http.build();
		
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(uds);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		return authenticationProvider;
	}
}
