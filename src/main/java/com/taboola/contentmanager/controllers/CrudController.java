package com.taboola.contentmanager.controllers;

import com.taboola.contentmanager.dal.Error;
import com.taboola.contentmanager.models.ContentManagerCrudResponse;
import com.taboola.contentmanager.models.CreateContentItemRequest;
import com.taboola.contentmanager.services.ErrorsContainer;
import com.taboola.contentmanager.services.requesthandlers.CrudRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/content-item")
public class CrudController {

    private final CrudRequestHandler crudRequestHandler;
    private final ErrorsContainer errorsContainer;

    @Autowired
    public CrudController(CrudRequestHandler crudRequestHandler, ErrorsContainer errorsContainer) {
        this.crudRequestHandler = crudRequestHandler;
        this.errorsContainer = errorsContainer;
    }


    @PostMapping("/create")
    public ContentManagerCrudResponse create(@RequestBody @Valid CreateContentItemRequest createContentItemRequest) {
        return crudRequestHandler.create(createContentItemRequest);
    }


    @GetMapping("/get-all-content-item/{from}/{dataSize}")
    public ContentManagerCrudResponse getAll(@PathVariable Optional<Integer> from, @PathVariable Optional<Integer> dataSize) {
        Integer errorCode = 400;
        Error error = errorsContainer.getErrors().get(errorCode);
        if (!from.isPresent()){
            return new ContentManagerCrudResponse(errorCode, error.getMessage() + " - from");

        }
        if (!dataSize.isPresent()){
            return new ContentManagerCrudResponse(errorCode, error.getMessage() + " - dataSize");

        }
        return crudRequestHandler.getAll(from.get(), dataSize.get());
    }
}
