package com.taboola.contentmanager.services.dal;


import com.taboola.contentmanager.dal.Error;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ErrorsRepo extends MongoRepository<Error, String> {
    Optional<Error> getByCode(Integer code);
}
