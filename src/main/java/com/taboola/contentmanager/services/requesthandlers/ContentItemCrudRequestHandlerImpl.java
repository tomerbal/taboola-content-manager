package com.taboola.contentmanager.services.requesthandlers;

import com.taboola.contentmanager.contracts.ContentItemContract;
import com.taboola.contentmanager.dal.ContentItem;
import com.taboola.contentmanager.dal.Country;
import com.taboola.contentmanager.dal.Error;
import com.taboola.contentmanager.models.ContentManagerCrudResponse;
import com.taboola.contentmanager.models.CreateContentItemRequest;
import com.taboola.contentmanager.models.GetAllContentItemsResponse;
import com.taboola.contentmanager.services.ErrorsContainer;
import com.taboola.contentmanager.services.dal.ContentItemRepo;
import com.taboola.contentmanager.services.dal.CountriesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ContentItemCrudRequestHandlerImpl implements ContentItemCrudRequestHandler {

    private final ContentItemRepo contentItemRepo;
    private final CountriesRepo countriesRepo;
    private final ErrorsContainer errorsContainer;

    @Autowired
    public ContentItemCrudRequestHandlerImpl(ContentItemRepo contentItemRepo, CountriesRepo countriesRepo, ErrorsContainer errorsContainer) {
        this.contentItemRepo = contentItemRepo;
        this.countriesRepo = countriesRepo;
        this.errorsContainer = errorsContainer;
    }

    @Override
    public ContentManagerCrudResponse create(CreateContentItemRequest createContentItemRequest) {
        try {
            String countryName = createContentItemRequest.getCountry();
            Optional<Country> optionalCountry = countriesRepo.findByName(countryName);
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
        try {
            Page<ContentItem> contentItems = contentItemRepo.findAll(PageRequest.of(from, dataSize));
            long totalElements = contentItems.getTotalElements();
            List<ContentItem> contentItemList = contentItems.get().collect(Collectors.toList());
            Set<String> countryCodes = contentItemList.stream().map(ContentItem::getCountryCode).collect(Collectors.toSet());
            List<Country> countries = countriesRepo.findByCodeIn(new ArrayList<>(countryCodes));
            Map<String, Country> countryMap = countries.stream().collect(Collectors.toMap(Country::getCode,
                    Function.identity()));
            List<ContentItemContract> contentItemContracts = new ArrayList<>();
            for (ContentItem contentItem : contentItemList) {
                Country country = countryMap.get(contentItem.getCountryCode());
                String countryName = country.getName();
                String brand = contentItem.getBrand();
                String title = contentItem.getTitle();
                String img = contentItem.getImg();
                ContentItemContract contentItemContract = new ContentItemContract(countryName, brand, title, img);
                contentItemContracts.add(contentItemContract);
            }
            return new GetAllContentItemsResponse(200, "success", contentItemContracts, totalElements);
        }
        catch(Exception e){
            return new ContentManagerCrudResponse(500, e.getMessage());
        }
    }
}
