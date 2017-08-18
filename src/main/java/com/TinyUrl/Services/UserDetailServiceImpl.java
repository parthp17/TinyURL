package com.TinyUrl.Services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.TinyUrl.Models.Role;
import com.TinyUrl.Models.User;

@Service
public class UserDetailServiceImpl implements UserDetailsService{

	@Autowired
	UserService userservice;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userservice.getByEmail(email);
		Set<GrantedAuthority> grantedAuthority =  new HashSet<>();
		for(String role : user.getRoles())
			grantedAuthority.add(new SimpleGrantedAuthority(role));
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),grantedAuthority);
	}
	
}