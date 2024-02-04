package ru.inno.task5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity(name = "agreements")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Agreement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // ID доп.соглашения

    @NotNull
    @Column(name = "product_id")
    private Long product_id; // ID экземпляра продукта

    @Column(name = "generalagreementid")
    private Long generalAgreementId; // ID доп.Ген.соглашения
    @Column(name = "arrangementtype")
    private String arrangementType; // Тип соглашения Enum, НСО/ЕЖО/СМО/ДБДС и тд, см. актуальную ЛМД
    @Column(name = "shedulerjobid")
    private Integer shedulerJobId; // Идентификатор задания/расписания периодичность учета/расчета/выплаты фиксируется в поле name

    @NotBlank
    @Column(name = "number")
    private String number; // Номер ДС
    @NotNull @Column(name = "openingdate")
    private LocalDate openingDate; // Дата заключения сделки (НСО/ЕЖО/СМО/ДБДС)

    @Column(name = "closingdate")
    private LocalDate closingDate; // Дата окончания сделки
    @Column(name = "canceldate")
    private LocalDate cancelDate; // Дата отзыва сделки
    @Column(name = "validityduration")
    private Integer validityDuration; // Срок действия сделки
    @Column(name = "cancellationreason")
    private String cancellationReason; // Причина расторжения
    @Column(name = "status")
    private String status; // Статус ДС: закрыт, открыт
    @Column(name = "interestcalculationdate")
    private LocalDate interestCalculationDate; // Начисление начинается с (дата)
    @Column(name = "interestrate")
    private BigDecimal interestRate; // Процент начисления на остаток
    @Column(name = "coefficient")
    private BigDecimal coefficient; // Показатель управления ставкой
    @Column(name = "coefficientaction")
    private String coefficientAction; // Повышающий/понижающий enum +/-
    @Column(name = "minimuminterestrate")
    private BigDecimal minimumInterestRate; // Минимум по ставке
    @Column(name = "minimuminterestratecoefficient")
    private String minimumInterestRateCoefficient; // Коэффициент по минимальной ставке
    @Column(name = "minimuminterestratecoefficientaction")
    private String minimumInterestRateCoefficientAction; // Повышающий/понижающий enum +/-
    @Column(name = "maximalnterestrate")
    private BigDecimal maximalnterestRate; // Максимум по ставке
    @Column(name = "maximalnterestratecoefficient")
    private BigDecimal maximalnterestRateCoefficient; // Коэффициент по максимальной ставке
    @Column(name = "maximalnterestratecoefficientaction")
    private String maximalnterestRateCoefficientAction; // Повышающий/понижающий enum +/-
}
