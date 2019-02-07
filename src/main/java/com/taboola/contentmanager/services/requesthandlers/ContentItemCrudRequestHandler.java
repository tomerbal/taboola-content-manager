package com.taboola.contentmanager.services.requesthandlers;

import com.taboola.contentmanager.models.ContentManagerCrudResponse;
import com.taboola.contentmanager.models.CreateContentItemRequest;

public interface ContentItemCrudRequestHandler {
    ContentManagerCrudResponse create(CreateContentItemRequest createContentItemRequest);

    ContentManagerCrudResponse getAll(Integer from, Integer dataSize);
}
