package com.taboola.contentmanager.services.requesthandlers;

import com.taboola.contentmanager.dal.ContentItem;
import com.taboola.contentmanager.dal.Country;
import com.taboola.contentmanager.dal.Error;
import com.taboola.contentmanager.models.ContentManagerCrudResponse;
import com.taboola.contentmanager.models.CreateContentItemRequest;
import com.taboola.contentmanager.services.dal.ContentItemRepo;
import com.taboola.contentmanager.services.dal.CountriesRepo;
import com.taboola.contentmanager.services.dal.ErrorsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CrudRequestHandlerImpl implements CrudRequestHandler{

    private final ContentItemRepo contentItemRepo;
    private final CountriesRepo countriesRepo;
    private final ErrorsRepo errorsRepo;

    @Autowired
    public CrudRequestHandlerImpl(ContentItemRepo contentItemRepo, CountriesRepo countriesRepo, ErrorsRepo errorsRepo) {
        this.contentItemRepo = contentItemRepo;
        this.countriesRepo = countriesRepo;
        this.errorsRepo = errorsRepo;
    }

    @Override
    public ContentManagerCrudResponse create(CreateContentItemRequest createContentItemRequest) {
        try {
            String countryCode = createContentItemRequest.getCountryCode();
            Optional<Country> optionalCountry = countriesRepo.getByCode(countryCode);
            if (!optionalCountry.isPresent()) {
                Integer code = 401;
                Error error = errorsRepo.getByCode(code).get();
                String msg = error.getMessage() + " - country";
                return new ContentManagerCrudResponse(code, msg);
            }

            Country country = optionalCountry.get();
            String brand = createContentItemRequest.getBrand();
            String img = createContentItemRequest.getImg();
            String title = createContentItemRequest.getTitle();
            ContentItem contentItem = new ContentItem(country.getCode(), brand, title, img);
            contentItemRepo.insert(contentItem);
            return new ContentManagerCrudResponse(200, "success");
        }
        catch(Exception e){
            return new ContentManagerCrudResponse(500, e.getMessage());
        }
    }
}
