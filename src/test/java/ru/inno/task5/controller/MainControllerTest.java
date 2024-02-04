package ru.inno.task5.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.inno.task5.model.ProductRegistryRequest;
import ru.inno.task5.model.ProductRequest;
import ru.inno.task5.service.ProductRegistryService;
import ru.inno.task5.service.ProductService;
import ru.inno.task5.util.Converter;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MainController.class)
class MainControllerTest {
    @MockBean
    private ProductRegistryService productRegistryService;
    @MockBean
    ProductService productService; // Надо мокировать все бины(сервисы), инжектированные в MainController

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Тест, что Post-запрос на создание продуктового регистра возвращает статус HttpStatus.OK(200)")
    void createSettlementAccount() throws Exception {
        ProductRegistryRequest request;
        request = Converter.fileToProductRegistryRequest("ProductRegistryRequest.json");
        String requestJson = objectMapper.writeValueAsString(request);

        // тест, что Post-запрос возвращает статус HttpStatus.OK(200)
        mockMvc.perform(post("/corporate-settlement-account/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

        // тест, что метод createProductRegister вызывается только 1 раз
        verify(productRegistryService, times(1)).createProductRegistry(request);
    }

    @Test
    @DisplayName("Тест, что Post-запрос на создание продукта возвращает статус HttpStatus.OK(200)")
    void createSettlementInstance() throws Exception {
        ProductRequest productRequest;
        productRequest = Converter.fileToProductRequest("ProductRequest_with_Arrangements.json");
        String requestJson = objectMapper.writeValueAsString(productRequest);

        // тест, что Post-запрос возвращает статус HttpStatus.OK(200)
        mockMvc.perform(post("/corporate-settlement-instance/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

        // тест, что метод createProduct вызывается только 1 раз
        verify(productService, times(1)).createProduct(productRequest);
    }
}
