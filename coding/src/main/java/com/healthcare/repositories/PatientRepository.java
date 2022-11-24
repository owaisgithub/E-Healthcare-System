package com.healthcare.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthcare.models.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
	
	public Patient findByEmail(String email);
	
}
