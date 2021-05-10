package by.bntu.diplomainformationproject.user.dto.mapper;


import by.bntu.diplomainformationproject.user.dto.FileDto;
import by.bntu.diplomainformationproject.user.entity.File;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface FileMapper extends BaseMapper<File, FileDto> {

    FileDto entityToDto(File file);

    File dtoToEntity(FileDto fileDto);
}
