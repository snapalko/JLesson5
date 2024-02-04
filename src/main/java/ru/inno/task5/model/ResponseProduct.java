package ru.inno.task5.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "data")
public class ResponseProduct {
    @JsonProperty("instanceId")
    private String instanceId;
    @JsonProperty("registerId")
    private String[] registerId;
    @JsonProperty("supplementaryAgreementId")
    private String[] supplementaryAgreementId;
}
