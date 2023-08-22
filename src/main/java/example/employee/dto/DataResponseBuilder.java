package example.employee.dto;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DataResponseBuilder<T extends Object> extends BaseResponseBuilder {
    private T data;

    @Builder
    public DataResponseBuilder(HttpStatus status, T data) {
        super(status);

        this.data = data;
    }
}
