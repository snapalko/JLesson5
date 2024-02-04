package ru.inno.task5.service;

import org.springframework.stereotype.Service;
import ru.inno.task5.model.Agreement;
import ru.inno.task5.model.AgreementRequest;
import ru.inno.task5.repositories.AgreementRepository;

import java.util.Optional;

@Service
public class AgreementService {
    private final AgreementRepository agreementRepository;
    private final ExceptionService exceptionService;

    public AgreementService(AgreementRepository agreementRepository,
                            ExceptionService exceptionService) {
        this.agreementRepository = agreementRepository;
        this.exceptionService = exceptionService;
    }

    public void findByContractNumber(String contractNumber) {
        Optional<Agreement> optArrangement = agreementRepository.findByContractNumber(contractNumber);
        optArrangement.ifPresent(prod -> exceptionService.methodThrowsException(400,
                "Параметр № Дополнительного соглашения (сделки) Number <{0}> уже существует для ДС с ИД  <{1}>"
                , prod.getNumber(), prod.getId())
        );
    }

    public Agreement createAgreementInstance(Long productId, AgreementRequest request) {
        System.out.println("Доп.соглашение: получили " + request);
        String generalAgreementId = request.getGeneralAgreementId();
        if (generalAgreementId == null) generalAgreementId = "0";

        Agreement agreement = new Agreement(
                null,
                productId,
                Long.parseLong(generalAgreementId),
                request.getArrangementType(),
                request.getShedulerJobId(),
                request.getNumber(),
                request.getOpeningDate(),
                request.getClosingDate(),
                request.getCancelDate(),
                request.getValidityDuration(),
                request.getCancellationReason(),
                request.getStatus(),
                request.getInterestCalculationDate(),
                request.getInterestRate(),
                request.getCoefficient(),
                request.getCoefficientAction(),
                request.getMinimumInterestRate(),
                request.getMinimumInterestRateCoefficient(),
                request.getMinimumInterestRateCoefficientAction(),
                request.getMaximalnterestRate(),
                request.getMaximalnterestRateCoefficient(),
                request.getMaximalnterestRateCoefficientAction()
        );
        System.out.println(" создали ДС: " + agreement + "\n");

        return agreement;
    }
}
