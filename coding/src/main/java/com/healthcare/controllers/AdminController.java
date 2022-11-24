package com.healthcare.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.healthcare.models.Doctor;
import com.healthcare.models.LabReports;
import com.healthcare.models.Patient;
import com.healthcare.models.User;
import com.healthcare.repositories.DoctorRepository;
import com.healthcare.repositories.LabReportsRepository;
import com.healthcare.repositories.PatientRepository;
import com.healthcare.repositories.UserRepository;
import com.healthcare.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private LabReportsRepository labReportsRepo;
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	private void userDetails(Model m, Principal p)
	{
		String email = p.getName();
		User user = userRepo.findByEmail(email);
		m.addAttribute("user", user);	
	}
	
	@GetMapping("/")
	public String home()
	{
		return "admin/home";
	}
	
	@GetMapping("/view-patient")
	public String viewPatient(Model model)
	{
		List<Patient> patients = patientRepository.findAll();
		
		model.addAttribute("patients", patients);
		
		return "admin/patients";
	}
	
	@GetMapping("/view-doctor")
	public String viewDoctor(Model model)
	{
		List<Doctor> doctors = doctorRepository.findAll();
		
		model.addAttribute("doctors", doctors);
		
		return "admin/doctors";
	}
	
	@GetMapping("/add-admin")
	public String addAdminForm()
	{
		return "admin/admin-form";
	}
	
	@PostMapping("/admin-added")
	public String addAdmin(@ModelAttribute User user, HttpSession session, Model model) {
		if(userService.checkEmail(user.getEmail()))
		{
			session.setAttribute("msg", "Email is Already exists");
		}
		else
		{
			User u = new User();
			u.setRole("ROLE_ADMIN");
			User u1 = userService.createUser(u);
			
			//User u = userService.createUser(user);
			if(u1 != null)
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
		
		return "redirect:/admin/";
	}
	
	@GetMapping("/add-report")
	public String addReport(Model model) {
		LabReports labReports = new LabReports();
		model.addAttribute("labReports", labReports);
		return "/admin/add-reports";
	}
	
	@PostMapping("/report-added")
	public String reportAdded(@ModelAttribute LabReports labReports) {
		System.out.println(labReports.getReportName());
		System.out.println(labReports.getImage());
		return "redirect:/doctor/add-report";
	}
}
