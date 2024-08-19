package com.msoft.security.jwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.msoft.security.jwt.dao.UserRepository;
import com.msoft.security.jwt.model.User;

@Service
public class MyUserDetails implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userObj = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		return org.springframework.security.core.userdetails.User.builder()
				.username(username)
				.password(userObj.getPassword())
				.roles(getRoles(userObj))
				.build();
	}
	
	private String[] getRoles(User user) {
		if(user.getRoles() == null) {
			return new String[] { "USER" };
		}
		return user.getRoles().split(",");
	}

}
