package app.sgsc.global;

import app.sgsc.global.configuration.exception.type.ApiExceptionType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Logger {
    public static void error(ApiExceptionType type) {
        log.info("[ERR] Code: {}, Message: {}", type.getCode(), type.getMessage());
    }
}