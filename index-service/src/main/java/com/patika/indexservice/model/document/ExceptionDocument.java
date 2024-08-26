package com.patika.indexservice.model.document;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Document(indexName = "exceptions")
public class ExceptionDocument {

    @Field(type = FieldType.Text)
    private String message;
    @Field(type = FieldType.Text)
    private String createdDateTime;

}
