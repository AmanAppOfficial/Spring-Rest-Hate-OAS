package e.aman.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;
import e.aman.demo.models.User;
import e.aman.demo.repositories.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepo.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("User 404");
		}
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		return new org.springframework.security.core.userdetails.User(user.getUsername() , user.getPassword() , grantedAuthorities);
	}

}
