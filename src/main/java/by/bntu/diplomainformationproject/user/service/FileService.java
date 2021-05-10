package by.bntu.diplomainformationproject.user.service;

import by.bntu.diplomainformationproject.service.Service;
import by.bntu.diplomainformationproject.user.dto.FileDto;

import java.util.List;

public interface FileService extends Service<FileDto, Long> {
        List<FileDto> findByCategory(String category);
}
