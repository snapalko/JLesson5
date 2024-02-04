package ru.inno.task5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity(name = "tpp_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_code_id")
    Long product_code_id;
    @NotNull
    @Column(name = "client_id")
    Long client_id;
    @NotNull
    @Column(name = "type")
    String type;
    @NotBlank
    @Column(name = "number")
    String number;
    @NotNull
    @Column(name = "priority")
    Long priority;
    @NotNull
    @Column(name = "date_of_conclusion")
    LocalDate date_of_conclusion;
    @Column(name = "start_date_time")
    LocalDate start_date_time;
    @Column(name = "end_date_time")
    LocalDate end_date_time;
    @Column(name = "days")
    Integer days;
    @Column(name = "penalty_rate")
    BigDecimal penalty_rate; // штрафная ставка
    @Column(name = "nso")
    BigDecimal nso; // неснижаемый остаток
    @Column(name = "threshold_amount")
    BigDecimal threshold_amount; // пороговая сумма
    @Column(name = "requisite_type")
    String requisite_type; // тип реквизита
    @Column(name = "interest_rate_type")
    String interest_rate_type; // тип процентной ставки
    @Column(name = "tax_rate")
    BigDecimal tax_rate; // ставка налога
    @Column(name = "reason_close")
    String reason_close;
    @Column(name = "state")
    String state = "Открыт";

    public Product(Long product_code_id, Long client_id,
                   String type, String number,
                   long priority, LocalDate date_of_conclusion) {

        this.product_code_id = product_code_id;
        this.client_id = client_id;
        this.type = type;
        this.number = number;
        this.priority = priority;
        this.date_of_conclusion = date_of_conclusion;
    }

    public Product(Long product_code_id, Long client_id,
                   String type, String number,
                   long priority, LocalDate date_of_conclusion,
                   LocalDate start_date_time, LocalDate end_date_time, Integer days,
                   BigDecimal penalty_rate, BigDecimal nso, BigDecimal threshold_amount,
                   String requisite_type, String interest_rate_type, BigDecimal tax_rate,
                   String reason_close, String state) {

        this.product_code_id = product_code_id;
        this.client_id = client_id;
        this.type = type;
        this.number = number;
        this.priority = priority;
        this.date_of_conclusion = date_of_conclusion;
        this.start_date_time = start_date_time;
        this.end_date_time = end_date_time;
        this.days = days;
        this.penalty_rate = penalty_rate;
        this.nso = nso;
        this.threshold_amount = threshold_amount;
        this.requisite_type = requisite_type;
        this.interest_rate_type = interest_rate_type;
        this.tax_rate = tax_rate;
        this.reason_close = reason_close;
        this.state = state;
    }
}
