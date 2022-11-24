package com.healthcare.services;

import com.healthcare.models.Patient;

public interface PatientService {
	
	public Patient createPatient(Patient patient);
	public Patient getPatientById(int id);
}
