package by.bntu.diplomainformationproject.user.service.impl;

import by.bntu.diplomainformationproject.service.exception.EntryIsNotFoundException;
import by.bntu.diplomainformationproject.user.dto.IdDto;
import by.bntu.diplomainformationproject.user.dto.StudentDto;
import by.bntu.diplomainformationproject.user.dto.TeacherDto;
import by.bntu.diplomainformationproject.user.dto.mapper.StudentMapper;
import by.bntu.diplomainformationproject.user.dto.mapper.TeacherMapper;
import by.bntu.diplomainformationproject.user.entity.Message;
import by.bntu.diplomainformationproject.user.entity.Student;
import by.bntu.diplomainformationproject.user.entity.Teacher;
import by.bntu.diplomainformationproject.user.repository.MessageRepository;
import by.bntu.diplomainformationproject.user.repository.StudentRepository;
import by.bntu.diplomainformationproject.user.repository.TeacherRepository;
import by.bntu.diplomainformationproject.user.service.EmailSenderService;
import by.bntu.diplomainformationproject.user.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static by.bntu.diplomainformationproject.util.Constants.EMAIL_SUBJECT;
import static by.bntu.diplomainformationproject.util.Constants.ENTRY_IS_NOT_FOUND_MSG;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    private final StudentRepository studentRepository;

    private final TeacherMapper teacherMapper;

    private final StudentMapper studentMapper;

    private final PasswordEncoder passwordEncoder;

    private final EmailSenderService emailSenderService;

    private final MessageRepository messageRepository;

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
        Teacher teacher = teacherMapper.dtoToEntity(teacherDto);
        teacherDto = teacherMapper
            .entityToDto(teacherRepository.save(teacher));
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
    public void addStudents(List<StudentDto> newStudents) {
        User details = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Teacher teacher = teacherRepository.findByEmail(details.getUsername())
                                           .orElseThrow(() -> new EntryIsNotFoundException(ENTRY_IS_NOT_FOUND_MSG));

        List<Long> ids = newStudents.stream().map(StudentDto::getId).collect(Collectors.toList());

        List<Student> allByIds = studentRepository.findAllByIds(ids);

        allByIds.forEach(s -> {
            s.setTeacher(teacher);
            studentRepository.save(s);
        });

        log.debug("students with id {} were updated", ids);
    }

    @Transactional
    @Override
    public void deleteFromMyStudents(StudentDto studentDto) {
        Student studentById = studentRepository.findById(studentDto.getId())
                                               .orElseThrow(() -> new EntryIsNotFoundException(ENTRY_IS_NOT_FOUND_MSG));

        studentById.setTeacher(null);
        studentRepository.save(studentById);

        log.debug("student with id {} was updated", studentDto.getId());
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
        Student student = studentRepository.findById((Long) idDto.getId())
                                           .orElseThrow(() -> new EntryIsNotFoundException(ENTRY_IS_NOT_FOUND_MSG));
        student.setIsConfirmed(true);
        studentRepository.save(student);
    }

    @Override
    public void confirmTeacher(IdDto idDto) {
        Teacher teacher = teacherRepository.findById((Long) idDto.getId())
                                           .orElseThrow(() -> new EntryIsNotFoundException(ENTRY_IS_NOT_FOUND_MSG));
        teacher.setIsConfirmed(true);
        teacherRepository.save(teacher);
    }

    @Override
    public List<StudentDto> findMyStudents() {
        return getMyStudents().stream().map(studentMapper::entityToDto)
                              .collect(Collectors.toList());
    }

    @Override
    public void sendMessage(String message) {
        List<Student> students = getMyStudents();
        Message newMessage = new Message();
        newMessage.setText(message);
        messageRepository.save(newMessage);
        students.forEach((student) ->
                             emailSenderService.send(student.getEmail(), EMAIL_SUBJECT, message));
    }

    private List<Student> getMyStudents() {
        User details = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return studentRepository.findAllByTeacherEmail(details.getUsername());
    }
}
