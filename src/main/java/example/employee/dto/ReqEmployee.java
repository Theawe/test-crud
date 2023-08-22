package example.employee.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReqEmployee {
    @NotBlank(message = "Employee name is required")
    private String employeeName;

    @NotNull(message = "Employee salary is required")
    @Positive(message = "Employee salary must be positive")
    private Integer employeeSalary;

    @NotNull(message = "Employee age is required")
    @Positive(message = "Employee age must be positive")
    private Integer employeeAge;
}
