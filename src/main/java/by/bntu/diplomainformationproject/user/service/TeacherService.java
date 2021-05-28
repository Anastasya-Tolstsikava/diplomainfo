package by.bntu.diplomainformationproject.user.service;

import by.bntu.diplomainformationproject.service.Service;
import by.bntu.diplomainformationproject.user.dto.IdDto;
import by.bntu.diplomainformationproject.user.dto.StudentDto;
import by.bntu.diplomainformationproject.user.dto.TeacherDto;

import java.util.List;

public interface TeacherService extends Service<TeacherDto, Long> {

    List<TeacherDto> findAll();

    boolean existsByEmail(String email);

    void confirmStudent(IdDto id);

    void confirmTeacher(IdDto id);

    List<StudentDto> findMyStudents();

    void sendMessage(String message);

    void addStudents(List<StudentDto> newStudents);

    void deleteFromMyStudents(StudentDto studentDto);
}
