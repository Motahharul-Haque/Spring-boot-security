package com.spring.security.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import static com.spring.security.securityconfig.ApplicationUserRole.*;
import static com.spring.security.securityconfig.ApplicationUserPermission.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{

	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/", "index", "css/*", "/js/*").permitAll()
		.antMatchers("/api/**").hasRole(STUDENT.name())
				/*
				 * .antMatchers(HttpMethod.DELETE,
				 * "management/api/**").hasAuthority(COURSE_WRITE.getPermission())
				 * .antMatchers(HttpMethod.POST,
				 * "management/api/**").hasAuthority(COURSE_WRITE.getPermission())
				 * .antMatchers(HttpMethod.PUT,
				 * "management/api/**").hasAuthority(COURSE_WRITE.getPermission())
				 * .antMatchers(HttpMethod.GET, "management/api/**").hasAnyRole(ADMIN.name(),
				 * ADMINTRAINEE.name())
				 */
		.anyRequest()
		.authenticated()
		.and()
		//.httpBasic();
		.formLogin()
		.loginPage("/login").permitAll();
	}
	
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		
		UserDetails michelUser = User.builder()
		.username("Michael Faraday")
		.password(passwordEncoder.encode("password"))
		//.roles(STUDENT.name())
		.authorities(STUDENT.getGrantedAuthorities())
		.build();
		
		UserDetails lindaUser = User.builder()
				.username("Linda")
				.password(passwordEncoder.encode("password123"))
				//.roles(ADMIN.name())
				.authorities(ADMIN.getGrantedAuthorities())
				.build();
		

		UserDetails tomUser = User.builder()
				.username("Tom")
				.password(passwordEncoder.encode("password123"))
				//.roles(ADMINTRAINEE.name())
				.authorities(ADMINTRAINEE.getGrantedAuthorities())
				.build();
		
		return new InMemoryUserDetailsManager(michelUser, lindaUser,tomUser);
	}
	
	
}
