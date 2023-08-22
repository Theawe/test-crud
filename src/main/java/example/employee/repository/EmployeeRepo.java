package example.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import example.employee.model.Employee;
import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
    List<Employee> findByEmployeeName(String employeeName);
}
