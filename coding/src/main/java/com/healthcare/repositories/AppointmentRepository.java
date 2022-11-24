package com.healthcare.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthcare.models.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer>{
	
	public List<Appointment> findByPatientId(int id);
	
	public List<Appointment> findByDoctorId(int id);
	
	public Appointment findById(int id);
}
