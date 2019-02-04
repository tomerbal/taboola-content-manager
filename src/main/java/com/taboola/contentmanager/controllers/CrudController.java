package com.taboola.contentmanager.controllers;

import com.taboola.contentmanager.models.ContentManagerCrudResponse;
import com.taboola.contentmanager.models.CreateContentItemRequest;
import com.taboola.contentmanager.services.requesthandlers.CrudRequestHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/content-item")
public class CrudController {

    private final CrudRequestHandler createGetARideRequest;

    @Autowired
    public CrudController(CrudRequestHandler createGetARideRequest) {
        this.createGetARideRequest = createGetARideRequest;
    }


    @PostMapping("/create")
    public ContentManagerCrudResponse create(@RequestBody @Valid CreateContentItemRequest createContentItemRequest) {
        return createGetARideRequest.create(createContentItemRequest);
    }
}
