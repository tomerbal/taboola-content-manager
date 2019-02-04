package com.taboola.contentmanager.services.dal;

import com.taboola.contentmanager.dal.Country;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CountriesRepo extends MongoRepository<Country, String> {
    Optional<Country> getByCode(String code);
}
