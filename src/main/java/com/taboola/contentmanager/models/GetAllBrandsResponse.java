package com.taboola.contentmanager.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetAllBrandsResponse extends ContentManagerCrudResponse{

    private final Set<String> brands;

    public GetAllBrandsResponse(Integer status, String message, Set<String> brands) {
        super(status, message);
        this.brands = brands;
    }
}
