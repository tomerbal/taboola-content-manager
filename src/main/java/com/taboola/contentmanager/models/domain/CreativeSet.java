package com.taboola.contentmanager.models.domain;

import lombok.Data;

import java.util.Objects;

@Data
public class CreativeSet {
    private final String title;
    private final String img;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreativeSet)) return false;
        CreativeSet that = (CreativeSet) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(img, that.img);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, img);
    }
}
