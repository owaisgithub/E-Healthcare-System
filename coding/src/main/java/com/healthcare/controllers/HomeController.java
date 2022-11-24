package com.healthcare.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.healthcare.models.Doctor;
import com.healthcare.models.Patient;
import com.healthcare.models.User;
import com.healthcare.services.DoctorService;
import com.healthcare.services.PatientService;
import com.healthcare.services.UserService;

@Controller
public class HomeController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private PatientService patientService; 
	
	@GetMapping("/")
	public String index()
	{
		return "index";
	}
	
	@GetMapping("/registerPatient")
	public String register()
	{
		return "register";
	}
	
	@GetMapping("/registerDoctor")
	public String registerDoctor()
	{
		return "register-doctor";
	}
	
	@GetMapping("/signin")
	public String login()
	{
		return "login";
	}
	
	@PostMapping("/createDoctor")
	public String createDoctor(@ModelAttribute Doctor doctor, HttpSession session)
	{
		if(userService.checkEmail(doctor.getEmail()))
		{
			session.setAttribute("msg", "Email is Already exists");
		}
		else
		{
			User u = new User();
			u.setEmail(doctor.getEmail());
			u.setPassword(doctor.getPassword());
			u.setRole("ROLE_DOCTOR");
			
			User u2 = userService.createUser(u);
			Doctor d = doctorService.createDoctor(doctor);
			
			if(d != null && u2 != null)
			{
				session.setAttribute("msg", "Register Successfully");
			}
			else
			{
				//System.out.println("Failed");
				session.setAttribute("msg", "Something wrong on server");
			}
		}
		
		
		
		return "redirect:/registerDoctor";
	}
	
	@PostMapping("/createPatient")
	public String createUser(@ModelAttribute Patient patient, HttpSession session, Model model)
	{
		//System.out.println(user.getName());
		if(userService.checkEmail(patient.getEmail()))
		{
			session.setAttribute("msg", "Email is Already exists");
		}
		else
		{
			User u = new User();
			u.setEmail(patient.getEmail());
			u.setPassword(patient.getPassword());
			u.setRole("ROLE_USER");
			
			User u2 = userService.createUser(u);
			Patient p = patientService.createPatient(patient);
			
			//User u = userService.createUser(user);
			if(u2 != null && p != null)
			{
				//System.out.println("Success");
				session.setAttribute("msg", "Register Successfully");
			}
			else
			{
				//System.out.println("Failed");
				session.setAttribute("msg", "Something wrong on server");
			}
		}
		
		model.addAttribute("msg", "Registration Successfull");
		
		return "redirect:/registerPatient";
	}
}
