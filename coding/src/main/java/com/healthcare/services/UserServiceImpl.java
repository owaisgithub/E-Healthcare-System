package com.healthcare.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.healthcare.models.User;
import com.healthcare.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public User createUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		//user.setRole("ROLE_ADMIN");
		return userRepo.save(user);
	}
	
	@Override
	public boolean checkEmail(String email) {
		return userRepo.existsByEmail(email);
	}
}
