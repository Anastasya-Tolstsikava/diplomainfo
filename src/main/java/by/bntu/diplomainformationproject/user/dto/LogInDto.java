package by.bntu.diplomainformationproject.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static by.bntu.diplomainformationproject.util.Constants.*;

@Data
public class LogInDto {

    @NotBlank(message = NOT_NULL_OR_EMPTY_MSG)
    @Pattern(regexp = REGEX_FOR_EMAIL, message = EMAIL_PATTERN_MSG)
    private String email;

    @NotBlank(message = NOT_NULL_OR_EMPTY_MSG)
    @Pattern(regexp = REGEX_FOR_PASSWORD, message = PASSWORD_PATTERN_MSG)
    @Size(min = PASSWORD_MIN_SIZE, max = PASSWORD_MAX_SIZE, message = PASSWORD_SIZE_MSG)
    private String password;
}
