package com.taboola.contentmanager.services.requesthandlers.contentitem;

import com.taboola.contentmanager.models.contracts.ContentManagerCrudResponse;
import com.taboola.contentmanager.models.contracts.CreateContentItemRequest;
import com.taboola.contentmanager.models.contracts.DeleteContentItemRequest;
import com.taboola.contentmanager.models.contracts.EditContentItemRequest;

public interface ContentItemCrudRequestHandler {
    ContentManagerCrudResponse create(CreateContentItemRequest createContentItemRequest);
    ContentManagerCrudResponse getAll(Integer from, Integer dataSize);
    ContentManagerCrudResponse delete(DeleteContentItemRequest deleteContentItemRequest);
    ContentManagerCrudResponse edit(EditContentItemRequest editContentItemRequest);
}
