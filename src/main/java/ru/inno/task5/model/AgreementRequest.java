package ru.inno.task5.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Component
public class AgreementRequest {
    private String generalAgreementId;          // ID доп.Ген.соглашения
    private String SupplementaryAgreementId;    // ID доп.соглашения
    private String arrangementType;             // Тип соглашения Enum, НСО/ЕЖО/СМО/ДБДС и тд, см. актуальную ЛМД
    private Integer shedulerJobId;              // Идентификатор задания/расписания периодичность учета/расчета/выплаты фиксируется в поле name

    @NotNull @NotBlank
    private String number;                                  // Номер ДС
    @NotNull
    private LocalDate openingDate;                          // Дата заключения сделки (НСО/ЕЖО/СМО/ДБДС)

    private LocalDate closingDate;                          // Дата окончания сделки
    private LocalDate cancelDate;                           // Дата отзыва сделки
    private Integer validityDuration;                       // Срок действия сделки
    private String cancellationReason;                      // Причина расторжения
    private String status;                                  // Статус ДС: закрыт, открыт
    private LocalDate interestCalculationDate;              // Начисление начинается с (дата)
    private BigDecimal interestRate;                        // Процент начисления на остаток
    private BigDecimal coefficient;                         // Показатель управления ставкой
    private String coefficientAction;                       // Повышающий/понижающий enum +/-
    private BigDecimal minimumInterestRate;                 // Минимум по ставке
    private String minimumInterestRateCoefficient;          // Коэффициент по минимальной ставке
    private String minimumInterestRateCoefficientAction;    // Повышающий/понижающий enum +/-
    private BigDecimal maximalnterestRate;                  // Максимум по ставке
    private BigDecimal maximalnterestRateCoefficient;       // Коэффициент по максимальной ставке
    private String maximalnterestRateCoefficientAction;     // Повышающий/понижающий enum +/-
}
