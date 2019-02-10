package com.taboola.contentmanager.controllers;

import com.taboola.contentmanager.models.contracts.ContentManagerCrudResponse;
import com.taboola.contentmanager.services.requesthandlers.country.CountryCrudRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/country")
public class CountryCrudController {

    private final CountryCrudRequestHandler countryCrudRequestHandler;

    @Autowired
    public CountryCrudController( CountryCrudRequestHandler countryCrudRequestHandler) {
        this.countryCrudRequestHandler = countryCrudRequestHandler;
    }

    @GetMapping("/get-all")
    public ContentManagerCrudResponse getAll() {
        return countryCrudRequestHandler.getAll();
    }
}
