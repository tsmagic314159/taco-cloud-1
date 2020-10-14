package Tacos.data;

import org.springframework.data.repository.CrudRepository;

import Tacos.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
	User findByUsername(String username);
	
}
