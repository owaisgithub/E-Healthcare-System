package com.healthcare.services;

import com.healthcare.models.User;

public interface UserService {
	
	public User createUser(User user);
	
	public boolean checkEmail(String email);
}
