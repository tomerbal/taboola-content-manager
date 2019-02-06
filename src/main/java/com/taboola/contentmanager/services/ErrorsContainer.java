package com.taboola.contentmanager.services;

import com.taboola.contentmanager.dal.Error;
import com.taboola.contentmanager.services.dal.ErrorsRepo;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ErrorsContainer {

    private final ErrorsRepo errorsRepo;

    @Getter
    private final Map<Integer, Error> errors = new HashMap<>();

    @Autowired
    public ErrorsContainer(ErrorsRepo errorsRepo) {
        this.errorsRepo = errorsRepo;
    }

    @PostConstruct
    public void fillErrors(){
        List<Error> allErrors = errorsRepo.findAll();
        for (Error error : allErrors) {
            errors.put(error.getCode(), error);
        }
    }
}
