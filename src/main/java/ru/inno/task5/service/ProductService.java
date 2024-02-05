package ru.inno.task5.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.inno.task5.exception.BadRequestException;
import ru.inno.task5.model.*;
import ru.inno.task5.repositories.ProductRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.inno.task5.exception.NotFoundException.notFoundException;

@Slf4j
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final RefProductClassService refProductClassService;
    private final RefProductRegistryTypeService refProductRegistryTypeService;
    private final AccountPoolService accountPoolService;
    private final AgreementService agreementService;
    private final ExceptionService exceptionService;
    private final TransactionalService transactionalService;


    public ProductService(ProductRepository productRepository,
                          RefProductClassService refProductClassService,
                          RefProductRegistryTypeService refProductRegistryTypeService,
                          AccountPoolService accountPoolService,
                          AgreementService agreementService,
                          ExceptionService exceptionService,
                          @Lazy TransactionalService transactionalService) {
        this.productRepository = productRepository;
        this.refProductClassService = refProductClassService;
        this.refProductRegistryTypeService = refProductRegistryTypeService;
        this.accountPoolService = accountPoolService;
        this.agreementService = agreementService;
        this.exceptionService = exceptionService;
        this.transactionalService = transactionalService;
    }

    public Long getClientByMdm(String mdmCode) {
        if (mdmCode != null)
            return 112233L;
        return 159753L;
    }

    public String getProductNumber() {
        // Получить число от 1 до 10000, формула: (Math.random() * (b-a)) + a
        int a = 1, b = 10000;
        int num = (int) (Math.random() * (b - a)) + a;

        return String.valueOf(num);
    }

    public int getProductPriority() {
        return 5;
    }

    public LocalDate getDateOfConclusion() {
        return LocalDate.now();
    }

    private void findByContractNumber(String contractNumber) {
        Optional<Product> optProduct = productRepository.findByContractNumber(contractNumber);
        optProduct.ifPresent(prod -> exceptionService.methodThrowsException(400,
                "Параметр ContractNumber № договора <{0}> уже существует для ЭП с ИД <{1}>"
                , prod.getNumber(), prod.getId())
        );
    }

    public ResponseProduct createProduct(ProductRequest productRequest) throws BadRequestException {
        ResponseProduct responseProduct;

        if (productRequest.getInstanceId() == null) {
            // Шаг 1.
            // Проверка Request.Body на обязательность.
            // Если не заполнено хотя бы одно обязательное поле (см. Request.Body)
            // вернуть Статус: 400/Bad Request, Текст: Имя обязательного параметра <значение> не заполнено.
            // Перейти на Шаг 2.

            // Шаг 2.
            findByContractNumber(productRequest.getContractNumber());

            // Шаг 3.
            AgreementRequest[] agreements = productRequest.getArrangements();
            for (AgreementRequest agreement : agreements) {
                agreementService.findByContractNumber(agreement.getNumber());
            }

            // Шаг 4.
            String productCode = productRequest.getProductCode();
            RefProductClass refProductClass = refProductClassService.findOneByValue(productCode);
            log.info("ProductClass: " + refProductClass);

            List<RefProductRegistryType> registerTypeList = refProductRegistryTypeService
                    .findAllByProductClassCodeAndAccountType(productCode, refProductClass.getValue(), "Клиентский");
            log.info("Массив ProductRegisterType: " + registerTypeList.get(0));

            // Шаг 5.
            Product product = createProductInstance(productRequest, refProductClass.getInternal_id());
            log.info("Продукт: " + product);

            List<Agreement> agreementList = new ArrayList<>();
            for (AgreementRequest agrRequest : productRequest.getArrangements()) {
                Agreement agreement = agreementService.createAgreementInstance(productRequest.getInstanceId(), agrRequest);
                agreementList.add(agreement);
            }
            log.info("Массив Доп.соглашений: " + agreementList);

            // Шаг 6.
            List<ProductRegistry> productRegistryList = new ArrayList<>();
            for (RefProductRegistryType rt : registerTypeList) {
                String registerTypeCode = rt.getValue();
                Account_pool accountPool = accountPoolService.findByRegistryTypeCode(registerTypeCode);
                log.info("Pool счетов: " + accountPool);

                ProductRegistry productRegistry = new ProductRegistry(product.getId(),
                        registerTypeCode,
                        accountPool.getId(),
                        accountPool.getCurrencyCode(),
                        product.getState(),
                        accountPool.getAccounts()[0]
                );
                productRegistryList.add(productRegistry);
            }
            log.info("массив ПР: " + productRegistryList);

            // Шаг 7.
            responseProduct = transactionalService.saveProductAndProductRegistry(
                    product,
                    productRegistryList,
                    agreementList);

        } else { // то изменяется состав ДС (сделка/доп.Соглашение)
            // Шаг 8.
            Product product = productRepository.findById(productRequest.getInstanceId())
                    .orElseThrow(notFoundException("Не найден экземпляр продукта с ID = <{0}>!",
                            productRequest.getInstanceId())
                    );
            List<Agreement> agreementList = new ArrayList<>();
            for (AgreementRequest agrRequest : productRequest.getArrangements()) {
                agreementService.findByContractNumber(agrRequest.getNumber());
                Agreement agreement = agreementService.createAgreementInstance(productRequest.getInstanceId(), agrRequest);
                agreementList.add(agreement);
            }
            log.info("Массив Доп.соглашений: " + agreementList);
            responseProduct = transactionalService.saveAgreementList(product, agreementList);

        }
        return responseProduct;
    }

    private Product createProductInstance(ProductRequest request, Long internalID) {
        System.out.println("получили " + request);

        Product product = new Product(internalID,
                getClientByMdm(request.getMdmCode()),
                request.getProductType(),
                request.getContractNumber(),
                request.getPriority(),
                request.getContractDate(),
                request.getStart_date_time(),
                request.getEnd_date_time(),
                request.getDays(),
                request.getInterestRatePenalty(),
                request.getMinimalBalance(),
                request.getThresholdAmount(),
                request.getAccountingDetails(),
                request.getRateType(),
                request.getTaxPercentageRate(),
                request.getReason_close(),
                request.getState()
        );
        System.out.println(" создали " + product);

        return product;
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

}
