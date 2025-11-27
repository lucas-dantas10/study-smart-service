package br.com.study_smart_service.utils.logger;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class LogUtils {

    public static void log(String classpath, String message, boolean isError) {
        if (isError) {
            log.error("[{}] - {} - {}", classpath, LocalDateTime.now(), message);
        } else {
            log.info("[{}] - {} - {}", classpath, LocalDateTime.now(), message);
        }
    }
}
