package e.aman.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import e.aman.demo.models.Employee;

public interface EmployeeRepo extends PagingAndSortingRepository<Employee , Integer>{
	
	
	
}
