package ru.inno.task5.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inno.task5.model.*;
import ru.inno.task5.repositories.AgreementRepository;
import ru.inno.task5.repositories.ProductRegistryRepository;
import ru.inno.task5.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TransactionalService {
    private final ProductRepository productRepository;
    private final ProductRegistryRepository productRegisterRepo;
    private final AgreementRepository agreementRepository;

    public TransactionalService(ProductRepository productRepository,
                                ProductRegistryRepository productRegisterRepo,
                                AgreementRepository agreementRepository) {
        this.productRepository = productRepository;
        this.productRegisterRepo = productRegisterRepo;
        this.agreementRepository = agreementRepository;
    }

    @Transactional
    public ResponseRegistry saveProductAndProductRegistry(Product product, ProductRegistry productRegistry) {
        ResponseRegistry data = new ResponseRegistry();

        productRepository.save(product);
        log.info("Сохранили продукт в БД и получили product id=" + product.getId());

        productRegistry.setProduct_id(product.getId());
        log.info(String.valueOf(productRegistry));

        productRegisterRepo.save(productRegistry);
        log.info("Сохранили продуктовый Регистр в БД с id=" + productRegistry.getId());
        data.setAccountId(productRegistry.getId().toString());
        return data;
    }

    @Transactional
    public ResponseProduct saveProductAndProductRegistry(Product product, List<ProductRegistry> productRegistryList,
                                              List<Agreement> agreementList) {
        ArrayList<String> registerId = new ArrayList<>();
        ArrayList<String> supplementaryAgreementId = new ArrayList<>();
        ResponseProduct data = new ResponseProduct();

        productRepository.save(product);
        Long productId = product.getId();
        data.setInstanceId(productId.toString());
        log.info("Сохранили продукт в БД и получили product id=" + productId);

        for (ProductRegistry productRegistry : productRegistryList) {
            productRegistry.setProduct_id(productId);
            productRegisterRepo.save(productRegistry);
            registerId.add(productRegistry.getId().toString());
            log.info("Сохранили продуктовый Регистр в БД с id=" + productRegistry.getId());
        }
        String[] arrayProductRegister = registerId.toArray(new String[0]);
        data.setRegisterId(arrayProductRegister);

        for (Agreement agreement : agreementList) {
            agreement.setProduct_id(productId);
            agreementRepository.save(agreement);
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
            agreementRepository.save(agreement);
            supplementaryAgreementId.add(agreement.getId().toString());
            log.info("Сохранили Доп.соглашение к договору (id=" + productId + ") в БД с id=" + agreement.getId());
        }

        String[] arrayAgreementId = supplementaryAgreementId.toArray(new String[0]);
        data.setSupplementaryAgreementId(arrayAgreementId);
        return data;
    }
}
