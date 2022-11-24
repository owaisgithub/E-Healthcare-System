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
import com.healthcare.models.User;
import com.healthcare.repositories.AppointmentRepository;
import com.healthcare.repositories.DoctorRepository;
import com.healthcare.repositories.UserRepository;
import com.healthcare.services.DoctorService;
import com.healthcare.services.PatientService;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AppointmentRepository appRepo;
	
	@Autowired
	private DoctorRepository docRepo;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private DoctorService doctorService;
	
	@ModelAttribute
	private void userDetails(Model m, Principal p)
	{
		String email = p.getName();
		User user = userRepo.findByEmail(email);
		m.addAttribute("user", user);	
	}
	
	@GetMapping("/")
	public String home(Model model, Principal p)
	{
		String email = p.getName();
		Doctor doctor = docRepo.findByEmail(email);
		List<Appointment> app = appRepo.findByDoctorId(doctor.getId());
		System.out.println(app.size());
		
		List<HashMap<String, String>> list = new ArrayList<>();
		
		for(int i = 0; i < app.size(); i++)
		{
			HashMap<String, String> map = new HashMap<>();
			Appointment a = app.get(i);
			if(a.getStatus().equals("Null")) {
				
				Patient patient = patientService.getPatientById(a.getPatientId());
				map.put("id", Integer.toString(a.getId()));
				map.put("pName", patient.getName());
				map.put("date", a.getAppDate());
				map.put("time", a.getAppTime());
				list.add(map);
			}
		}
		
		model.addAttribute("apps", list);
		return "doctor/home";
	}
	
	@ModelAttribute
	@GetMapping("/appointments")
	public String viewAppointmentDetails(Model model, Principal p) {
		String email = p.getName();
		Doctor doctor = docRepo.findByEmail(email);
		List<Appointment> app = appRepo.findByDoctorId(doctor.getId());
		System.out.println(app.size());
		
		List<HashMap<String, String>> list = new ArrayList<>();
		
		for(int i = 0; i < app.size(); i++)
		{
			HashMap<String, String> map = new HashMap<>();
			Appointment a = app.get(i);
			System.out.println(a.getStatus());
			if(a.getStatus().equals("Success")) {
				Patient patient = patientService.getPatientById(a.getPatientId());
				map.put("pName", patient.getName());
				map.put("date", a.getAppDate());
				map.put("time", a.getAppTime());
				map.put("status", a.getStatus());
				list.add(map);
			}
		}
		
		model.addAttribute("apps", list);
		
		return "doctor/appointments";
	}
	
	@GetMapping("/appointment-acc/{id}")
	public String acceptApp(@PathVariable (value = "id") int id)
	{
		Appointment app = appRepo.findById(id);
		
		app.setStatus("Success");
		
		appRepo.save(app);
		
		return "redirect:/";
	}
	
	@GetMapping("/appointment-rej/{id}")
	public String rejectApp(@PathVariable (value = "id") int id)
	{
		Appointment app = appRepo.findById(id);
		
		app.setStatus("Reject");
		
		appRepo.save(app);
		
		return "redirect:/";
	}
	
	
	@GetMapping("/view-profile")
	public String viewProfile(Model model, Principal p)
	{
		String email = p.getName();
		Doctor doctor = docRepo.findByEmail(email);
		model.addAttribute("doctor", doctor);
		
		return "doctor/view-profile";
	}
	
//	@GetMapping("/update-profile/{id}")
//	public String showUpdateForm(@PathVariable(value = "id") int id, Model model) {
//		// get doctor 
//		Doctor doctor = doctorService.getById(id);
//		
//		model.addAttribute("doctor", doctor);
//		return "doctor/update-doctor";
//	}
//	
//	@PostMapping("/update-doctor")
//	public String updateDoctor(@ModelAttribute Doctor doctor) {
//		Doctor d = doctorService.createDoctor(doctor);
//		
//		return "redirect:/doctor/view-profile";
//	}
}
