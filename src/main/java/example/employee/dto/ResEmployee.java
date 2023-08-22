package example.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResEmployee {
    private Integer id;
    private String employeeName;
    private Integer employeeSalary;
    private Integer employeeAge;
}
