package ru.inno.task5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "tpp_ref_product_register_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RefProductRegistryType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "internal_id")
    private Long internal_id;

    @Column(name = "value")
    @NotNull
    private String value;

    @Column(name = "register_type_name")
    @NotNull
    private String register_type_name;

    @Column(name = "product_class_code")
    @NotNull
    private String product_class_code;

    @Column(name = "account_type")
    @NotNull
    private String account_type;
}
