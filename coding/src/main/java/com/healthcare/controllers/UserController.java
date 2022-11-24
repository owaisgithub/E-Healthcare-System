package com.healthcare.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.healthcare.models.Appointment;
import com.healthcare.models.Doctor;
import com.healthcare.models.Patient;
import com.healthcare.repositories.AppointmentRepository;
import com.healthcare.repositories.DoctorRepository;
//import com.healthcare.models.User;
import com.healthcare.repositories.PatientRepository;
import com.healthcare.services.DoctorService;
//import com.healthcare.repositories.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private AppointmentRepository appRepo;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@ModelAttribute
	private void userDetails(Model m, Principal p)
	{
		String email = p.getName();
		Patient patient = patientRepository.findByEmail(email);
		m.addAttribute("user", patient);	
	}
	
	@GetMapping("/")
	public String home()
	{
		return "user/home";
	}
	
	@GetMapping("/find-all-doctor")
	public String findAllDoctor(Model m)
	{
		List<Doctor> doctors = doctorService.getAllDoctor();
		
		m.addAttribute("doctor", doctors);
		
		return "user/doctors";
	}
	
	@GetMapping("/book-appointment/{id}")
	public String bookAppointment(@PathVariable (value = "id") int id, Model model, Principal p) {
		String email = p.getName();
		Patient patient = patientRepository.findByEmail(email);
		Doctor doctor = doctorService.getById(id);
		Appointment app = new Appointment();
		app.setPatientId(patient.getId());
		app.setDoctorId(doctor.getId());
		app.setStatus("Success");
//		HashMap<String, String> map = new HashMap<>();
//		map.put("dcotor", doctor.getName());
//		map.put("dcotorId", String.valueOf(doctor.getId()));
//		map.put("patientId", String.valueOf(patient.getId()));
//		map.put("status", "success");
		model.addAttribute("apps", app);
		model.addAttribute("name", doctor.getName());
		//model.addAllAttributes(map);
		
		return "user/book-appointment";
	}
	
	@PostMapping("/booked")
	public String appointmentBooked(@ModelAttribute Appointment app)
	{
		app.setStatus("Null");
		appRepo.save(app);
		return "redirect:/user/view-appointments";	
	}
	
	@GetMapping("/view-appointments")
	public String viewAppointment(Model model, Principal p){
		String email = p.getName();
		Patient patient = patientRepository.findByEmail(email);
		List<Appointment> app = appRepo.findByPatientId(patient.getId());
		
		List<HashMap<String, String>> list = new ArrayList<>();
		
		for(int i = 0; i < app.size(); i++)
		{
			HashMap<String, String> map = new HashMap<>();
			Appointment a = app.get(i);
			Doctor doctor = doctorService.getById(a.getDoctorId());
			map.put("dname", doctor.getName());
			map.put("dsp", doctor.getSpecialization());
			map.put("date", a.getAppDate());
			map.put("time", a.getAppTime());
			map.put("status", a.getStatus());
			list.add(map);
		}
		
		model.addAttribute("apps", list);
		
		return "user/view-appointments";
	}
	
	@GetMapping("/view-profile")
	public String viewProfile(Model model, Principal p)
	{
		String email = p.getName();
		Patient patient = patientRepository.findByEmail(email);
		model.addAttribute("patient", patient);
		
		return "user/view-profile";
	}
	
}
