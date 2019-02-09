package com.taboola.contentmanager.dal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "content")
public class ContentItem {

    @Id
    private String _id;
    private String countryId;
    private String brandId;
    private String title;
    private String img;


    public ContentItem(String countryId, String brandId, String title, String img) {
        this.countryId = countryId;
        this.brandId = brandId;
        this.title = title;
        this.img = img;
    }
}
