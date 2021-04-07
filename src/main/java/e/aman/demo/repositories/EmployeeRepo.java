package e.aman.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import e.aman.demo.models.Employee;

public interface EmployeeRepo extends JpaRepository<Employee , Integer>{
	
}
