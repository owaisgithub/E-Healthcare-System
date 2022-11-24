package com.healthcare.services;

import java.util.List;

import com.healthcare.models.Doctor;

public interface DoctorService {
	
	public Doctor createDoctor(Doctor doctor);
	
	public List<Doctor> getAllDoctor();
	
	Doctor getById(int id);
	
}
