package com.tema.sda.Tema_SDA_3.business.facade;

import com.tema.sda.Tema_SDA_3.business.service.BookService;
import com.tema.sda.Tema_SDA_3.data.entity.Book;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.vavr.control.Try;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class BookFacadeImpl implements BookFacade {

    private final CircuitBreaker circuitBreaker;
    private final BookService service;
    private static final Logger logger = LogManager.getLogger(BookFacadeImpl.class);

    public BookFacadeImpl(CircuitBreaker circuitBreaker, BookService service) {
        this.circuitBreaker = circuitBreaker;
        this.service = service;
    }

    @Override
    public Iterable<Book> findAll() {
        Try<Iterable<Book>> result = Try.ofSupplier(circuitBreaker.decorateSupplier(this.service::findAll));
        if (result.isFailure()) {
            logger.error("We have a problem with the book service. I can't call the findAll function!");
        }
        return result.getOrElse(new ArrayList<>());
    }

    @Override
    public Optional<Book> findByTitle(@NotNull @NotEmpty final String bookTitle) {
        Try<Optional<Book>> result = Try.ofSupplier(circuitBreaker.decorateSupplier(() -> this.service.findByTitle(bookTitle)));
        if (result.isFailure()) {
            logger.error("We have a problem with the book service. I can't call the findByTitle function!");
        }
        return result.get();
    }

    @Override
    public Book saveNewBook(final Book theNewBook) {
        Try<Book> result = Try.ofSupplier(circuitBreaker.decorateSupplier(() -> this.service.saveNewBook(theNewBook)));
        if (result.isFailure()) {
            logger.error("We have a problem with the book service. I can't call the save function!");
        }
        return result.get();
    }

    @Override
    public boolean updateTheBook(Book theNewBook) {
        Try<Boolean> result = Try.ofSupplier(circuitBreaker.decorateSupplier(() -> this.service.updateTheBook(theNewBook)));
        if (result.isFailure()) {
            logger.error("We have a problem with the book service. I can't call the update function!");
        }
        return result.get();
    }
}
