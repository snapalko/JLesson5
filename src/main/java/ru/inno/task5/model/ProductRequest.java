package ru.inno.task5.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Component
public class ProductRequest {
    @Id
    private Long instanceId;
    @NotBlank
    private String productType;
    @NotBlank
    private String productCode;
    @NotBlank
    private String registerType;
    @NotBlank
    private String mdmCode;// ссылка на Клиента
    @NotBlank
    private String contractNumber;
    @NotNull
    private LocalDate contractDate;
    private LocalDate start_date_time;
    private LocalDate end_date_time;
    private Integer days;
    @NotNull
    private Integer priority;

    private BigDecimal interestRatePenalty;
    private BigDecimal minimalBalance;
    private BigDecimal thresholdAmount;
    private String accountingDetails;
    private String rateType;
    private BigDecimal taxPercentageRate;
    private BigDecimal technicalOverdraftLimitAmount;

    @NotNull
    private Long contractId;
    @NotNull
    private String BranchCode;
    @NotBlank
    private String IsoCurrencyCode;
    @NotBlank
    private String urgencyCode;

    private int ReferenceCode;
    private String reason_close;
    private String state;
    private AgreementRequest[] arrangements;
}
