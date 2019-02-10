package com.taboola.contentmanager.controllers;

import com.taboola.contentmanager.models.contracts.ContentManagerCrudResponse;
import com.taboola.contentmanager.models.contracts.FilterContentItemsRequest;
import com.taboola.contentmanager.services.requesthandlers.filter.FilterContentItemsRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/content-item")
public class ContentItemFilterController {

    private final FilterContentItemsRequestHandler filterContentItemsRequestHandler;

    @Autowired
    public ContentItemFilterController(FilterContentItemsRequestHandler filterContentItemsRequestHandler) {
        this.filterContentItemsRequestHandler = filterContentItemsRequestHandler;
    }

    @PostMapping(value = "/filter")
    public ContentManagerCrudResponse test(@RequestBody @Valid FilterContentItemsRequest filterContentItemsRequest) {
        return filterContentItemsRequestHandler.filter(filterContentItemsRequest);
    }

}
