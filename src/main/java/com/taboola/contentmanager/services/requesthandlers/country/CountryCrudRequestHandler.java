package com.taboola.contentmanager.services.requesthandlers.country;

import com.taboola.contentmanager.models.ContentManagerCrudResponse;
import com.taboola.contentmanager.models.CreateContentItemRequest;

public interface CountryCrudRequestHandler {
    ContentManagerCrudResponse getAll();
}
