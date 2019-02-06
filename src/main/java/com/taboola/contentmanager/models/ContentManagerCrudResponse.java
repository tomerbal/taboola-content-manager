package com.taboola.contentmanager.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ContentManagerCrudResponse {
    private final Integer status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String message;
}
