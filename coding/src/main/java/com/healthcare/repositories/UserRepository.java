package com.healthcare.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	public boolean existsByEmail(String email);
	
	public User findByEmail(String email);
}
