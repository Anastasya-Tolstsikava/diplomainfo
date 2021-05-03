package by.bntu.diplomainformationproject.user.controller;

import by.bntu.diplomainformationproject.user.dto.StudentDto;
import by.bntu.diplomainformationproject.user.dto.TeacherDto;
import by.bntu.diplomainformationproject.user.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diplomainfo/students")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<StudentDto> create(@Valid @RequestBody StudentDto studentDto) {
        StudentDto newStudentDto = studentService.create(studentDto);
        return ResponseEntity.ok(newStudentDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<StudentDto> update(@Valid @RequestBody StudentDto studentDto) {
        StudentDto newStudentDto = studentService.update(studentDto);
        return ResponseEntity.ok(newStudentDto);
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> findAll() {
        List<StudentDto> allStudents = studentService.findAll();
        return ResponseEntity.ok(allStudents);
    }

    @GetMapping("{id}")
    public ResponseEntity<StudentDto> findById(@PathVariable Long id) {
        StudentDto studentDto = studentService.findById(id);
        return ResponseEntity.ok(studentDto);
    }

    @GetMapping("/teacher")
    public ResponseEntity<List<StudentDto>> findByTeacher(@Valid @RequestBody TeacherDto teacherDto) {
        List<StudentDto> studentDto = studentService.findByTeacher(teacherDto);
        return ResponseEntity.ok(studentDto);
    }

    @DeleteMapping("{id}")
   // @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable Long id) {
        studentService.deleteById(id);
    }


}
