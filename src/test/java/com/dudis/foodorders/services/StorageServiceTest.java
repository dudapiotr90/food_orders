package com.dudis.foodorders.services;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import uk.org.lidalia.slf4jext.Level;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, OutputCaptureExtension.class})
@TestPropertySource(locations = "classpath:application-test.yml")
class StorageServiceTest {

    TestLogger logger = TestLoggerFactory.getTestLogger(StorageService.class);

    public static final String TEMP = "/src/test/resources/testing/uploaded";
    @InjectMocks
    private StorageService storageService;
    @Mock
    private RandomUUIDGenerator randomUUIDGenerator;

    @BeforeEach
    void setUp() throws IOException {
        Files.createDirectories(Paths.get(System.getProperty("user.dir") + TEMP));
    }

    @AfterEach
    void clean() throws IOException {
        Path uploads = Paths.get(System.getProperty("user.dir") + TEMP);
        FileUtils.cleanDirectory(new File(uploads.toUri()));
    }

    @Test
    void uploadImageToServerWorksCorrectly() throws IOException {
        // Given
        byte[] byteArray = new byte[]{1, 2, 3, 4, 5};
        MultipartFile someFile1 = new MockMultipartFile("name", new byte[0]);
        MultipartFile someFile2 = new MockMultipartFile("someFileName", "originalName", null, byteArray);
        String uniqueCode = UUID.randomUUID().toString();
        when(randomUUIDGenerator.generateUniqueCode()).thenReturn(uniqueCode);
        String expectedPath1 = "";
        ReflectionTestUtils.setField(storageService, "uploadDir", TEMP);

        // When
        String result1 = storageService.uploadImageToServer(someFile1, 5);
        String result2 = storageService.uploadImageToServer(someFile2, 9);

        // Then
        assertEquals(expectedPath1, result1);
        assertTrue(result2.replace("\\","/").contains(TEMP));

    }

    @Test
    void removeImageFromServerWorksCorrectly(CapturedOutput output) throws IOException {
        // Given
        String somePath = "some/path/to/test";
        Path path = Paths.get(TEMP);
        String pathToThrow = "some/path/to/throw";
        Path throwingPath = Paths.get(pathToThrow);
        logger.setEnabledLevels(Level.ERROR,Level.INFO,Level.WARN);
        String expectedLog1 = "Image successfully deleted";
        String expectedLog2 = "Trying to delete non existing file";
        String expectedLog3 = "Cannot delete file";
        mockStatic(Files.class);
        mock(Path.class);

        when(Files.deleteIfExists(path)).thenReturn(true);
        BDDMockito.given(Files.deleteIfExists(throwingPath)).willAnswer(invocation -> {
            throw new IOException(expectedLog3);
        });

        // When
        storageService.removeImageFromServer(TEMP);
        storageService.removeImageFromServer(somePath);
        Throwable exception = assertThrows(FileUploadException.class,
            () -> storageService.removeImageFromServer(pathToThrow));

        // Then
        Assertions.assertTrue(output.getOut().contains(expectedLog1));
        Assertions.assertTrue(output.getOut().contains(expectedLog2));
        Assertions.assertTrue(output.getOut().contains(expectedLog3));
        Assertions.assertTrue(exception.getMessage().contains(expectedLog3));
    }
}