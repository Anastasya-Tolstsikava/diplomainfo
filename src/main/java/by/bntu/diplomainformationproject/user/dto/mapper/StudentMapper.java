package by.bntu.diplomainformationproject.user.dto.mapper;

import by.bntu.diplomainformationproject.user.dto.StudentDto;
import by.bntu.diplomainformationproject.user.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.WARN)

public interface StudentMapper extends BaseMapper<Student, StudentDto> {

    StudentDto entityToDto(Student student);

    Student dtoToEntity(StudentDto studentDto);
}
