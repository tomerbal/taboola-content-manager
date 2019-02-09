package com.taboola.contentmanager.controllers;

import com.taboola.contentmanager.models.ContentManagerCrudResponse;
import com.taboola.contentmanager.services.requesthandlers.brand.BrandCrudRequestHandler;
import com.taboola.contentmanager.services.requesthandlers.country.CountryCrudRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/brand")
public class BrandCrudController {

    private final BrandCrudRequestHandler brandCrudRequestHandler;

    @Autowired
    public BrandCrudController(BrandCrudRequestHandler brandCrudRequestHandler) {
        this.brandCrudRequestHandler = brandCrudRequestHandler;
    }

    @GetMapping("/get-all")
    public ContentManagerCrudResponse getAll() {
        return brandCrudRequestHandler.getAll();
    }
}
