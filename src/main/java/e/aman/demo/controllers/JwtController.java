package e.aman.demo.controllers;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

import e.aman.demo.models.User;
import e.aman.demo.security.MyUserDetailsService;
import e.aman.demo.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Arrays;

@RestController
public class JwtController {
	
	private static final Object AUTHORITIES_KEY = null;

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private MyUserDetailsService userDetailService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@PostMapping("/login")
	public ResponseEntity<?> generateToken(@RequestBody User user){
		String token="";
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername() , user.getPassword()));
		}
		catch(BadRequest e){
			System.out.print("Bad Request");
		}

		
		UserDetails details = userDetailService.loadUserByUsername(user.getUsername());
		token = jwtUtil.generateToken(details);
		
		return ResponseEntity.ok().cacheControl(CacheControl.maxAge(600, TimeUnit.SECONDS)).body(token);
	}

}
