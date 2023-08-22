package example.employee.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nik", unique = true, nullable = false)
    private Integer nik;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "employee_salary")
    private Integer employeeSalary;

    @Column(name = "employee_age")
    private Integer employeeAge;

}
