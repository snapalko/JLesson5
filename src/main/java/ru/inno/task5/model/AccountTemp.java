package ru.inno.task5.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
//@NoArgsConstructor
public class AccountTemp {
    @Id
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
    private String accounts;
}
