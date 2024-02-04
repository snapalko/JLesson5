package ru.inno.task5.model;

import lombok.Getter;

@Getter
public enum ArrangementType {
    NSO("НСО"),
    EJO("ЕЖО"),
    CMO("СМО"),
    DBDS("ДБДС"),
    DOGOVOR("договор");

    private final String code;

    ArrangementType(String code) {
        this.code = code;
    }
}
