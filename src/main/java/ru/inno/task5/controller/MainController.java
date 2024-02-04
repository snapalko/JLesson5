package ru.inno.task5.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.inno.task5.model.ProductRegistryRequest;
import ru.inno.task5.model.ProductRequest;
import ru.inno.task5.model.ResponseProduct;
import ru.inno.task5.model.ResponseRegistry;
import ru.inno.task5.service.ProductRegistryService;
import ru.inno.task5.service.ProductService;
import ru.inno.task5.util.Converter;

@Slf4j
@RestController
@Validated
@RestControllerAdvice
public class MainController {
    private final ProductRegistryService productRegistryService;
    private final ProductService productService;

    public MainController(ProductRegistryService productRegistryService,
                          ProductService productService) {
        this.productRegistryService = productRegistryService;
        this.productService = productService;
    }

    @PostMapping(value = "/corporate-settlement-account/create")
    public ResponseEntity<String> createSettlementAccount(@Valid @RequestBody ProductRegistryRequest productRegistryRequest) {
        log.info("...Сработал createSettlementAccount()");
        log.info(String.valueOf(productRegistryRequest));

        ResponseRegistry responseRegistry = productRegistryService.createProductRegistry(productRegistryRequest);
        Converter converter = new Converter();
        String response;
        try {
            response = converter.JSONToString(responseRegistry);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/corporate-settlement-instance/create")
    public ResponseEntity<String> createSettlementInstance(@Valid @RequestBody ProductRequest productRequest) {
        log.info("...Сработал createSettlementInstance()");
        log.info(String.valueOf(productRequest));

        ResponseProduct responseProduct = productService.createProduct(productRequest);
        Converter converter = new Converter();
        String response;
        try {
            response = converter.JSONToString(responseProduct);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(response);
    }
}
