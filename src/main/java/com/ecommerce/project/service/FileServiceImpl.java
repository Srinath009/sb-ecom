package com.ecommerce.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename();

        String randomId = UUID.randomUUID().toString();

        //appending random id with original file name (example: 1234.jpg)
        assert originalFilename != null;
        String fileName = randomId.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));

        String filePath = path + File.separator + fileName;

        Path folderPath = Paths.get(path);
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        //upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName;
    }
}
