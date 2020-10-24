package Tacos.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Tacos.User;
import Tacos.data.RegistrationForm;
import Tacos.data.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegistrationController {
	private UserRepository userRepo;
	private PasswordEncoder passwordEncoder;
	
	public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}
	
	@GetMapping
	public String registerForm() {
		return "registration";
	}
	
	@PostMapping
	public String processRegistration(RegistrationForm form) {
		User user = form.toUser(passwordEncoder);
		System.out.println(user);
		userRepo.save(user);
		log.info("RegistrationForm: "+ form.toUser(passwordEncoder));
		return "redirect:/login";
	}
}
