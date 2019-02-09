package com.taboola.contentmanager.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteContentItemRequest {
    @NotNull
    private String itemId;
}
