package by.bntu.diplomainformationproject.user.service.impl;

import by.bntu.diplomainformationproject.jwt.JwtProvider;
import by.bntu.diplomainformationproject.service.exception.EntryIsNotFoundException;
import by.bntu.diplomainformationproject.user.dto.AuthToken;
import by.bntu.diplomainformationproject.user.dto.LogInDto;
import by.bntu.diplomainformationproject.user.dto.StudentDto;
import by.bntu.diplomainformationproject.user.dto.TeacherDto;
import by.bntu.diplomainformationproject.user.dto.mapper.StudentMapper;
import by.bntu.diplomainformationproject.user.dto.mapper.TeacherMapper;
import by.bntu.diplomainformationproject.user.entity.Student;
import by.bntu.diplomainformationproject.user.repository.StudentRepository;
import by.bntu.diplomainformationproject.user.service.StudentService;
import by.bntu.diplomainformationproject.user.service.exception.IsNotConfirmedEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static by.bntu.diplomainformationproject.util.Constants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService, UserDetailsService {

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    private final TeacherMapper teacherMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    @Override
    public StudentDto findById(Long id) {
        return studentMapper
                .entityToDto(studentRepository.findById(id).orElseThrow(() ->
                        new EntryIsNotFoundException(String.format(ENTRY_IS_NOT_FOUND_MSG, id))));
    }

    @Transactional
    @Override
    public StudentDto create(StudentDto studentDto) {
        if (studentDto.getPassword() != null) {
            studentDto.setPassword(passwordEncoder.encode(studentDto.getPassword()));
        }
        studentDto = studentMapper
                .entityToDto(studentRepository.save(studentMapper.dtoToEntity(studentDto)));
        log.debug("Student {} was created", studentDto);
        return studentDto;
    }

    @Transactional
    @Override
    public StudentDto update(StudentDto studentDto) {
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

    private Student findByEmailAndPassword(String password, String email) {
        Student student = findByEmail(email);
        boolean matches = passwordEncoder.matches(password, student.getPassword());
        if (!matches) {
            throw new BadCredentialsException(INCORRECT_PASSWORD_MSG);
        }
        return student;
    }

    private Student findByEmail(String email) {
        return studentRepository.findByEmail(email).orElseThrow(() ->
                new EntryIsNotFoundException(String.format(USER_WITH_EMAIL_NOT_FOUND_MSG, email)));
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Student student = findByEmail(email);
        return new org.springframework.security.core.userdetails.User(student.getEmail(), student.getPassword(),
                getAuthority(student));
    }

    @Override
    public AuthToken logIn(LogInDto logInDto) {
        Student student = findByEmailAndPassword(logInDto.getPassword(), logInDto.getEmail());
        if (!student.getIsConfirmedEmail()) {
            throw new IsNotConfirmedEmail(IS_NOT_CONFIRMED_EMAIL);
        }
        String token = jwtProvider.generateToken(student.getEmail());
        return new AuthToken(token);
    }

    private Set<SimpleGrantedAuthority> getAuthority(Student student) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(student.getRole().toString()));
        return authorities;
    }
}
