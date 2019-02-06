package com.taboola.contentmanager.models;

import com.taboola.contentmanager.dal.ContentItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetAllContentItemsResponse extends ContentManagerCrudResponse{
    private final List<ContentItem> contentItems;
    private final Long outOf;

    public GetAllContentItemsResponse(Integer status, String message, List<ContentItem> contentItems, Long outOf) {
        super(status, message);
        this.contentItems = contentItems;
        this.outOf = outOf;
    }
}
