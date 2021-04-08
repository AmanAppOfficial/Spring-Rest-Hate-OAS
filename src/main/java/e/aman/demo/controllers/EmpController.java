package e.aman.demo.controllers;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import e.aman.demo.exceptions.CustomNullPointerException;
import e.aman.demo.models.Employee;
import e.aman.demo.repositories.EmployeeRepo;

@RestController
@RequestMapping("api")
public class EmpController {
	
	
	@Autowired
	EmployeeRepo empRepo;
	
	
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
		Page<Employee> list =  empRepo.findAll(page);
		
		for(Employee e : list) {
			/**
			 * Add self link to every employee object 
			 * **/
		Link selfLink = linkTo(methodOn(EmpController.class).getEmployeeById(e.getEmpId())).withSelfRel();
		e.add(selfLink);
		}
		
		Link selfLink = linkTo(methodOn(EmpController.class).getEmployees(page)).withSelfRel();
		Link rootLink = linkTo(EmpController.class).withRel("root");
		return ResponseEntity.ok().cacheControl(CacheControl.maxAge(600, TimeUnit.SECONDS)).body(CollectionModel.of(list , selfLink , rootLink));
	}
	
	 

	/*
	 * To get the employee by id
	 * */
	@GetMapping("/employee/{empId}")
	public Employee getEmployeeById(@PathVariable Integer empId) {
		Optional<Employee> e =  empRepo.findById(empId);
		
		
		
		 if(!e.isPresent()) {
			throw new CustomNullPointerException("Not Found Custom Message");
		}
		 
		 Employee e1 = new Employee();
		 e1.setEmpId(e.get().getEmpId());
		 e1.seteName(e.get().geteName());
		 e1.seteDept(e.get().geteDept());
		
		 /**
		 * Add self link to employee object 
		 * **/
		 Link selfLink = linkTo(methodOn(EmpController.class).getEmployeeById(e1.getEmpId())).withSelfRel();
			e1.add(selfLink);
		return e1;
	}
	
	

}
