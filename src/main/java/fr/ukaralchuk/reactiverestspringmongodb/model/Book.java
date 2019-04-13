package fr.ukaralchuk.reactiverestspringmongodb.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;


@Document(collection = "book")
@Data
public class Book {
    @Id
    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String yearPublication;


}
