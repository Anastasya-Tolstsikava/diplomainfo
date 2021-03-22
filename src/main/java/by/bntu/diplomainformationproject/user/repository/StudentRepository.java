package by.bntu.diplomainformationproject.user.repository;

import by.bntu.diplomainformationproject.user.entity.Student;
import by.bntu.diplomainformationproject.user.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByTeacher(Teacher teacher);
    Optional<Student> findByEmail(String email);
}
