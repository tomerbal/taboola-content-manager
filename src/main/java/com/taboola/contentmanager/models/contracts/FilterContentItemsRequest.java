package com.taboola.contentmanager.models.contracts;

import com.taboola.contentmanager.dal.Brand;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FilterContentItemsRequest {
    private List<String> countries = new ArrayList<>();
    private List<String> brands = new ArrayList<>();
    private boolean superBrand;
    private int from;
    private int dataSize;
}
