package com.msoft.security.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.msoft.security.jwt.security.MyUserDetails;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private MyUserDetails myUserDetails;
	@Autowired
	private JwtAuthenticationFilter jwtAuthFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(request -> {
					request.requestMatchers("/home", "/signup", "/authenticate").permitAll();
					request.requestMatchers("/user/**").hasRole("USER");
					request.requestMatchers("/admin/**").hasRole("ADMIN");
					request.anyRequest().authenticated();
				})
				.formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return myUserDetails;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(passwordEncoder());
		provider.setUserDetailsService(myUserDetails);
		return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(authenticationProvider());
	}
	
}
