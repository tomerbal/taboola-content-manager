package com.taboola.contentmanager.models.contracts;

import com.taboola.contentmanager.dal.Country;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetAllCountriesResponse extends ContentManagerCrudResponse{

    private final List<Country> countries;

    public GetAllCountriesResponse(Integer status, String message, List<Country> countries) {
        super(status, message);
        this.countries = countries;
    }
}
