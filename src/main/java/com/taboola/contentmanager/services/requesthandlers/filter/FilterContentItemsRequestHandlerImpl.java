package com.taboola.contentmanager.services.requesthandlers.filter;

import com.taboola.contentmanager.contracts.ContentItemContract;
import com.taboola.contentmanager.dal.Brand;
import com.taboola.contentmanager.dal.ContentItem;
import com.taboola.contentmanager.dal.Country;
import com.taboola.contentmanager.dal.Error;
import com.taboola.contentmanager.models.contracts.ContentManagerCrudResponse;
import com.taboola.contentmanager.models.contracts.FilterContentItemsRequest;
import com.taboola.contentmanager.models.contracts.GetAllContentItemsResponse;
import com.taboola.contentmanager.services.ErrorsContainer;
import com.taboola.contentmanager.services.dal.BrandsRepo;
import com.taboola.contentmanager.services.dal.ContentItemCustomOps;
import com.taboola.contentmanager.services.dal.CountriesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilterContentItemsRequestHandlerImpl implements FilterContentItemsRequestHandler {

    private final ContentItemCustomOps contentItemCustomOps;
    private final BrandsRepo brandsRepo;
    private final CountriesRepo countriesRepo;
    private final ErrorsContainer errorsContainer;

    @Autowired
    public FilterContentItemsRequestHandlerImpl(ContentItemCustomOps contentItemCustomOps, BrandsRepo brandsRepo, CountriesRepo countriesRepo, ErrorsContainer errorsContainer) {
        this.contentItemCustomOps = contentItemCustomOps;
        this.brandsRepo = brandsRepo;
        this.countriesRepo = countriesRepo;
        this.errorsContainer = errorsContainer;
    }

    @Override
    public ContentManagerCrudResponse filter(FilterContentItemsRequest filterContentItemsRequest) {
        try {
            List<String> brandNames = filterContentItemsRequest.getBrands();
            List<Brand> brands = brandsRepo.findByNameIn(brandNames);
            List<String> brandIds = brands.stream().map(Brand::get_id).collect(Collectors.toList());
            if (!brandNames.isEmpty() && brandIds.isEmpty()) {
                Integer code = 401;
                Error error = errorsContainer.getErrors().get(code);
                String msg = error.getMessage() + " - brand: " + brandNames;
                return new ContentManagerCrudResponse(code, msg);
            }

            List<String> countryNames = filterContentItemsRequest.getCountries();
            List<Country> countries = countriesRepo.findByNameIn(countryNames);
            List<String> countryIds = countries.stream().map(Country::get_id).collect(Collectors.toList());
            if (!countryNames.isEmpty() && countryIds.isEmpty()) {
                Integer code = 401;
                Error error = errorsContainer.getErrors().get(code);
                String msg = error.getMessage() + " - country: " + countryNames;
                return new ContentManagerCrudResponse(code, msg);
            }

            boolean superBrand = filterContentItemsRequest.isSuperBrand();
            int from = filterContentItemsRequest.getFrom();
            int dataSize = filterContentItemsRequest.getDataSize();
            Page<ContentItem> filteredPage = contentItemCustomOps.getFiltered(countryIds, brandIds, superBrand, from, dataSize);
            long totalElements = filteredPage.getTotalElements();
            List<ContentItem> contentItems = filteredPage.get().collect(Collectors.toList());
            List<ContentItemContract> contentItemContracts = this.convertToContracts(contentItems);
            return new GetAllContentItemsResponse(200, "success", contentItemContracts, totalElements);
        }
        catch(Exception e){
            return new ContentManagerCrudResponse(500, e.getMessage());
        }
    }

    private List<ContentItemContract> convertToContracts(List<ContentItem> contentItems) {
        List<Brand> brandsFromDb = this.getBrandsFromDb(contentItems);
        List<Country> countriesFromDb = this.getCountriesFromDb(contentItems);
        List<ContentItemContract> contentItemContracts = new ArrayList<>();
        for (ContentItem contentItem : contentItems) {
            String brandId = contentItem.getBrandId();
            String brandName = brandsFromDb.stream()
                    .filter(brand -> brand.get_id().equals(brandId))
                    .findAny()
                    .get()
                    .getName();
            String countryId = contentItem.getCountryId();
            String countryName = countriesFromDb.stream()
                    .filter(country -> country.get_id().equals(countryId))
                    .findAny()
                    .get()
                    .getName();
            String img = contentItem.getImg();
            String title = contentItem.getTitle();
            String contentItemId = contentItem.get_id();
            contentItemContracts.add(new ContentItemContract(contentItemId, countryName, brandName, title, img));

        }
        return contentItemContracts;
    }

    private List<Country> getCountriesFromDb(List<ContentItem> contentItems ){
        Set<String> countryIds = contentItems.stream().map(ContentItem::getCountryId).collect(Collectors.toSet());
        return countriesRepo.findBy_idIn(new ArrayList<>(countryIds));
    }

    private List<Brand> getBrandsFromDb(List<ContentItem> contentItems ){
        Set<String> brandIds = contentItems.stream().map(ContentItem::getBrandId).collect(Collectors.toSet());
        return brandsRepo.findBy_idIn(new ArrayList<>(brandIds));
    }
}
