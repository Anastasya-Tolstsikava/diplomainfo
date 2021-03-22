package by.bntu.diplomainformationproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDto {
    private String status;
    private String message;
    private Map<String, List<String>> errors;

    public ErrorDto(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
