package example.employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<DataResponseBuilder<List<Employee>>> getAllEmployees() {
        List<Employee> employees = employeeRepo.findAll();
        DataResponseBuilder<List<Employee>> response = DataResponseBuilder.<List<Employee>>builder()
                .data(employees)
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/employee/{nik}")
    public ResponseEntity<DataResponseBuilder<Object>> getEmployeeByNik(@PathVariable(name = "nik") Integer nik) {
        Optional<Employee> employeeOptional = employeeRepo.findById(nik);
        if (employeeOptional.isEmpty()) {
            String message = "Employee Not Found";
            HashMap<String, String> result = new HashMap<>();
            result.put("message", message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(DataResponseBuilder.<Object>builder()
                    .data(result)
                    .status(HttpStatus.NOT_FOUND)
                    .build());
        }
        Employee employee = employeeOptional.get();
        return ResponseEntity.ok(DataResponseBuilder.<Object>builder()
                .data(employee)
                .status(HttpStatus.OK)
                .build());
    }

    @GetMapping("/employee/search")
    public ResponseEntity<DataResponseBuilder<List<Employee>>> getEmployeeByName(@RequestParam String name) {
        List<Employee> employees = employeeRepo.findByEmployeeName(name);
        DataResponseBuilder<List<Employee>> response = DataResponseBuilder.<List<Employee>>builder()
                .data(employees)
                .status(HttpStatus.OK)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/employee/create")
    public ResponseEntity<DataResponseBuilder<Employee>> createEmployee(@RequestBody ReqEmployee request) {
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
    public ResponseEntity<DataResponseBuilder<Object>> updateEmployee(@PathVariable(name = "nik") Integer nik,
            @RequestBody ReqEmployee request) {
        Optional<Employee> employeeOptional = employeeRepo.findById(nik);

        if (employeeOptional.isEmpty()) {
            String message = "Employee Not Found";
            HashMap<String, String> result = new HashMap<>();
            result.put("message", message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(DataResponseBuilder.<Object>builder()
                    .data(result)
                    .status(HttpStatus.NOT_FOUND)
                    .build());
        }
        Employee employee = employeeOptional.get();
        employee.setEmployeeAge(request.getEmployeeAge());
        employee.setEmployeeName(request.getEmployeeName());
        employee.setEmployeeSalary(request.getEmployeeSalary());
        employeeRepo.save(employee);

        DataResponseBuilder<Object> response = DataResponseBuilder.<Object>builder()
                .data(employee)
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/employee/delete/{nik}")
    public ResponseEntity<HashMap<String, String>> deleteEmployee(@PathVariable(name = "nik") Integer nik) {
        Optional<Employee> employeeOptional = employeeRepo.findById(nik);
        HashMap<String, String> result = new HashMap<>();
        String message = "Successfully! deleted Records";
        result.put("message", message);
        if (employeeOptional.isEmpty()) {
            message = "Employee Not Found";
            result.put("message", message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        Employee employee = employeeOptional.get();
        employeeRepo.delete(employee);
        return ResponseEntity.ok(result);
    }

}
