package com.taboola.contentmanager.services.requesthandlers.brand;

import com.taboola.contentmanager.dal.Brand;
import com.taboola.contentmanager.models.ContentManagerCrudResponse;
import com.taboola.contentmanager.models.GetAllBrandsResponse;
import com.taboola.contentmanager.services.dal.BrandsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BrandCrudRequestHandlerImpl implements BrandCrudRequestHandler {

    private final BrandsRepo brandsRepo;

    @Autowired
    public BrandCrudRequestHandlerImpl(BrandsRepo brandsRepo) {
        this.brandsRepo = brandsRepo;
    }

    @Override
    public ContentManagerCrudResponse getAll() {
        try {
            List<Brand> brandItems = brandsRepo.findAll();
            Set<String> brands = brandItems.stream().map(Brand::getName).collect(Collectors.toSet());
            return new GetAllBrandsResponse(200, "success", brands);
        }
        catch(Exception e){
            return new ContentManagerCrudResponse(500, e.getMessage());
        }
    }
}
