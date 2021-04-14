package by.bntu.diplomainformationproject.user.service.impl;

import by.bntu.diplomainformationproject.jwt.JwtProvider;
import by.bntu.diplomainformationproject.service.exception.EntryIsNotFoundException;
import by.bntu.diplomainformationproject.user.dto.AuthToken;
import by.bntu.diplomainformationproject.user.dto.LogInDto;
import by.bntu.diplomainformationproject.user.entity.Student;
import by.bntu.diplomainformationproject.user.entity.Teacher;
import by.bntu.diplomainformationproject.user.entity.User;
import by.bntu.diplomainformationproject.user.repository.StudentRepository;
import by.bntu.diplomainformationproject.user.repository.TeacherRepository;
import by.bntu.diplomainformationproject.user.service.LogInService;
import by.bntu.diplomainformationproject.user.service.exception.IsNotConfirmedEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static by.bntu.diplomainformationproject.util.Constants.INCORRECT_PASSWORD_MSG;
import static by.bntu.diplomainformationproject.util.Constants.IS_NOT_CONFIRMED_EMAIL;
import static by.bntu.diplomainformationproject.util.Constants.USER_WITH_EMAIL_NOT_FOUND_MSG;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogInServiceImpl implements LogInService, UserDetailsService {

    private final StudentRepository studentRepository;

    private final TeacherRepository teacherRepository;

    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthToken logIn(LogInDto logInDto) {
        User user = studentExistsByEmail(logInDto.getEmail()) ? findStudentByEmailAndPassword(logInDto.getPassword(),
                                                                                              logInDto.getEmail())
                                                              : findTeacherByEmailAndPassword(logInDto.getPassword(),
                                                                                              logInDto.getEmail());
        if (!user.getIsConfirmed()) {
            throw new IsNotConfirmedEmail(IS_NOT_CONFIRMED_EMAIL);
        }
        String token = jwtProvider.generateToken(user.getEmail());
        return new AuthToken(token);
    }

    private Student findStudentByEmailAndPassword(String password, String email) {
        Student student = findStudentByEmail(email);
        boolean matches = passwordEncoder.matches(password, student.getPassword());
        if (!matches) {
            throw new BadCredentialsException(INCORRECT_PASSWORD_MSG);
        }
        return student;
    }

    private Student findStudentByEmail(String email) {
        return studentRepository.findByEmail(email).orElseThrow(() ->
                                                                    new EntryIsNotFoundException(String.format(
                                                                        USER_WITH_EMAIL_NOT_FOUND_MSG, email)));
    }

    public boolean studentExistsByEmail(String email) {
        return studentRepository.findByEmail(email).isPresent();
    }

    private Teacher findTeacherByEmailAndPassword(String password, String email) {
        Teacher teacher = findTeacherByEmail(email);
        boolean matches = passwordEncoder.matches(password, teacher.getPassword());
        if (!matches) {
            throw new BadCredentialsException(INCORRECT_PASSWORD_MSG);
        }
        return teacher;
    }

    private Teacher findTeacherByEmail(String email) {
        return teacherRepository.findByEmail(email).orElseThrow(() ->
                                                                    new EntryIsNotFoundException(String.format(
                                                                        USER_WITH_EMAIL_NOT_FOUND_MSG, email)));
    }


    public UserDetails loadUserByUsername(String email) {
        User user = studentExistsByEmail(email) ? findStudentByEmail(email)
                                                : findTeacherByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                                                                      getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
        return authorities;
    }
}
