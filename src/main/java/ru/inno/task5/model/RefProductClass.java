package ru.inno.task5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "tpp_ref_product_class")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RefProductClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "internal_id")
    private Long internal_id;

    @Column(name = "value")
    @NotNull
    private String value;

    @Column(name = "gbl_code")
    @NotNull
    private String gbl_code;

    @Column(name = "gbl_name")
    @NotNull
    private String gbl_name;

    @Column(name = "product_row_code")
    @NotNull
    private String product_row_code;

    @Column(name = "product_row_name")
    @NotNull
    private String product_row_name;

    @Column(name = "subclass_code")
    @NotNull
    private String subclass_code;

    @Column(name = "subclass_name")
    @NotNull
    private String subclass_name;
}