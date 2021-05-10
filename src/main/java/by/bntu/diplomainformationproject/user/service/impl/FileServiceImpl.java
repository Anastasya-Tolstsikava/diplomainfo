package by.bntu.diplomainformationproject.user.service.impl;

import by.bntu.diplomainformationproject.user.dto.FileDto;
import by.bntu.diplomainformationproject.user.dto.mapper.FileMapper;
import by.bntu.diplomainformationproject.user.entity.File;
import by.bntu.diplomainformationproject.user.repository.FileRepository;
import by.bntu.diplomainformationproject.user.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    private final FileMapper fileMapper;

    @Override
    public FileDto findById(Long id) {
        return null;
    }

    @Override
    public FileDto create(FileDto fileDto) {
        File file = new File();
        file.setPath(fileDto.getPath());
        file.setCategory(fileDto.getCategory());
        fileRepository.save(file);
        return fileDto;
    }

    @Override
    public FileDto update(FileDto object) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public List<FileDto> findByCategory(String category) {
        List<File> files = fileRepository.findByCategory(category);
        return files.stream().map(this::mapToDto)
                                      .collect(Collectors.toList());
    }

    private FileDto mapToDto(File file){
        return new FileDto(file.getPath(),file.getCategory());
    }
}
