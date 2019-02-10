package com.taboola.contentmanager.services.requesthandlers.contentitem;

import com.taboola.contentmanager.contracts.ContentItemContract;
import com.taboola.contentmanager.dal.Brand;
import com.taboola.contentmanager.dal.ContentItem;
import com.taboola.contentmanager.dal.Country;
import com.taboola.contentmanager.dal.Error;
import com.taboola.contentmanager.models.contracts.*;
import com.taboola.contentmanager.services.ErrorsContainer;
import com.taboola.contentmanager.services.dal.BrandsRepo;
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
    private final BrandsRepo brandsRepo;
    private final ErrorsContainer errorsContainer;

    @Autowired
    public ContentItemCrudRequestHandlerImpl(ContentItemRepo contentItemRepo, CountriesRepo countriesRepo, BrandsRepo brandsRepo, ErrorsContainer errorsContainer) {
        this.contentItemRepo = contentItemRepo;
        this.countriesRepo = countriesRepo;
        this.brandsRepo = brandsRepo;
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
                String msg = error.getMessage() + " - country: " + countryName;
                return new ContentManagerCrudResponse(code, msg);
            }

            String brandName = createContentItemRequest.getBrand();

            Optional<Brand> optionalBrand = brandsRepo.findByName(brandName);
            if (!optionalBrand.isPresent()) {
                Integer code = 401;
                Error error = errorsContainer.getErrors().get(code);
                String msg = error.getMessage() + " - brand: " + brandName;
                return new ContentManagerCrudResponse(code, msg);
            }

            Country country = optionalCountry.get();
            Brand brand = optionalBrand.get();
            String img = createContentItemRequest.getImg();
            String title = createContentItemRequest.getTitle();
            ContentItem contentItem = new ContentItem(country.get_id(), brand.get_id(), title, img);
            contentItemRepo.insert(contentItem);
            return new ContentManagerCrudResponse(200, "success");
        } catch (Exception e) {
            return new ContentManagerCrudResponse(500, e.getMessage());
        }
    }

    @Override
    public ContentManagerCrudResponse getAll(Integer from, Integer dataSize) {
        try {

            Page<ContentItem> contentItems = contentItemRepo.findAll(PageRequest.of(from, dataSize));
            long totalElements = contentItems.getTotalElements();
            List<ContentItem> contentItemList = contentItems.get().collect(Collectors.toList());

            Set<String> countryIds = contentItemList.stream().map(ContentItem::getCountryId).collect(Collectors.toSet());
            Iterable<Country> countryIterable = countriesRepo.findAllById(new ArrayList<>(countryIds));
            List<Country> countries = new ArrayList<>();
            countryIterable.forEach(countries::add);
            Map<String, Country> countryMap = countries.stream().collect(Collectors.toMap(Country::get_id,
                    Function.identity()));

            Set<String> brandIds = contentItemList.stream().map(ContentItem::getBrandId).collect(Collectors.toSet());
            Iterable<Brand> brandIterable = brandsRepo.findAllById(new ArrayList<>(brandIds));
            List<Brand> brands = new ArrayList<>();
            brandIterable.forEach(brands::add);
            Map<String, Brand> brandMap = brands.stream().collect(Collectors.toMap(Brand::get_id,
                    Function.identity()));

            List<ContentItemContract> contentItemContracts = new ArrayList<>();
            for (ContentItem contentItem : contentItemList) {
                Country country = countryMap.get(contentItem.getCountryId());
                String countryName = country.getName();
                Brand brand = brandMap.get(contentItem.getBrandId());
                String brandName = brand.getName();
                String title = contentItem.getTitle();
                String img = contentItem.getImg();
                ContentItemContract contentItemContract = new ContentItemContract(contentItem.get_id(), countryName, brandName, title, img);
                contentItemContracts.add(contentItemContract);
            }
            return new GetAllContentItemsResponse(200, "success", contentItemContracts, totalElements);
        } catch (Exception e) {
            return new ContentManagerCrudResponse(500, e.getMessage());
        }
    }

    @Override
    public ContentManagerCrudResponse delete(DeleteContentItemRequest deleteContentItemRequest) {
        try {
            contentItemRepo.deleteById(deleteContentItemRequest.getItemId());
            return new ContentManagerCrudResponse(200, "success");
        } catch (Exception e) {
            return new ContentManagerCrudResponse(500, e.getMessage());
        }
    }

    @Override
    public ContentManagerCrudResponse edit(EditContentItemRequest editContentItemRequest) {
        try {
            String itemId = editContentItemRequest.getItemId();
            Optional<ContentItem> itemOptional = contentItemRepo.findById(itemId);
            if (!itemOptional.isPresent()){
                int errorCode = 402;
                Error error = errorsContainer.getErrors().get(errorCode);
                return new ContentManagerCrudResponse(errorCode, error.getMessage() + ", id: " + itemId);
            }

            ContentItem contentItem = itemOptional.get();
            String brandName = editContentItemRequest.getBrand();

            Optional<Brand> optionalBrand = brandsRepo.findByName(brandName);
            if (!optionalBrand.isPresent()) {
                Integer code = 401;
                Error error = errorsContainer.getErrors().get(code);
                String msg = error.getMessage() + " - brand: " + brandName;
                return new ContentManagerCrudResponse(code, msg);
            }

            String countryName = editContentItemRequest.getCountry();
            Optional<Country> optionalCountry = countriesRepo.findByName(countryName);
            if (!optionalCountry.isPresent()) {
                Integer code = 401;
                Error error = errorsContainer.getErrors().get(code);
                String msg = error.getMessage() + " - country: " + countryName;
                return new ContentManagerCrudResponse(code, msg);
            }

             String img = editContentItemRequest.getImg();
             String title = editContentItemRequest.getTitle();
             contentItem.setBrandId(optionalBrand.get().get_id());
             contentItem.setCountryId(optionalCountry.get().get_id());
             contentItem.setImg(img);
             contentItem.setTitle(title);

             contentItemRepo.save(contentItem);

            return new ContentManagerCrudResponse(200, "success");
        } catch (Exception e) {
            return new ContentManagerCrudResponse(500, e.getMessage());
        }
    }
}
