package com.spring.security.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.spring.security.auth.ApplicationUserService;
import com.spring.security.jwt.JwtConfig;
import com.spring.security.jwt.JwtTokenVerifier;
import com.spring.security.jwt.JwtUsernameAndPasswordAuthenticationFilter;

import static com.spring.security.securityconfig.ApplicationUserRole.*;

import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import static com.spring.security.securityconfig.ApplicationUserPermission.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{

	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserService applicationUserService;
	private final JwtConfig jwtConfig;
	private final SecretKey secretKey;
	
	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService
			,JwtConfig jwtConfig, SecretKey secretKey) {
		this.applicationUserService = applicationUserService;
		this.passwordEncoder = passwordEncoder;
		this.jwtConfig = jwtConfig;
		this.secretKey = secretKey;
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
		.addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtUsernameAndPasswordAuthenticationFilter.class)
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
		.authenticated();
		//.and()
		//.httpBasic();
		/*.formLogin()
		.loginPage("/login")
		.permitAll()
		.passwordParameter("password")
		.usernameParameter("username")
		.defaultSuccessUrl("/courses", true)
		.and()
		.rememberMe()
			.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
					.key("Somethingsecret")
					.rememberMeParameter("remember-me")
		.and()
		.logout()
		.logoutUrl("/logout")
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
		.clearAuthentication(true)
		.invalidateHttpSession(true)
		.deleteCookies("JSESSIONID", "remember-me")
		.logoutSuccessUrl("/login");*/
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)throws Exception
	{
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider()
	{
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();	
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(applicationUserService);
		return provider;
	}

	/*
	 * @Override
	 * 
	 * @Bean protected UserDetailsService userDetailsService() {
	 * 
	 * UserDetails michelUser = User.builder() .username("Michael Faraday")
	 * .password(passwordEncoder.encode("password")) //.roles(STUDENT.name())
	 * .authorities(STUDENT.getGrantedAuthorities()) .build();
	 * 
	 * UserDetails lindaUser = User.builder() .username("Linda")
	 * .password(passwordEncoder.encode("password123")) //.roles(ADMIN.name())
	 * .authorities(ADMIN.getGrantedAuthorities()) .build();
	 * 
	 * 
	 * UserDetails tomUser = User.builder() .username("Tom")
	 * .password(passwordEncoder.encode("password123"))
	 * //.roles(ADMINTRAINEE.name())
	 * .authorities(ADMINTRAINEE.getGrantedAuthorities()) .build();
	 * 
	 * return new InMemoryUserDetailsManager(michelUser, lindaUser,tomUser); }
	 */
	
	
}
