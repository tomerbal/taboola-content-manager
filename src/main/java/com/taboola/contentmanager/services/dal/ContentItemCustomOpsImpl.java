package com.taboola.contentmanager.services.dal;

import com.taboola.contentmanager.dal.ContentItem;
import com.taboola.contentmanager.models.domain.CreativeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContentItemCustomOpsImpl implements ContentItemCustomOps {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ContentItemCustomOpsImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<ContentItem> getFiltered(List<String> countryIds, List<String> brandIds, boolean superBrand, int from, int dataSize) {
        PageRequest pageRequest = PageRequest.of(from, dataSize);
        Query query = new Query().with(pageRequest);
        if (!brandIds.isEmpty()){
            query.addCriteria(Criteria
                    .where("brandId").in(brandIds));
        }
        if (!countryIds.isEmpty()){
            query.addCriteria(Criteria
                    .where("countryId").in(countryIds));
        }
        List<ContentItem> contentItems = mongoTemplate.find(query, ContentItem.class, "content");
        if (superBrand){
            contentItems = this.getCreativeBrands(contentItems);
        }
        Page<ContentItem> page = PageableExecutionUtils.getPage(
                contentItems,
                pageRequest,
                () -> mongoTemplate.count(query, ContentItem.class));
        return page;
    }

    private List<ContentItem> getCreativeBrands(List<ContentItem> contentItems){
        Map<String, Set<CreativeSet>> helperMap = new HashMap<>();
        for (ContentItem contentItem : contentItems) {
            String brandId = contentItem.getBrandId();
            helperMap.putIfAbsent(brandId, new HashSet<>());
            String img = contentItem.getImg();
            String title = contentItem.getTitle();
            helperMap.get(brandId).add(new CreativeSet(title, img));
        }

        List<String> superBrands = helperMap.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<ContentItem> output = new ArrayList<>();

        for (ContentItem contentItem : contentItems) {
            String brandId = contentItem.getBrandId();
            if (superBrands.contains(brandId)){
                output.add(contentItem);
            }
        }
        return output;

    }
}
