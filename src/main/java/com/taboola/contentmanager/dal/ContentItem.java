package com.taboola.contentmanager.dal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "content")
public class ContentItem {
    private String countryCode;
    private String brand;
    private String title;
    private String img;
}
