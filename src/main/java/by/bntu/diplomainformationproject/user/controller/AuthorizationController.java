package by.bntu.diplomainformationproject.user.controller;

import by.bntu.diplomainformationproject.user.dto.AuthToken;
import by.bntu.diplomainformationproject.user.dto.LogInDto;
import by.bntu.diplomainformationproject.user.dto.StudentDto;
import by.bntu.diplomainformationproject.user.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diplomainfo/login")
public class AuthorizationController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<AuthToken> logIn(@Valid @RequestBody LogInDto logInDto) {
        AuthToken token = studentService.logIn(logInDto);
        return ResponseEntity.ok(token);
    }
}
