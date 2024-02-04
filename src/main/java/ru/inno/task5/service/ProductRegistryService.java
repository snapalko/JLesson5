package ru.inno.task5.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.inno.task5.exception.BadRequestException;
import ru.inno.task5.model.*;
import ru.inno.task5.repositories.AccountPoolRepository;
import ru.inno.task5.repositories.ProductRegistryRepository;
import ru.inno.task5.repositories.RefProductClassRepository;
import ru.inno.task5.repositories.RefProductRegistryTypeRepository;

import java.util.Optional;

import static ru.inno.task5.exception.NotFoundException.notFoundException;

@Slf4j
@Service
public class ProductRegistryService {
    private final ProductService productService;
    private final ProductRegistryRepository productRegistryRepository;
    private final RefProductClassRepository refProductClassRepository;
    private final RefProductRegistryTypeRepository refProductRegistryTypeRepository;
    private final AccountPoolRepository accountPoolRepository;
    private final TransactionalService transactionalService;

    public ProductRegistryService(ProductService productService,
                                  ProductRegistryRepository productRegistryRepository,
                                  RefProductClassRepository refProductClassRepository,
                                  RefProductRegistryTypeRepository refProductRegistryTypeRepository,
                                  AccountPoolRepository accountPoolRepository,
                                  TransactionalService transactionalService) {
        this.productService = productService;
        this.productRegistryRepository = productRegistryRepository;
        this.refProductClassRepository = refProductClassRepository;
        this.refProductRegistryTypeRepository = refProductRegistryTypeRepository;
        this.accountPoolRepository = accountPoolRepository;
        this.transactionalService = transactionalService;
    }

    public ResponseRegistry createProductRegistry(ProductRegistryRequest request) {
        // Шаг 2.
        Long instanceId = request.getInstanceId();
        String registryTypeCode = request.getRegistryTypeCode();

        Optional<ProductRegistry> optProductRegister = productRegistryRepository
                .findByProductIdAndType(instanceId, registryTypeCode);

        if (optProductRegister.isPresent()) {
            // вернуть Статус: 400/Bad Request
            throw new BadRequestException(
                    "Параметр registryTypeCode тип регистра <{0}> уже существует для ЭП с ИД <{1}>",
                    registryTypeCode, instanceId);
        }

        // Шаг 3.
        RefProductRegistryType refProductRegistryType = refProductRegistryTypeRepository
                .findOneByValue(request.getRegistryTypeCode())
                .orElseThrow(notFoundException(// вернуть Статус: 404/Данные не найдены
                        "КодПродукта <{0}> не найден в Каталоге продуктов <tpp_ref_product_register_type> для данного типа Регистра",
                        request.getRegistryTypeCode()));

        // Шаг 4.
        AccountTemp accountTemp = null;
        if (request.getRegistryTypeCode() != null) {
            accountTemp = accountPoolRepository.findAccount(
                            request.getBranchCode(),
                            request.getCurrencyCode(),
                            request.getMdmCode(),
                            request.getPriorityCode(),
                            request.getRegistryTypeCode())
                    .orElseThrow(notFoundException(
                            "Значение <Номера счета> не найдено по параметрам branchCode, currencyCode, mdmCode," +
                                    "priorityCode, registryTypeCode в таблице <Пулов счетов>"
                    ));
        }

        log.info("accountTemp: " + accountTemp);

        RefProductClass refProductClass = refProductClassRepository.findOneByValue(
                        refProductRegistryType.getProduct_class_code()
                )
                .orElseThrow(notFoundException("Не найден RefProductClass с value = <{0}>!",
                        refProductRegistryType.getProduct_class_code()));

        log.info(String.valueOf(refProductClass));

        Product product = new Product(
                refProductClass.getInternal_id(),
                productService.getClientByMdm(null),
                refProductClass.getGbl_name(),
                productService.getProductNumber(),
                productService.getProductPriority(),
                productService.getDateOfConclusion()
        );

        assert accountTemp != null;
        ProductRegistry productRegistry = new ProductRegistry(
                product.getId(),
                request.getRegistryTypeCode(),
                accountTemp.getId(),
                accountTemp.getCurrencyCode(),
                product.getState(),
                accountTemp.getAccounts()
        );

        return transactionalService.saveProductAndProductRegistry(product, productRegistry);
    }
}
