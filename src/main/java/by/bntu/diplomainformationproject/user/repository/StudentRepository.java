package by.bntu.diplomainformationproject.user.repository;

import by.bntu.diplomainformationproject.user.entity.Student;
import by.bntu.diplomainformationproject.user.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByTeacher(Teacher teacher);
    Optional<Student> findByEmail(String email);
    List<Student> findAllByTeacherEmail(String email);

    @Query("SELECT s FROM Student s WHERE s.id IN (:ids)")
    List<Student> findAllByIds(List<Long> ids);

}
