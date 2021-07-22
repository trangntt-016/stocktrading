package com.canada.edu.stocktrading.factory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class GeneralResponse implements Serializable{
    private static final long serialVersionUID = 1L;

    @JsonProperty("status")
    private ResponseStatus status;

    @JsonProperty("data")
    private Object data;
}
