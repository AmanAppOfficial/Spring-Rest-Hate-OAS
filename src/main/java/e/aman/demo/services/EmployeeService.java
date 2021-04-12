package e.aman.demo.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import e.aman.demo.controllers.EmpController;
import e.aman.demo.exceptions.CustomNullPointerException;
import e.aman.demo.models.Employee;
import e.aman.demo.repositories.EmployeeRepo;

@Service
public class EmployeeService {

	
	@Autowired
	EmployeeRepo empRepo;
	
	
	
	public ResponseEntity<CollectionModel<Employee>> getEmployeesService(Pageable page){
		
		
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
	
		return ResponseEntity.ok().body(CollectionModel.of(list , selfLink , rootLink));
	}

	
	
	@CacheEvict("employee")
	public Employee removeEmployeeById(Integer empId) {
		Employee e =  getEmployeeByIdService(empId);
		try {
		empRepo.deleteById(empId);
		}
		catch (Exception ee) {
			throw new CustomNullPointerException("Not Found Custom Message");
		}
		return e;
	}
	
	
	@Cacheable("employee")
	public Employee getEmployeeByIdService(Integer empId){
		
		System.out.println("false");
		
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
