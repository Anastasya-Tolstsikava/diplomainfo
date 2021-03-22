package by.bntu.diplomainformationproject.user.service;

import by.bntu.diplomainformationproject.service.Service;
import by.bntu.diplomainformationproject.user.dto.AuthToken;
import by.bntu.diplomainformationproject.user.dto.LogInDto;
import by.bntu.diplomainformationproject.user.dto.StudentDto;
import by.bntu.diplomainformationproject.user.dto.TeacherDto;

import java.util.List;

public interface StudentService extends Service<StudentDto, Long> {
    List<StudentDto> findAll();

    List<StudentDto> findByTeacher(TeacherDto teacherDto);

    boolean existsByEmail(String email);

    public AuthToken logIn(LogInDto logInDto);
}
