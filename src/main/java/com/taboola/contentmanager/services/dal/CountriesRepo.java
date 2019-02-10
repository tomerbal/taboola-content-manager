package com.taboola.contentmanager.services.dal;

import com.taboola.contentmanager.dal.Country;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CountriesRepo extends MongoRepository<Country, String> {
    Optional<Country> findByName(String name);
    List<Country> findBy_idIn(List<String> ids);
    List<Country> findByNameIn(List<String> countryNames);
}
