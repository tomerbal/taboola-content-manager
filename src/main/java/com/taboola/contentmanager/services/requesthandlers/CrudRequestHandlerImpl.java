package com.taboola.contentmanager.services.requesthandlers;

import com.taboola.contentmanager.dal.ContentItem;
import com.taboola.contentmanager.dal.Country;
import com.taboola.contentmanager.dal.Error;
import com.taboola.contentmanager.models.ContentManagerCrudResponse;
import com.taboola.contentmanager.models.CreateContentItemRequest;
import com.taboola.contentmanager.models.GetAllContentItemsResponse;
import com.taboola.contentmanager.services.ErrorsContainer;
import com.taboola.contentmanager.services.dal.ContentItemRepo;
import com.taboola.contentmanager.services.dal.CountriesRepo;
import com.taboola.contentmanager.services.dal.ErrorsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CrudRequestHandlerImpl implements CrudRequestHandler{

    private final ContentItemRepo contentItemRepo;
    private final CountriesRepo countriesRepo;
    private final ErrorsContainer errorsContainer;

    @Autowired
    public CrudRequestHandlerImpl(ContentItemRepo contentItemRepo, CountriesRepo countriesRepo, ErrorsContainer errorsContainer) {
        this.contentItemRepo = contentItemRepo;
        this.countriesRepo = countriesRepo;
        this.errorsContainer = errorsContainer;
    }

    @Override
    public ContentManagerCrudResponse create(CreateContentItemRequest createContentItemRequest) {
        try {
            String countryCode = createContentItemRequest.getCountryCode();
            Optional<Country> optionalCountry = countriesRepo.getByCode(countryCode);
            if (!optionalCountry.isPresent()) {
                Integer code = 401;
                Error error = errorsContainer.getErrors().get(code);
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

    @Override
    public ContentManagerCrudResponse getAll(Integer from, Integer dataSize) {
        Page<ContentItem> contentItems = contentItemRepo.findAll(PageRequest.of(from, dataSize));
        long totalElements = contentItems.getTotalElements();
        List<ContentItem> contentItemList = contentItems.get().collect(Collectors.toList());
        return new GetAllContentItemsResponse(200, "success", contentItemList, totalElements);
    }
}
