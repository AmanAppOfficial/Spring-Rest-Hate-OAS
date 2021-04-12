package e.aman.demo.controllers;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import e.aman.demo.exceptions.CustomNullPointerException;
import e.aman.demo.models.Employee;
import e.aman.demo.repositories.EmployeeRepo;
import e.aman.demo.services.EmployeeService;

@RestController
@RequestMapping("api")
public class EmpController {
	 
	@Autowired
	EmployeeRepo empRepo;
	
	@Autowired
	EmployeeService empService;
	
	
	/*
	 * To get the list of all employees
	 * */
	
	
	/*
	 * Pagination support where pageSize defines the 
	 * number of objects and pageNumber defines the Elements of which page
	 * */
	@GetMapping("/employees")
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public ResponseEntity<CollectionModel<Employee>> getEmployees(Pageable page) {
		
		return empService.getEmployeesService(page);
		
	}
	
	 

	/*
	 * To get the employee by id
	 * */
	@GetMapping("/employee/{empId}")
	public Employee getEmployeeById(@PathVariable Integer empId) {
		return empService.getEmployeeByIdService(empId);
	
	}
	
	
	
	@DeleteMapping("employee/{empId}")
	public Employee deleteEmployeeById(@PathVariable Integer empId) {
		return empService.removeEmployeeById(empId);
	}
	

}
