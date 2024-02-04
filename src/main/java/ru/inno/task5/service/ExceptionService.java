package ru.inno.task5.service;

import org.springframework.stereotype.Service;
import ru.inno.task5.exception.BadRequestException;
import ru.inno.task5.exception.NotFoundException;

@Service
public class ExceptionService {
    public void methodThrowsException(int errCode, String message) {
        if (errCode == 400)
            throw new BadRequestException(message);
        else if (errCode == 404)
            throw new NotFoundException(message);
    }

    public void methodThrowsException(int errCode, String message, Object... args) {
        if (errCode == 400)
            throw new BadRequestException(message, args);
        else if (errCode == 404)
            throw new NotFoundException(message, args);
    }
}
