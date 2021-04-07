package e.aman.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import e.aman.demo.models.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	User findByUsername(String username);
	
}
