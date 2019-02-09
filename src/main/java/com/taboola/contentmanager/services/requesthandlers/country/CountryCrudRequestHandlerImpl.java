package com.taboola.contentmanager.services.requesthandlers.country;

import com.taboola.contentmanager.dal.Country;
import com.taboola.contentmanager.models.ContentManagerCrudResponse;
import com.taboola.contentmanager.models.GetAllCountriesResponse;
import com.taboola.contentmanager.services.dal.CountriesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryCrudRequestHandlerImpl implements CountryCrudRequestHandler {

    private final CountriesRepo countriesRepo;

    @Autowired
    public CountryCrudRequestHandlerImpl(CountriesRepo countriesRepo) {
        this.countriesRepo = countriesRepo;
    }

    @Override
    public ContentManagerCrudResponse getAll() {
        try {
            List<Country> countries = countriesRepo.findAll();
            return new GetAllCountriesResponse(200, "success", countries);
        }
        catch(Exception e){
            return new ContentManagerCrudResponse(500, e.getMessage());
        }
    }
}
