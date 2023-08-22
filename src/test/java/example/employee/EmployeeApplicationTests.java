package example.employee;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import example.employee.controller.EmployeeController;
import example.employee.dto.DataResponseBuilder;
import example.employee.dto.ReqEmployee;
import example.employee.model.Employee;
import example.employee.repository.EmployeeRepo;

@SpringBootTest
class EmployeeApplicationTests {

	@Mock
	private EmployeeRepo employeeRepository;

	@InjectMocks
	private EmployeeController employeeController;

	@Test
	public void testGetAllEmployees() {
		// Membuat beberapa data dummy untuk pengujian
		List<Employee> dummyEmployees = new ArrayList<>();
		dummyEmployees.add(new Employee(1, "John", 100000, 21));
		dummyEmployees.add(new Employee(2, "Kevin", 200000, 21));

		// Mocking pemanggilan employeeRepository.findAll()
		when(employeeRepository.findAll()).thenReturn(dummyEmployees);

		// Memanggil metode yang akan diuji
		ResponseEntity<DataResponseBuilder<List<Employee>>> responseEntity = employeeController.getAllEmployees();

		// Verifikasi hasil
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		// Mengekstrak data dari response
		DataResponseBuilder<List<Employee>> responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertEquals(dummyEmployees, responseBody.getData());

		// Verifikasi pemanggilan repository
		verify(employeeRepository, times(1)).findAll();
	}

	@Test
	public void testGetEmployeeByNikFound() throws Exception {
		// Membuat data dummy untuk pengujian
		Employee dummyEmployee = new Employee(12345, "John", 100000, 21);
		Integer nik = 12345;

		// Mocking pemanggilan employeeRepository.findById()
		when(employeeRepository.findById(nik)).thenReturn(Optional.of(dummyEmployee));

		// Memanggil metode yang akan diuji
		ResponseEntity<DataResponseBuilder<Object>> responseEntity = employeeController.getEmployeeByNik(nik);

		// Verifikasi hasil
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		// Mengekstrak data dari response
		DataResponseBuilder<Object> responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertEquals(dummyEmployee, responseBody.getData());
	}

	@Test
	public void testGetEmployeeByNikNotFound() {
		Integer nik = 12345;

		// Mocking pemanggilan employeeRepository.findById()
		when(employeeRepository.findById(nik)).thenReturn(Optional.empty());

		// Memanggil metode yang akan diuji
		ResponseEntity<DataResponseBuilder<Object>> responseEntity = employeeController.getEmployeeByNik(nik);

		// Verifikasi hasil
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

		// Mengekstrak data dari response
		DataResponseBuilder<Object> responseBody = (DataResponseBuilder<Object>) responseEntity.getBody();
		assertNotNull(responseBody);
		assertNotNull(responseBody.getData());
	}

	@Test
	public void testGetEmployeeByName() {
		// Membuat data dummy untuk pengujian
		List<Employee> dummyEmployees = new ArrayList<>();
		dummyEmployees.add(new Employee(1, "John", 100000, 21));
		dummyEmployees.add(new Employee(2, "John", 200000, 21));

		String searchName = "John";

		// Mocking pemanggilan employeeRepository.findByEmployeeName()
		when(employeeRepository.findByEmployeeName(searchName)).thenReturn(dummyEmployees);

		// Memanggil metode yang akan diuji
		ResponseEntity<DataResponseBuilder<List<Employee>>> responseEntity = employeeController
				.getEmployeeByName(searchName);

		// Verifikasi hasil
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		// Mengekstrak data dari response
		DataResponseBuilder<List<Employee>> responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertEquals(dummyEmployees, responseBody.getData());
	}

	@Test
	public void testCreateEmployee() {
		// Membuat data dummy untuk pengujian
		ReqEmployee dummyRequest = new ReqEmployee("John Doe", 5000, 30);
		Employee dummyEmployee = new Employee(1, dummyRequest.getEmployeeName(), dummyRequest.getEmployeeSalary(),
				dummyRequest.getEmployeeAge());

		// Mocking pemanggilan employeeRepository.save()
		when(employeeRepository.save(any(Employee.class))).thenReturn(dummyEmployee);

		// Memanggil metode yang akan diuji
		ResponseEntity<DataResponseBuilder<Employee>> responseEntity = employeeController.createEmployee(dummyRequest);

		// Verifikasi hasil
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

		// Mengekstrak data dari response
		DataResponseBuilder<Employee> responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertEquals(dummyEmployee, responseBody.getData());
	}

	@Test
	public void testUpdateEmployee() {
		// Membuat data dummy untuk pengujian
		ReqEmployee dummyRequest = new ReqEmployee("John Doe", 5000, 30);
		Employee dummyEmployee = new Employee(12345, dummyRequest.getEmployeeName(), dummyRequest.getEmployeeSalary(),
				dummyRequest.getEmployeeAge());
		Integer nik = 12345;

		// Mocking pemanggilan employeeRepository.findById()
		when(employeeRepository.findById(nik)).thenReturn(Optional.of(dummyEmployee));
		// Mocking pemanggilan employeeRepository.save()
		when(employeeRepository.save(any(Employee.class))).thenReturn(dummyEmployee);

		// Memanggil metode yang akan diuji
		ResponseEntity<DataResponseBuilder<Object>> responseEntity = employeeController.updateEmployee(nik,
				dummyRequest);

		// Verifikasi hasil
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		// Mengekstrak data dari response
		DataResponseBuilder<Object> responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertEquals(dummyEmployee, responseBody.getData());
	}

	@Test
	public void testDeleteEmployeeSuccess() {
		Integer nik = 12345;
		Employee dummyEmployee = new Employee(nik, "John Doe", 5000, 30);

		// Mocking pemanggilan employeeRepository.findById()
		when(employeeRepository.findById(nik)).thenReturn(Optional.of(dummyEmployee));

		// Memanggil metode yang akan diuji
		ResponseEntity<HashMap<String, String>> responseEntity = employeeController.deleteEmployee(nik);

		// Verifikasi hasil
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		// Mengekstrak data dari response
		HashMap<String, String> responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertEquals("Successfully! deleted Records", responseBody.get("message"));

		// Verifikasi pemanggilan repository
		verify(employeeRepository, times(1)).delete(dummyEmployee);
	}
}
