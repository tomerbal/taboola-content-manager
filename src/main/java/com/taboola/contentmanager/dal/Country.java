package com.taboola.contentmanager.dal;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "countries")
public class Country {
    private String name;
    private String code;
}
