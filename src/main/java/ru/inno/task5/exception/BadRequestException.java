package ru.inno.task5.exception;

import java.text.MessageFormat;
import java.util.function.Supplier;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String reason) {
        super(reason);
    }

    public BadRequestException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public static Supplier<BadRequestException> badRequestException(String message) {
        return () -> new BadRequestException(message);
    }

    public static Supplier<BadRequestException> badRequestException(String message, Object... args) {
        return () -> new BadRequestException(message, args);
    }

}
