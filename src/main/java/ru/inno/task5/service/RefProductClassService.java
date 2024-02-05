package ru.inno.task5.service;

import org.springframework.stereotype.Service;
import ru.inno.task5.model.RefProductClass;
import ru.inno.task5.repositories.RefProductClassRepository;

import static ru.inno.task5.exception.NotFoundException.notFoundException;

@Service
public class RefProductClassService {
    private final RefProductClassRepository productClassRepository;

    public RefProductClassService(RefProductClassRepository productClassRepository) {
        this.productClassRepository = productClassRepository;
    }

    public RefProductClass findOneByValue(String value) {
        return productClassRepository.findOneByValue(value)
                .orElseThrow(notFoundException("Не найден RefProductClass с value = <{0}>!",
                        value));
    }
}
