package Tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Tacos.User;
import Tacos.data.UserRepository;

//stereotype中的一个，Spring boot会自动发现它并初始化为一个bean
@Service
public class UserRepositoryUserDetailsService implements UserDetailsService{

	private UserRepository userRepo;
	
	@Autowired
	public UserRepositoryUserDetailsService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	//规则：绝不能返回null
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if(user != null) {
			return user;
		}
		throw new UsernameNotFoundException("User '" + username + "' not found");
	}
	
}
