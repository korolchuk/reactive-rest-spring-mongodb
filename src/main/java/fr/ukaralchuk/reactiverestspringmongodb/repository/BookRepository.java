package fr.ukaralchuk.reactiverestspringmongodb.repository;

import fr.ukaralchuk.reactiverestspringmongodb.model.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Mono<Book> findBookByTitle(String title);
    Flux<Book> findBooksByAuthor(String author);
}
