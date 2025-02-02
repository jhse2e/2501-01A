package app.sgsc.domain.service.logic.handler;

import app.sgsc.domain.db.rds.entity.User;
import app.sgsc.domain.db.rds.repository.query.UserRepository;
import app.sgsc.domain.dto.request.UserDto;
import app.sgsc.domain.service.logic.helper.UserPasswordHelper;
import app.sgsc.global.common.service.FileUtils;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserFileHandler {
    private final UserRepository userRepository;

    public void execute(MultipartFile userFile, UserPasswordHelper userPasswordHelper) {
        try {
            log.info("{}", "User File");
            CSVReader reader = new CSVReader(new FileReader(FileUtils.toFile(userFile)));
            reader.readNext();
            String[] data;

            while ((data = reader.readNext()) != null) {
                User user = null;

                // log.info("{}", Arrays.toString(data));

                if (!data[0].isBlank() && !data[1].isBlank() && !data[2].isBlank() && !data[3].isBlank() && !data[4].isBlank()) {
                    user = getOrCreateUser(new UserDto(data[1], data[0], data[2], Integer.valueOf(data[3]), Integer.valueOf(data[4]), Integer.valueOf(data[5])), userPasswordHelper);
                }
            }
            log.info("{}", "User File Completed");
        } catch (IOException | CsvValidationException e) {
            log.info(e.getMessage());
        }
    }

    private User getOrCreateUser(UserDto userDto, UserPasswordHelper userPasswordHelper) {
        return userRepository.getUserByUserNumber(userDto.userNumber())
                .orElseGet(() -> userRepository.save(User.create(userDto.userName(), userDto.userNumber(), userPasswordHelper.encrypt(userDto.userPassword()), userDto.userRegistrationCredit(), userDto.userRegistrationCreditLeft(), userDto.userRegistrationCreditLimit())));
    }
}