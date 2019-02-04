package com.taboola.contentmanager.services.dal;

import com.taboola.contentmanager.dal.ContentItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContentItemRepo extends MongoRepository<ContentItem, String> {
}
