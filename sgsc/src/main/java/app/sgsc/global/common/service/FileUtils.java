package app.sgsc.global.common.service;

import app.sgsc.global.configuration.exception.ApiException;
import app.sgsc.global.configuration.exception.type.ApiExceptionType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils {

    public static File toFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null) {
            throw ApiException.of400(ApiExceptionType.COMMON_FILE_NOT_VALID);
        }
        if (multipartFile.getOriginalFilename() == null) {
            throw ApiException.of400(ApiExceptionType.COMMON_FILE_NOT_VALID);
        }

        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();

        return file;
    }

    public static MultipartFile toMultipartFile(String fileName, String filePath) {
        File file = new File(filePath);

        try (var fileStream =  new FileInputStream(file)) {
            return new FileDto(fileName, filePath, "multipart/form-data", fileStream.readAllBytes());
        } catch (IOException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }
}