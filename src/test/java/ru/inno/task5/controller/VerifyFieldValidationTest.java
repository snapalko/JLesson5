package ru.inno.task5.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class VerifyFieldValidationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Проверка маппинга и десериализации HTTP-запросов")
    void productRegistryTest() throws Exception {
        // Передаем JSON с заведомо неправильным значением поля instanceId, который не должен быть NULL
        this.mockMvc.perform(post("/corporate-settlement-account/create")
                        .content("""
                                {
                                    "instanceId": null,
                                    "registryTypeCode": "03.012.002_47533_ComSoLd",
                                    "accountType": "Клиентский",
                                    "currencyCode": "800",
                                    "branchCode": "0022",
                                    "priorityCode": "00",
                                    "mdmCode": "15",
                                    "clientCode": "РЖД",
                                    "trainRegion": null,
                                    "counter": null,
                                    "salesCode": null
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isBadRequest());
        // И получаем статус 400 {"fieldName":"instanceId","message":"must not be null"}
    }
}