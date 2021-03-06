package com.taboola.contentmanager.models.contracts;

import com.taboola.contentmanager.contracts.ContentItemContract;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetAllContentItemsResponse extends ContentManagerCrudResponse{
    private final List<ContentItemContract> contentItems;
    private final Long outOf;

    public GetAllContentItemsResponse(Integer status, String message, List<ContentItemContract> contentItems, Long outOf) {
        super(status, message);
        this.contentItems = contentItems;
        this.outOf = outOf;
    }
}
