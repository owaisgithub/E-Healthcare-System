package com.healthcare.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.healthcare.models.Doctor;
import com.healthcare.repositories.DoctorRepository;

@Service
public class DoctorServiceImpl implements DoctorService {
	
	@Autowired
	private DoctorRepository doctorRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public Doctor createDoctor(Doctor doctor) {
		doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
		return doctorRepo.save(doctor);
	}

	@Override
	public List<Doctor> getAllDoctor() {
		// TODO Auto-generated method stub
		return doctorRepo.findAll();
	}

	@Override
	public Doctor getById(int id) {
		Optional<Doctor> optional = doctorRepo.findById(id);
		Doctor doctor = null;
		
		if(optional.isPresent()){
			doctor = optional.get();
		}else {
			throw new RuntimeException("Doctor not found for id" + id);
		}
		return doctor;
	}
	
	
}
