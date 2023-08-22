package example.employee.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import example.employee.dto.DataResponseBuilder;
import example.employee.dto.ReqEmployee;
import example.employee.model.Employee;
import example.employee.repository.EmployeeRepo;

@RestController
@RequestMapping("api/v1")
public class EmployeeController {

    private EmployeeRepo employeeRepo;

    public EmployeeController(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @GetMapping("/employees")
    public ResponseEntity<Object> getAllEmployees() {
        List<Employee> employees = employeeRepo.findAll();
        DataResponseBuilder<List<Employee>> response = DataResponseBuilder.<List<Employee>>builder()
                .data(employees)
                .status(HttpStatus.OK)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/employee/{nik}")
    public ResponseEntity<Object> getEmployeeByNik(@PathVariable(name = "nik") Integer nik) {
        Employee employee = employeeRepo.findById(nik).orElse(null);
        String message = "Employee Not Found";
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        DataResponseBuilder<Employee> response = DataResponseBuilder.<Employee>builder()
                .data(employee)
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/employee/search")
    public ResponseEntity<Object> getEmployeeByName(@RequestParam String name) {
        List<Employee> employees = employeeRepo.findByEmployeeName(name);
        DataResponseBuilder<List<Employee>> response = DataResponseBuilder.<List<Employee>>builder()
                .data(employees)
                .status(HttpStatus.OK)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/employee/create")
    public ResponseEntity<Object> createEmployee(@RequestBody ReqEmployee request) {

        Employee employee = Employee.builder()
                .employeeName(request.getEmployeeName())
                .employeeSalary(request.getEmployeeSalary())
                .employeeAge(request.getEmployeeAge())
                .build();
        employee = employeeRepo.save(employee);

        DataResponseBuilder<Employee> response = DataResponseBuilder.<Employee>builder()
                .data(employee)
                .status(HttpStatus.CREATED)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/employee/update/{nik}")
    public ResponseEntity<Object> updateEmployee(@PathVariable(name = "nik") Integer nik,
            @RequestBody ReqEmployee request) {
        Employee employee = employeeRepo.findById(nik).orElse(null);
        String message = "Employee Not Found";
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);

        }
        employee.setEmployeeAge(request.getEmployeeAge());
        employee.setEmployeeName(request.getEmployeeName());
        employee.setEmployeeSalary(request.getEmployeeSalary());
        employeeRepo.save(employee);

        DataResponseBuilder<Employee> response = DataResponseBuilder.<Employee>builder()
                .data(employee)
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/employee/delete/{nik}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable(name = "nik") Integer nik) {
        Employee employee = employeeRepo.findById(nik).orElse(null);
        HashMap<String, String> result = new HashMap<>();
        result.put("Message", "successfully! deleted Records");
        String message = "Employee Not Found";
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        employeeRepo.delete(employee);
        return ResponseEntity.ok(result);
    }
}
