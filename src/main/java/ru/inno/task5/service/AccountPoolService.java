package ru.inno.task5.service;

import org.springframework.stereotype.Service;
import ru.inno.task5.model.AccountTemp;
import ru.inno.task5.model.Account_pool;
import ru.inno.task5.repositories.AccountPoolRepository;

import static ru.inno.task5.exception.NotFoundException.notFoundException;

@Service
public class AccountPoolService {
    private final AccountPoolRepository accountPoolRepository;

    public AccountPoolService(AccountPoolRepository accountPoolRepository) {
        this.accountPoolRepository = accountPoolRepository;
    }

    public Account_pool findByRegistryTypeCode(String registerTypeCode) {
        return accountPoolRepository.findByRegistryTypeCode(registerTypeCode)
                .orElseThrow(notFoundException("Не найден Счет в справочнике Account_pool по типу регистра = <{0}>!",
                        registerTypeCode)
                );
    }

    public AccountTemp findAccount(String branchCode,
                                   String currencyCode,
                                   String mdmCode,
                                   String priorityCode,
                                   String registryTypeCode) {
        return accountPoolRepository.findAccount(
                        branchCode,
                        currencyCode,
                        mdmCode,
                        priorityCode,
                        registryTypeCode)
                .orElseThrow(notFoundException(
                        "Значение <Номера счета> не найдено по параметрам branchCode, currencyCode, mdmCode," +
                                "priorityCode, registryTypeCode в таблице <Пулов счетов>"
                ));
    }
}
