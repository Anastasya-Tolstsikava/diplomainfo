package by.bntu.diplomainformationproject.user.annotation.impl;


import by.bntu.diplomainformationproject.user.annotation.UniqueEmail;
import by.bntu.diplomainformationproject.user.service.StudentService;
import by.bntu.diplomainformationproject.user.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !studentService.existsByEmail(s) && !teacherService.existsByEmail(s);
    }
}
