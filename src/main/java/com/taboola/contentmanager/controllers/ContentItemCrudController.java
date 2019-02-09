package com.taboola.contentmanager.controllers;

import com.taboola.contentmanager.dal.Error;
import com.taboola.contentmanager.models.ContentManagerCrudResponse;
import com.taboola.contentmanager.models.CreateContentItemRequest;
import com.taboola.contentmanager.services.ErrorsContainer;
import com.taboola.contentmanager.services.requesthandlers.contentitem.ContentItemCrudRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/content-item")
public class ContentItemCrudController {

    private final ContentItemCrudRequestHandler contentItemCrudRequestHandler;
    private final ErrorsContainer errorsContainer;

    @Autowired
    public ContentItemCrudController(ContentItemCrudRequestHandler contentItemCrudRequestHandler, ErrorsContainer errorsContainer) {
        this.contentItemCrudRequestHandler = contentItemCrudRequestHandler;
        this.errorsContainer = errorsContainer;
    }


    @PostMapping("/create")
    public ContentManagerCrudResponse create(@RequestBody @Valid CreateContentItemRequest createContentItemRequest) {
        return contentItemCrudRequestHandler.create(createContentItemRequest);
    }


    @GetMapping("/get-all-content-items/{from}/{dataSize}")
    public ContentManagerCrudResponse getAll(@PathVariable Optional<Integer> from, @PathVariable Optional<Integer> dataSize) {
        Integer errorCode = 400;
        Error error = errorsContainer.getErrors().get(errorCode);
        if (!from.isPresent()){
            return new ContentManagerCrudResponse(errorCode, error.getMessage() + " - from");

        }
        if (!dataSize.isPresent()){
            return new ContentManagerCrudResponse(errorCode, error.getMessage() + " - dataSize");

        }
        return contentItemCrudRequestHandler.getAll(from.get(), dataSize.get());
    }
}
