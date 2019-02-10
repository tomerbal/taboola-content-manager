package com.taboola.contentmanager.services.requesthandlers.country;

import com.taboola.contentmanager.models.contracts.ContentManagerCrudResponse;

public interface CountryCrudRequestHandler {
    ContentManagerCrudResponse getAll();
}
