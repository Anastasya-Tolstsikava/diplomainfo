package by.bntu.diplomainformationproject.user.service.impl;

import by.bntu.diplomainformationproject.service.exception.EntryIsNotFoundException;
import by.bntu.diplomainformationproject.user.dto.StudentDto;
import by.bntu.diplomainformationproject.user.dto.TeacherDto;
import by.bntu.diplomainformationproject.user.dto.mapper.StudentMapper;
import by.bntu.diplomainformationproject.user.dto.mapper.TeacherMapper;
import by.bntu.diplomainformationproject.user.entity.Student;
import by.bntu.diplomainformationproject.user.entity.Teacher;
import by.bntu.diplomainformationproject.user.repository.StudentRepository;
import by.bntu.diplomainformationproject.user.repository.TeacherRepository;
import by.bntu.diplomainformationproject.user.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static by.bntu.diplomainformationproject.util.Constants.ENTRY_IS_NOT_FOUND_MSG;
import static by.bntu.diplomainformationproject.util.Constants.USER_WITH_EMAIL_NOT_FOUND_MSG;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final TeacherRepository teacherRepository;

    private final StudentMapper studentMapper;

    private final TeacherMapper teacherMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public StudentDto findById(Long id) {
        return studentMapper
            .entityToDto(studentRepository.findById(id).orElseThrow(() ->
                                                                        new EntryIsNotFoundException(String.format(
                                                                            ENTRY_IS_NOT_FOUND_MSG, id))));
    }

    @Transactional
    @Override
    public StudentDto create(StudentDto studentDto) {
        if (studentDto.getPassword() != null) {
            studentDto.setPassword(passwordEncoder.encode(studentDto.getPassword()));
        }
        Student entity = studentMapper.dtoToEntity(studentDto);
        entity.setIsConfirmed(false);
        Student student = studentRepository.save(entity);
        studentDto = studentMapper.entityToDto(student);
        log.debug("Student {} was created", studentDto);
        return studentDto;
    }

    @Transactional
    @Override
    public StudentDto update(StudentDto studentDto) {
        if (studentDto.getTeacher() == null) {
            User details = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String teacherEmail = details.getUsername();
            Teacher teacher = teacherRepository.findByEmail(teacherEmail).orElse(null);
            studentDto.setTeacher(teacher);
        }
        studentDto = studentMapper
            .entityToDto(studentRepository.save(studentMapper.dtoToEntity(studentDto)));
        log.debug("Student {} was updated", studentDto);
        return studentDto;
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntryIsNotFoundException(String.format(ENTRY_IS_NOT_FOUND_MSG, id));
        }
        studentRepository.deleteById(id);
        log.info("Student with id = {} was deleted", id);
        return true;
    }

    @Override
    public List<StudentDto> findAll() {
        return studentRepository.findAll()
                                .stream().map(studentMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<StudentDto> findByTeacher(TeacherDto teacherDto) {
        return studentRepository.findAllByTeacher(teacherMapper.dtoToEntity(teacherDto))
                                .stream().map(studentMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public boolean existsByEmail(String email) {
        return studentRepository.findByEmail(email).isPresent();
    }

    private Student findByEmail(String email) {
        return studentRepository.findByEmail(email).orElseThrow(() ->
                                                                    new EntryIsNotFoundException(String.format(
                                                                        USER_WITH_EMAIL_NOT_FOUND_MSG, email)));
    }

//    @Override
//    public StudentDto addTeacher(StudentDto studentDto) {
//        User details = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String teacherEmail = details.getUsername();
//
//        studentRepository.
//    }
}
