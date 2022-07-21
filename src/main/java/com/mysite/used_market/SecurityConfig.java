package com.mysite.used_market;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.mysite.used_market.User.UserSecurityService;

import lombok.RequiredArgsConstructor;

@EnableGlobalMethodSecurity(prePostEnabled=true)
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final UserSecurityService userSecurityService;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/**").permitAll()
		.and()
		 .csrf().ignoringAntMatchers("/h2-console/**")
		.and()
		  .headers()
		  .addHeaderWriter(new XFrameOptionsHeaderWriter(
				   XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
		.and()
		  .formLogin()
		  .loginPage("/user/login")
		  .defaultSuccessUrl("/")
		.and()
		   .logout()
		   .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
		   .logoutSuccessUrl("/")
		   .invalidateHttpSession(true)
		;
		
	
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	}
	
	//스프링 시큐리티의 인증을 담당한다. 
	//AuthenticationManager 빈 생성시 스프링의 내부 동작으로 인해 위에서 작성한 UserSecurityService와 PasswordEncoder가 자동으로 설정된다.
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			 throws Exception 
	 {		 
	    return authenticationConfiguration.getAuthenticationManager();
	 }
	
	
}
