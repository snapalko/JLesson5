package ru.inno.task5.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inno.task5.model.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TransactionalService {
    private final ProductService productService;
    private final ProductRegistryService productRegistryService;
    private final AgreementService agreementService;

    public TransactionalService(@Lazy ProductService productService,
                                @Lazy ProductRegistryService productRegistryService,
                                AgreementService agreementService) {
        this.productService = productService;
        this.productRegistryService = productRegistryService;
        this.agreementService = agreementService;
    }

    @Transactional
    public ResponseRegistry saveProductAndProductRegistry(Product product,
                                                          ProductRegistry productRegistry) {
        ResponseRegistry data = new ResponseRegistry();

        product = productService.saveProduct(product);
        log.info("Сохранили продукт в БД и получили product id=" + product.getId());

        productRegistry.setProduct_id(product.getId());
        log.info(String.valueOf(productRegistry));

        productRegistry = productRegistryService.saveProductRegistry(productRegistry);
        log.info("Сохранили продуктовый Регистр в БД с id=" + productRegistry.getId());
        data.setAccountId(productRegistry.getId().toString());
        return data;
    }

    @Transactional
    public ResponseProduct saveProductAndProductRegistry(Product product,
                                                         List<ProductRegistry> productRegistryList,
                                                         List<Agreement> agreementList) {
        ArrayList<String> registerId = new ArrayList<>();
        ArrayList<String> supplementaryAgreementId = new ArrayList<>();
        ResponseProduct data = new ResponseProduct();

        product = productService.saveProduct(product);
        Long productId = product.getId();
        data.setInstanceId(productId.toString());
        log.info("Сохранили продукт в БД и получили product id=" + productId);

        for (ProductRegistry productRegistry : productRegistryList) {
            productRegistry.setProduct_id(productId);
            productRegistry = productRegistryService.saveProductRegistry(productRegistry);
            registerId.add(productRegistry.getId().toString());
            log.info("Сохранили продуктовый Регистр в БД с id=" + productRegistry.getId());
        }
        String[] arrayProductRegister = registerId.toArray(new String[0]);
        data.setRegisterId(arrayProductRegister);

        for (Agreement agreement : agreementList) {
            agreement.setProduct_id(productId);
            agreement = agreementService.saveAgreement(agreement);
            supplementaryAgreementId.add(agreement.getId().toString());
            log.info("Сохранили Доп.соглашение в БД с id=" + agreement.getId());
        }
        String[] arrayAgreementId = supplementaryAgreementId.toArray(new String[0]);
        data.setSupplementaryAgreementId(arrayAgreementId);
        return data;
    }

    @Transactional
    public ResponseProduct saveAgreementList(Product product, List<Agreement> agreementList) {
        Long productId = product.getId();
        ArrayList<String> supplementaryAgreementId = new ArrayList<>();

        ResponseProduct data = new ResponseProduct();
        data.setInstanceId(productId.toString());

        for (Agreement agreement : agreementList) {
            agreement.setProduct_id(productId);
            agreement = agreementService.saveAgreement(agreement);
            supplementaryAgreementId.add(agreement.getId().toString());
            log.info("Сохранили Доп.соглашение к договору (id=" + productId + ") в БД с id=" + agreement.getId());
        }

        String[] arrayAgreementId = supplementaryAgreementId.toArray(new String[0]);
        data.setSupplementaryAgreementId(arrayAgreementId);
        return data;
    }
}
