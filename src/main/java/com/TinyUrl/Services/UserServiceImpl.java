package com.TinyUrl.Services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.TinyUrl.Models.User;
import com.TinyUrl.Repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	@Override
	public User getByEmail(String email) {
		return userRepo.findOne(email);
	}

	@Override
	public User save(User user) {
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		Set<String> set = new HashSet<String>();
		set.add("USER");
		user.setRoles(set);
		userRepo.save(user);
		return user;
	}

	@Override
	public Boolean isUser(String email) {
		return userRepo.exists(email);
	}
	
	
}
