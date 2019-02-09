package com.taboola.contentmanager.services.dal;

import com.taboola.contentmanager.dal.Brand;
import com.taboola.contentmanager.dal.Country;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BrandsRepo extends MongoRepository<Brand, String> {
    Optional<Brand> findByName(String name);
//    Optional<Country> findByCode(String code);
//    List<Country> findByCodeIn(List<String> codes);
}
