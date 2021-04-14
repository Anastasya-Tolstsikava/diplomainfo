package by.bntu.diplomainformationproject.user.controller;

import by.bntu.diplomainformationproject.user.dto.IdDto;
import by.bntu.diplomainformationproject.user.dto.TeacherDto;
import by.bntu.diplomainformationproject.user.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diplomainfo/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TeacherDto> create(@Valid @RequestBody TeacherDto teacherDto) {
        TeacherDto newTeacherDto = teacherService.create(teacherDto);
        return ResponseEntity.ok(newTeacherDto);
    }

    @GetMapping
    public ResponseEntity<List<TeacherDto>> findAll() {
        List<TeacherDto> allTeachers = teacherService.findAll();
        return ResponseEntity.ok(allTeachers);
    }

    @GetMapping("{id}")
    public ResponseEntity<TeacherDto> findById(@PathVariable Long id) {
        TeacherDto teacherDto = teacherService.findById(id);
        return ResponseEntity.ok(teacherDto);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable Long id) {
        teacherService.deleteById(id);
    }

    @PostMapping("/student-confirmation")
    @PreAuthorize("hasRole('TEACHER') || hasRole('ADMIN')")
    public void confirmStudent(@Valid @RequestBody IdDto idDto){
        teacherService.confirmStudent(idDto);
    }

    @PostMapping("/teacher-confirmation")
    @PreAuthorize("hasRole('TEACHER') || hasRole('ADMIN')")
    public void confirmTeacher(@Valid @RequestBody IdDto idDto){
        teacherService.confirmTeacher(idDto);
    }
}
