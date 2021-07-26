package com.canada.edu.stocktrading.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatusCodeEnum {
    SUCCESS("ST200", "Success"),
    UPDATED("ST20001", "Resource updated successfully"),
    CREATED("ST201", "Created"),
    NO_PERMISSION("ST40000", "The client has no permission calling this API."),
    INVALID_PARAMETER("ST40001", "Invalid request parameter."),
    SCOPE_NOT_FOUND("ST40002", "Scope not found."),
    DUPLICATE_RECORD("ST40900", "Data has already existed."),
    RESOURCE_NOT_FOUND("ST404", "Resource not found."),
    INTERNAL_SERVER_ERROR("ST500", "Internal server error");
    private final String code;
    private final String message;
}