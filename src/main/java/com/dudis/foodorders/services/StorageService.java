package com.dudis.foodorders.services;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
@Slf4j
@Service
@NoArgsConstructor
public class StorageService {
    @Value("${server.servlet.multipart.location}")
    private String uploadDir;

    public String uploadImageToServer(MultipartFile image, Integer restaurantId) throws IOException {
        Path restaurantUploadsCatalog = Paths.get(System.getProperty("user.dir") + uploadDir + "\\restaurant" + restaurantId);
        if (!Files.exists(restaurantUploadsCatalog)) {
            Files.createDirectories(restaurantUploadsCatalog);
        }
        if (Objects.nonNull(image.getOriginalFilename()) || !("".equals(image.getOriginalFilename()))) {
            String filename = image.getOriginalFilename();
            String uniqueFilename = UUID.randomUUID().toString();
            String fileExtension = StringUtils.getFilenameExtension(filename);
            String imagePath = restaurantUploadsCatalog + "\\" + uniqueFilename + "." + fileExtension;
            File destinationFile = new File(imagePath);
            image.transferTo(destinationFile);
            return imagePath;

        } else {
            return "";
        }
    }

    public void removeImageFromServer(String foodImagePath) {
        try {
            if (Files.deleteIfExists(Paths.get(foodImagePath))) {
                log.info("Image successfully deleted");
            } else {
                log.warn("Trying to delete non existing file");
            }
        } catch (IOException e) {
            log.error("Cannot delete file: [{}]",foodImagePath);
            throw new RuntimeException("Trying to delete directory: %s".formatted(foodImagePath));
        }
    }
}
