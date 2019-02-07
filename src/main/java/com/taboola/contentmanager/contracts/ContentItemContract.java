package com.taboola.contentmanager.contracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class ContentItemContract {
    private final String countryName;
    private final String brand;
    private final String title;
    private final String img;
}
