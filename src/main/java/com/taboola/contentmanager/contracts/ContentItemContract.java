package com.taboola.contentmanager.contracts;

import lombok.Data;

@Data
public class ContentItemContract {

    private final String id;
    private final String countryName;
    private final String brandName;
    private final String title;
    private final String img;
}
