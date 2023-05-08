//package com.lexisnexis.tms.config;
//
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//	@Bean
//	public UserDetailsService userDetailsService() {
//		return new UserInfoDetails();
//		
//	}
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//		
//	} 
//	
//	@Bean
//	public AuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider authentication= new DaoAuthenticationProvider();
//		authentication.setUserDetailsService(userDetailsService());
//		authentication.setPasswordEncoder(passwordEncoder());
//		return authentication;
//	}
//	
//	
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//		
//		return http.csrf().disable().authorizeHttpRequests().
//				requestMatchers("/tms/api/work/addUser").permitAll()
//				.and().authorizeHttpRequests().requestMatchers("/tms/api/work/**").authenticated()
//				.and().formLogin().and().build();
//		
//	}
//	
//}
