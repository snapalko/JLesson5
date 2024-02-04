package ru.inno.task5.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.inno.task5.model.*;
import ru.inno.task5.repositories.*;
import ru.inno.task5.util.Converter;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProductRegistryServiceTest {
    @Mock
    private ProductService productService;
    @Mock
    private ProductRegistryRepository productRegistryRepository;
    @Mock
    private RefProductClassRepository refProductClassRepository;
    @Mock
    private RefProductRegistryTypeRepository refProductRegistryTypeRepository;
    @Mock
    private AccountPoolRepository accountPoolRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private TransactionalService transactionalService;

    @InjectMocks
    ProductRegistryService productRegistryService;

    @Test
    @DisplayName("Тест сервиса создания Продуктового регистра")
    void createProductRegisterTest() {
        ProductRegistryRequest request;
        // Получаем тело запроса на создание ПР из JSON-файла
        request = Converter.fileToProductRegistryRequest("ProductRegistryRequest.json");
        System.out.println(request);

        // В базе у договора нет регистра заданного типа
        when(productRegistryRepository.findByProductIdAndType(request.getInstanceId(), request.getRegistryTypeCode())).thenReturn(Optional.empty());
        // Ищем код продукта
        RefProductRegistryType refProductRegistryType = new RefProductRegistryType(1L, request.getRegistryTypeCode(), "Хранение ДМ.", "03.012.002", "Клиентский");
        when(refProductRegistryTypeRepository.findOneByValue(request.getRegistryTypeCode())).thenReturn(Optional.of(refProductRegistryType));

        AccountTemp accountTemp = new AccountTemp(1L, "0022", "800", "15", "00", "03.012.002_47533_ComSoLd", "475335516415314841861");
        when(accountPoolRepository.findAccount(request.getBranchCode(), request.getCurrencyCode(), request.getMdmCode(), request.getPriorityCode(),
                request.getRegistryTypeCode())).thenReturn(Optional.of(accountTemp));

        RefProductClass refProductClass = new RefProductClass(1L, "03.012.002", "03", "Розничный бизнес", "012", "Драг. металлы", "002", "Хранение");
        when(refProductClassRepository.findOneByValue(
                refProductRegistryType.getProduct_class_code())).thenReturn(Optional.of(refProductClass));

        when(productService.getClientByMdm("РЖД")).thenReturn(159753L);
        when(productService.getProductNumber()).thenReturn("2142");
        when(productService.getProductPriority()).thenReturn(5);
        when(productService.getDateOfConclusion()).thenReturn(LocalDate.now());

        when(productRepository.save(any())).then(returnsFirstArg());
        when(productRegistryRepository.save(any())).then(returnsFirstArg());

        Product product = new Product(1L, 159753L, "Розничный бизнес", "2142", 5, LocalDate.now());
        ProductRegistry productRegistry = new ProductRegistry(null, "03.012.002_47533_ComSoLd", 1L, "800", "Открыт", "475335516415314841861");

        ResponseRegistry responseRegistry = new ResponseRegistry("1");
        when(transactionalService.saveProductAndProductRegistry(product, productRegistry)).thenReturn(responseRegistry);

        when(productRegistryService.createProductRegistry(request)).thenReturn(responseRegistry);
        responseRegistry = productRegistryService.createProductRegistry(request);

        Assertions.assertEquals("1", responseRegistry.getAccountId());

        System.out.println("test: responseRegistry=" + responseRegistry);
        System.out.println("test: Ok");
    }
}