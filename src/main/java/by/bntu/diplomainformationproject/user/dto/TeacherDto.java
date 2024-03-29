package by.bntu.diplomainformationproject.user.dto;


import by.bntu.diplomainformationproject.user.annotation.UniqueEmail;
import by.bntu.diplomainformationproject.user.entity.Role;
import by.bntu.diplomainformationproject.user.entity.Student;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.List;

import static by.bntu.diplomainformationproject.util.Constants.EMAIL_PATTERN_MSG;
import static by.bntu.diplomainformationproject.util.Constants.NAME_MAX_SIZE;
import static by.bntu.diplomainformationproject.util.Constants.NAME_MIN_SIZE;
import static by.bntu.diplomainformationproject.util.Constants.NAME_PATTERN_MSG;
import static by.bntu.diplomainformationproject.util.Constants.NAME_SIZE_MSG;
import static by.bntu.diplomainformationproject.util.Constants.NOT_NULL_OR_EMPTY_MSG;
import static by.bntu.diplomainformationproject.util.Constants.PASSWORD_MAX_SIZE;
import static by.bntu.diplomainformationproject.util.Constants.PASSWORD_MIN_SIZE;
import static by.bntu.diplomainformationproject.util.Constants.PASSWORD_PATTERN_MSG;
import static by.bntu.diplomainformationproject.util.Constants.PASSWORD_SIZE_MSG;
import static by.bntu.diplomainformationproject.util.Constants.REGEX_FOR_EMAIL;
import static by.bntu.diplomainformationproject.util.Constants.REGEX_FOR_NAME;
import static by.bntu.diplomainformationproject.util.Constants.REGEX_FOR_PASSWORD;
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

    private Boolean isConfirmed;

   // @Builder.Default
    private Role role = Role.TEACHER;

    private List<Student> students;
}
