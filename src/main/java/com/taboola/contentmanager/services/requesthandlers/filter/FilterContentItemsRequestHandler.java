package com.taboola.contentmanager.services.requesthandlers.filter;

import com.taboola.contentmanager.models.contracts.ContentManagerCrudResponse;
import com.taboola.contentmanager.models.contracts.FilterContentItemsRequest;

public interface FilterContentItemsRequestHandler {
    ContentManagerCrudResponse filter(FilterContentItemsRequest filterContentItemsRequest);
}
