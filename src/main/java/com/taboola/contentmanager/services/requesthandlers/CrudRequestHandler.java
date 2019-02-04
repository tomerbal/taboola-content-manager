package com.taboola.contentmanager.services.requesthandlers;

import com.taboola.contentmanager.models.ContentManagerCrudResponse;
import com.taboola.contentmanager.models.CreateContentItemRequest;

public interface CrudRequestHandler {
    ContentManagerCrudResponse create(CreateContentItemRequest createContentItemRequest);
}
