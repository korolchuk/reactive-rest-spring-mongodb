package fr.ukaralchuk.reactiverestspringmongodb.controller;


import fr.ukaralchuk.reactiverestspringmongodb.model.Book;
import fr.ukaralchuk.reactiverestspringmongodb.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("books")
    public Flux<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping(value = "books/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Book> getAllBooksStream() {
        return bookRepository.findAll();
    }

    @GetMapping("books/{id}")
    public Mono<ResponseEntity<Book>> getBookById(@PathVariable("id") String id) {
        return bookRepository.findById(id).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("books/title/{title}")
    public Mono<ResponseEntity<Book>> getBookByTitle(@PathVariable("title") String title) {
        return bookRepository.findBookByTitle(title).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("books/author/{author}")
    public Flux<Book> getBooksByAuthor(@PathVariable("author") String author) {
        return bookRepository.findBooksByAuthor(author);
    }

    @PostMapping("books")
    public Mono<ResponseEntity<Book>> createBook(@Valid @RequestBody Book book) {
        return bookRepository.save(book).map(newBook -> new ResponseEntity<>(newBook, HttpStatus.CREATED));
    }

    @PutMapping("books/{id}")
    public Mono<ResponseEntity<Book>> updateBook(@PathVariable("id") String id, @Valid @RequestBody Book book) {
        return bookRepository.findById(id).flatMap(_book -> {
            _book.setTitle(book.getTitle());
            _book.setAuthor(book.getAuthor());
            _book.setYearPublication(book.getYearPublication());
            return bookRepository.save(_book);
        }).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @DeleteMapping("books/{id}")
    public Mono<ResponseEntity<Book>> deleteBook(@PathVariable("id") String id) {
        return bookRepository.findById(id).flatMap(book -> bookRepository.deleteById(id)
                .then(Mono.just(new ResponseEntity<>(book, HttpStatus.OK))))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
