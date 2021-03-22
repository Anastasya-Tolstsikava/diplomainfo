package by.bntu.diplomainformationproject.user.dto;

import by.bntu.diplomainformationproject.user.annotation.UniqueEmail;
import by.bntu.diplomainformationproject.user.entity.Role;
import by.bntu.diplomainformationproject.user.entity.Student;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

import static by.bntu.diplomainformationproject.util.Constants.*;
import static by.bntu.diplomainformationproject.util.Constants.UNIQUE_EMAIL_MSG;

@Data
public class TeacherDto {
    private Long id;

    @NotBlank(message = NOT_NULL_OR_EMPTY_MSG)
    @Pattern(regexp = REGEX_FOR_NAME, message = NAME_PATTERN_MSG)
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE, message = NAME_SIZE_MSG)
    private String firstName;

    @NotBlank(message = NOT_NULL_OR_EMPTY_MSG)
    @Pattern(regexp = REGEX_FOR_NAME, message = NAME_PATTERN_MSG)
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE, message = NAME_SIZE_MSG)
    private String lastName;

    @NotBlank(message = NOT_NULL_OR_EMPTY_MSG)
    @Pattern(regexp = REGEX_FOR_EMAIL, message = EMAIL_PATTERN_MSG)
    @UniqueEmail(message = UNIQUE_EMAIL_MSG)
    private String email;

    @Pattern(regexp = REGEX_FOR_PASSWORD, message = PASSWORD_PATTERN_MSG)
    @Size(min = PASSWORD_MIN_SIZE, max = PASSWORD_MAX_SIZE, message = PASSWORD_SIZE_MSG)
    private String password;

   // @Builder.Default
    private Role role = Role.TEACHER;

    private List<Student> students;
}
