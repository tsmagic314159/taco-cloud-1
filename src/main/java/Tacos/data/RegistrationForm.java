package Tacos.data;

import org.springframework.security.crypto.password.PasswordEncoder;

import Tacos.User;
import lombok.Data;

@Data
public class RegistrationForm {

	private String username;
	private String password;
	private String fullname;
	private String street;
	private String city;
	private String state;
	private String zip;
	private String phone;
	
	//passwordEncoder用于加密密码并传输到数据库中
	public User toUser(PasswordEncoder passwordEncoder) {
		return new User(
			username, passwordEncoder.encode(password),
			fullname, street, city, zip, phone);
	}
}
