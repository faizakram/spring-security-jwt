package com.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.model.Employee;

public interface IEmployeeRepository extends JpaRepository<Employee, Long> {

	@Query("select e1 from Employee e1 where e1.createdon = (select max(e2.createdon) from "
			+ "Employee e2 where e1.department.id = e2.department.id)")
	public List<Employee> getRecentEmployeeAsPerDepartment();

}
