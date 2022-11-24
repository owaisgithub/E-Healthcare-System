package com.healthcare.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.healthcare.models.Patient;
import com.healthcare.repositories.PatientRepository;


@Service
public class PatientServiceImpl implements PatientService {
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public Patient createPatient(Patient patient) {
		patient.setPassword(passwordEncoder.encode(patient.getPassword()));
		return patientRepository.save(patient);
	}
	
	public Patient getPatientById(int id) {
		Optional<Patient> optional = patientRepository.findById(id);
		Patient patient = null;
		
		if(optional.isPresent())
		{
			patient = optional.get();
		}else {
			throw new RuntimeException("Patient in not found");
		}
		
		return patient;
	}

}
