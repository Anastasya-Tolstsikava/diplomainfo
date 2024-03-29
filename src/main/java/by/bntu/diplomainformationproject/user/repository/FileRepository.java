package by.bntu.diplomainformationproject.user.repository;

import by.bntu.diplomainformationproject.user.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findByCategory(String category);
}
