package com.taboola.contentmanager.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateContentItemRequest {
    @NotNull
    private String country;
    @NotNull
    private String brand;
    @NotNull
    private String title;
    @NotNull
    private String img;
}
