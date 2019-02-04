package com.taboola.contentmanager.dal;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "errors")
public class Error {
    private Integer code;
    private String message;
}
