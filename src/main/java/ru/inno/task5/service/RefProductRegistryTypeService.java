package ru.inno.task5.service;

import org.springframework.stereotype.Service;
import ru.inno.task5.exception.NotFoundException;
import ru.inno.task5.model.RefProductRegistryType;
import ru.inno.task5.repositories.RefProductRegistryTypeRepository;

import java.util.List;

import static ru.inno.task5.exception.NotFoundException.notFoundException;

@Service
public class RefProductRegistryTypeService {
    private final RefProductRegistryTypeRepository refProductRegistryTypeRepository;

    public RefProductRegistryTypeService(RefProductRegistryTypeRepository refProductRegistryTypeRepository) {
        this.refProductRegistryTypeRepository = refProductRegistryTypeRepository;
    }

    public RefProductRegistryType findOneByValue(String value) {
        return refProductRegistryTypeRepository
                .findOneByValue(value)
                .orElseThrow(notFoundException(// вернуть Статус: 404/Данные не найдены
                        "КодПродукта <{0}> не найден в Каталоге продуктов <tpp_ref_product_register_type> для данного типа Регистра",
                        value));
    }

    public List<RefProductRegistryType> findAllByProductClassCodeAndAccountType(String productCode,
                                                                                String productClass,
                                                                                String accountType) {
        List<RefProductRegistryType> registerTypeList = refProductRegistryTypeRepository
                .findAllByProductClassCodeAndAccountType(productClass, accountType);
        if (registerTypeList.isEmpty())
            throw new NotFoundException("КодПродукта <{0}> не найден в Каталоге продуктов <{1}>",
                    productCode, "tpp_ref_product_register_type");

        return registerTypeList;
    }
}
