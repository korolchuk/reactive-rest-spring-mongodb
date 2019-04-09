package fr.ukaralchuk.reactiverestspringmongodb.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

import java.util.Date;

@Document(collection = "books")
@Data
public class Book {
    @Id
    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private Date createdBook = new Date();

}
