package com.taboola.contentmanager.models.contracts;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EditContentItemRequest {
    @NotNull
    private String itemId;
    @NotNull
    private String country;
    @NotNull
    private String brand;
    @NotNull
    private String title;
    @NotNull
    private String img;
}
