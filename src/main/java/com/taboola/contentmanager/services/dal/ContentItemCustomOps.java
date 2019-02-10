package com.taboola.contentmanager.services.dal;

import com.taboola.contentmanager.dal.ContentItem;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContentItemCustomOps {
    Page<ContentItem> getFiltered(List<String> countryIds, List<String> brandIds, boolean superBrand, int from, int dataSize);
}
