package ru.inno.task5.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ObjRequestBody {
    @NotNull
    private Long instanceId;

    private String registryTypeCode;
    @NotNull @NotBlank
    private String accountType;
    @NotNull @NotBlank
    private String currencyCode;
    @NotNull @NotBlank
    private String branchCode;
    @NotNull @NotBlank
    private String priorityCode;
    @NotNull @NotBlank
    private String mdmCode;
    @NotNull @NotBlank
    private String clientCode;

    private String trainRegion;
    private String counter;
    private String salesCode;
}
