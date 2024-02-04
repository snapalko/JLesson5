package ru.inno.task5.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.inno.task5.model.ProductRegistryRequest;
import ru.inno.task5.model.ProductRequest;
import ru.inno.task5.model.ResponseProduct;
import ru.inno.task5.model.ResponseRegistry;

import java.io.File;
import java.io.IOException;

public class Converter {
    public String JSONToString(ResponseRegistry responseRegistry) throws JsonProcessingException {
        String result;

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        result = mapper.writeValueAsString(responseRegistry);
        return result;
    }

    public String JSONToString(ResponseProduct responseProduct) throws JsonProcessingException {
        String result;

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        result = mapper.writeValueAsString(responseProduct);
        return result;
    }

    public static ProductRegistryRequest fileToProductRegistryRequest(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        ProductRegistryRequest request;
        try {
            File file = new File("src/test/resources/" + fileName);
            request = mapper.readValue(file, ProductRegistryRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return request;
    }

    public static ProductRequest fileToProductRequest(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        ProductRequest request;
        try {
            File file = new File("src/test/resources/" + fileName);
            request = mapper.readValue(file, ProductRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return request;
    }
}
