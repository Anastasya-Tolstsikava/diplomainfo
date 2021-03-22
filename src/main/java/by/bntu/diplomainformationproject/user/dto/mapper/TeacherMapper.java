package by.bntu.diplomainformationproject.user.dto.mapper;

import by.bntu.diplomainformationproject.user.dto.TeacherDto;
import by.bntu.diplomainformationproject.user.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface TeacherMapper extends BaseMapper<Teacher, TeacherDto> {

    TeacherDto entityToDto(Teacher teacher);

    Teacher dtoToEntity(TeacherDto teacherDto);
}
