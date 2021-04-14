package by.bntu.diplomainformationproject.user.service.impl;

import by.bntu.diplomainformationproject.service.exception.EntryIsNotFoundException;
import by.bntu.diplomainformationproject.user.dto.IdDto;
import by.bntu.diplomainformationproject.user.dto.TeacherDto;
import by.bntu.diplomainformationproject.user.dto.mapper.TeacherMapper;
import by.bntu.diplomainformationproject.user.entity.Student;
import by.bntu.diplomainformationproject.user.entity.Teacher;
import by.bntu.diplomainformationproject.user.repository.StudentRepository;
import by.bntu.diplomainformationproject.user.repository.TeacherRepository;
import by.bntu.diplomainformationproject.user.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static by.bntu.diplomainformationproject.util.Constants.ENTRY_IS_NOT_FOUND_MSG;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    private final StudentRepository studentRepository;

    private final TeacherMapper teacherMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public TeacherDto findById(Long id) {
        return teacherMapper
            .entityToDto(teacherRepository.findById(id).orElseThrow(() ->
                                                                        new EntryIsNotFoundException(String.format(
                                                                            ENTRY_IS_NOT_FOUND_MSG, id))));
    }

    @Transactional
    @Override
    public TeacherDto create(TeacherDto teacherDto) {
        if (teacherDto.getPassword() != null) {
            teacherDto.setPassword(passwordEncoder.encode(teacherDto.getPassword()));
        }
        teacherDto = teacherMapper
            .entityToDto(teacherRepository.save(teacherMapper.dtoToEntity(teacherDto)));
        log.debug("Teacher {} was created", teacherDto);
        return teacherDto;
    }

    @Transactional
    @Override
    public TeacherDto update(TeacherDto teacherDto) {
        teacherDto = teacherMapper
            .entityToDto(teacherRepository.save(teacherMapper.dtoToEntity(teacherDto)));
        log.debug("Teacher {} was updated", teacherDto);
        return teacherDto;
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new EntryIsNotFoundException(String.format(ENTRY_IS_NOT_FOUND_MSG, id));
        }
        teacherRepository.deleteById(id);
        log.info("Teacher with id = {} was deleted", id);
        return true;
    }

    @Override
    public List<TeacherDto> findAll() {
        return teacherRepository.findAll()
                                .stream().map(teacherMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public boolean existsByEmail(String email) {
        return teacherRepository.existsByEmail(email);
    }

    @Override
    public void confirmStudent(IdDto idDto) {
        Student student = studentRepository.findById((long)idDto.getId())
                                           .orElseThrow(() -> new EntryIsNotFoundException(ENTRY_IS_NOT_FOUND_MSG));
        student.setIsConfirmed(true);
        studentRepository.save(student);
    }

    @Override
    public void confirmTeacher(IdDto idDto) {
        Teacher teacher = teacherRepository.findById((long)idDto.getId())
                                           .orElseThrow(() -> new EntryIsNotFoundException(ENTRY_IS_NOT_FOUND_MSG));
        teacher.setIsConfirmed(true);
        teacherRepository.save(teacher);
    }
}
