package e.aman.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RestHateaosApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestHateaosApplication.class, args);
	}

}
