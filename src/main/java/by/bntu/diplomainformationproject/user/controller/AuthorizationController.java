package by.bntu.diplomainformationproject.user.controller;

import by.bntu.diplomainformationproject.user.dto.AuthToken;
import by.bntu.diplomainformationproject.user.dto.LogInDto;
import by.bntu.diplomainformationproject.user.service.LogInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diplomainfo/login")
public class AuthorizationController {

    private final LogInService logInService;

    @PostMapping
    public ResponseEntity<AuthToken> logIn(@Valid @RequestBody LogInDto logInDto) {
        AuthToken token = logInService.logIn(logInDto);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/details")
    public ResponseEntity getUserDetails() {
        SecurityContext context = SecurityContextHolder.getContext();
        try {
            Authentication authentication = context.getAuthentication();
            User details = (User) authentication.getPrincipal();
            return ResponseEntity.ok(details);
        } catch (ClassCastException e) {
            throw new AuthenticationCredentialsNotFoundException("User details not found");
        }

    }
}
