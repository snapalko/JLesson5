package ru.inno.task5.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "account_pool")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account_pool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "branchcode")
    private String branchCode;
    @Column(name = "currencycode")
    private String currencyCode;
    @Column(name = "mdmcode")
    private String mdmCode;
    @Column(name = "prioritycode")
    private String priorityCode;
    @Column(name = "registrytypecode")
    private String registryTypeCode;
    @Column(name = "accounts")
    private String[] accounts;
}
