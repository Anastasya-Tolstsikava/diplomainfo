package by.bntu.diplomainformationproject.user.service;

import by.bntu.diplomainformationproject.service.Service;
import by.bntu.diplomainformationproject.user.dto.TeacherDto;

import java.util.List;

public interface TeacherService extends Service<TeacherDto, Long> {
    List<TeacherDto> findAll();

    boolean existsByEmail(String email);
}
