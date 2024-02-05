package ru.inno.task5.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.inno.task5.exception.BadRequestException;
import ru.inno.task5.model.*;
import ru.inno.task5.repositories.ProductRegistryRepository;

import java.util.Optional;

@Slf4j
@Service
public class ProductRegistryService {
    private final ProductRegistryRepository productRegistryRepository;
    private final ProductService productService;
    private final RefProductClassService refProductClassService;
    private final RefProductRegistryTypeService refProductRegistryTypeService;
    private final AccountPoolService accountPoolService;
    private final TransactionalService transactionalService;

    public ProductRegistryService(ProductService productService,
                                  ProductRegistryRepository productRegistryRepository,
                                  RefProductClassService refProductClassService,
                                  RefProductRegistryTypeService refProductRegistryTypeService,
                                  AccountPoolService accountPoolService,
                                  TransactionalService transactionalService) {
        this.productService = productService;
        this.productRegistryRepository = productRegistryRepository;
        this.refProductClassService = refProductClassService;
        this.refProductRegistryTypeService = refProductRegistryTypeService;
        this.accountPoolService = accountPoolService;
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
        RefProductRegistryType refProductRegistryType = refProductRegistryTypeService
                .findOneByValue(request.getRegistryTypeCode());

        // Шаг 4.
        AccountTemp accountTemp = null;
        if (request.getRegistryTypeCode() != null) {
            accountTemp = accountPoolService.findAccount(
                            request.getBranchCode(),
                            request.getCurrencyCode(),
                            request.getMdmCode(),
                            request.getPriorityCode(),
                            request.getRegistryTypeCode());
        }
        log.info("accountTemp: " + accountTemp);

        RefProductClass refProductClass = refProductClassService.findOneByValue(
                        refProductRegistryType.getProduct_class_code());
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

    public ProductRegistry saveProductRegistry(ProductRegistry productRegistry) {
        return productRegistryRepository.save(productRegistry);
    }
}
