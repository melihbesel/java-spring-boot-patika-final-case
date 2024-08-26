package com.patika.searchservice.model.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Document(indexName = "logs")
public class LogDocument {

    @Id
    private String _id;

    @Field(type = FieldType.Text)
    private String message;
    @Field(type = FieldType.Text)
    private String createdDateTime;

}
