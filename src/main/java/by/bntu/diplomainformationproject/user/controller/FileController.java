package by.bntu.diplomainformationproject.user.controller;

import by.bntu.diplomainformationproject.user.dto.FileDto;
import by.bntu.diplomainformationproject.user.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diplomainfo/files")
public class FileController {

    @Value("${upload.path}")
    private String uploadPath;

    private final FileService fileService;

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file,
                                         @RequestParam("category") String category)
        throws IOException {

        if (file != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String fileName = file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + fileName));
            FileDto fileDto = new FileDto(fileName, category);
            fileService.create(fileDto);
        }
        return ResponseEntity.ok(file.getName());
    }

    @GetMapping
    public List<FileDto> getByCategory(@RequestParam String category) {
        return fileService.findByCategory(category);
    }

    @GetMapping("/file")
    @ResponseBody
    public ResponseEntity<FileSystemResource> getFile(@RequestParam String fileName) {
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/msword");
        return new ResponseEntity<>(new FileSystemResource(uploadPath + "/" + fileName), responseHeaders,
                                    HttpStatus.OK);
    }
}
