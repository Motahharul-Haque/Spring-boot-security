package com.spring.security.auth;

import static com.spring.security.securityconfig.ApplicationUserRole.ADMIN;
import static com.spring.security.securityconfig.ApplicationUserRole.ADMINTRAINEE;
import static com.spring.security.securityconfig.ApplicationUserRole.STUDENT;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao{

	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
		super();
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
		
		return getApplicationUsers()
				.stream()
				.filter(applicationUser -> username.equals(applicationUser.getUsername()))
				.findFirst();
	}
	
	private List<ApplicationUser> getApplicationUsers()
	{
		List<ApplicationUser> applicationUsers = Lists.newArrayList(	
				new ApplicationUser(
						STUDENT.getGrantedAuthorities(),
						passwordEncoder.encode("password"),
						"Michael Faraday",
						true, 
						true, 
						true, 
						true),
				new ApplicationUser(
						ADMIN.getGrantedAuthorities(),
						passwordEncoder.encode("password"),
						"Linda",
						true, 
						true, 
						true, 
						true),
				new ApplicationUser(
						ADMINTRAINEE.getGrantedAuthorities(),
						passwordEncoder.encode("password"),
						"Tom",
						true, 
						true, 
						true, 
						true)
				);
		
		return applicationUsers;
	}

}
