package ru.inno.task5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "tpp_product_register")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductRegistry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    @NotNull
    private Long product_id;

    @Column(name = "type")
    private String type;

    @Column(name = "account_id")
    private Long account_id;

    @Column(name = "currency_code")
    private String currency_code;

    @Column(name = "state")
    private String state;

    @Column(name = "account_number")
    private String account_number;

    public ProductRegistry(Long product_id, String type, Long account_id,
                           String currency_code, String state, String account_number) {
        this.product_id = product_id;
        this.type = type;
        this.account_id = account_id;
        this.currency_code = currency_code;
        this.state = state;
        this.account_number = account_number;
    }
}
