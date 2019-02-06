package com.taboola.contentmanager.services.dal;

import com.taboola.contentmanager.dal.ContentItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContentItemRepo extends MongoRepository<ContentItem, String> {
   // Page<ContentItem> findAll(String firstname, Pageable pageable);

}
