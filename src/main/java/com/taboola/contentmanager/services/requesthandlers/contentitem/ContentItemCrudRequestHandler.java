package com.taboola.contentmanager.services.requesthandlers.contentitem;

import com.taboola.contentmanager.models.ContentManagerCrudResponse;
import com.taboola.contentmanager.models.CreateContentItemRequest;
import com.taboola.contentmanager.models.DeleteContentItemRequest;

public interface ContentItemCrudRequestHandler {
    ContentManagerCrudResponse create(CreateContentItemRequest createContentItemRequest);
    ContentManagerCrudResponse getAll(Integer from, Integer dataSize);
    ContentManagerCrudResponse delete(DeleteContentItemRequest deleteContentItemRequest);
}
