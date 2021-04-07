package e.aman.demo.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.springframework.hateoas.*;


@Entity
public class Employee extends RepresentationModel<Employee> {
	
	@Id
	private Integer empid;   
	private String ename;
	private String edept;
	
	
	public Employee() {}
	
	public Employee(int empid, String ename, String edept) {
		super();
		this.empid = empid;
		this.ename = ename;
		this.edept = edept;
	}
	
	public Integer getEmpId() {
		return empid;
	}
	public void setEmpId(Integer empid) {
		this.empid = empid;
	}
	public String geteName() {
		return ename;
	}
	public void seteName(String ename) {
		this.ename = ename;
	}
	public String geteDept() {
		return edept;
	}
	public void seteDept(String edept) {
		this.edept = edept;
	}
	
	@Override
	public String toString() {
		return "Employee [empId=" + empid + ", eName=" + ename + ", eDept=" + edept + "]";
	}
	

}
