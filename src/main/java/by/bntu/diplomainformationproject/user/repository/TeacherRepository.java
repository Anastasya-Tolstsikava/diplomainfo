package by.bntu.diplomainformationproject.user.repository;

import by.bntu.diplomainformationproject.user.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    boolean existsByEmail(String email);

    Optional<Teacher> findByEmail(String email);
}
