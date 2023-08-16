package com.dudis.foodorders.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageService {
    @Value("${server.servlet.multipart.location}")
    private String uploadDir;

    private final RandomUUIDGenerator randomUUIDGenerator;

    public String uploadImageToServer(MultipartFile image, Integer restaurantId) throws IOException {
        Path restaurantUploadsCatalog = Paths.get(System.getProperty("user.dir") + uploadDir + "\\restaurant" + restaurantId);
        if (!Files.exists(restaurantUploadsCatalog)) {
            Files.createDirectories(restaurantUploadsCatalog);
        }
        if (!"".equals(image.getOriginalFilename())) {
            String filename = image.getOriginalFilename();
            String uniqueFilename = randomUUIDGenerator.generateUniqueCode();
            String fileExtension = StringUtils.getFilenameExtension(filename);
            String imagePath = restaurantUploadsCatalog + "\\" + uniqueFilename + "." + fileExtension;
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(imagePath), StandardCopyOption.REPLACE_EXISTING);
            }
            return imagePath;
        } else {
            return "";
        }
    }

    public void removeImageFromServer(String foodImagePath) throws FileUploadException {
        try {
            if (Files.deleteIfExists(Paths.get(foodImagePath))) {
                log.info("Image successfully deleted");
            } else {
                log.warn("Trying to delete non existing file");
            }
        } catch (IOException e) {
            log.error("Cannot delete file: [{}]", foodImagePath,e);
            throw new FileUploadException("Cannot delete file: [%s]".formatted(foodImagePath));
        }
    }
}
