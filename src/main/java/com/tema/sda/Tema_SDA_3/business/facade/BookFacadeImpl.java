package com.tema.sda.Tema_SDA_3.business.facade;

import com.tema.sda.Tema_SDA_3.business.service.book.BookService;
import com.tema.sda.Tema_SDA_3.data.entity.Book;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.vavr.control.Try;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
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
    public Optional<Book> findAllByTitleAndAuthorAndVolum(final String title, final String author, final int volum) {
        Try<Optional<Book>> result = Try.ofSupplier(circuitBreaker.decorateSupplier(() -> this.service.findAllByTitleAndAuthorAndVolum(title, author, volum)));
        if (result.isFailure()) {
            logger.error("We have a problem with the book service. I can't call the findAllByTitleAndAuthorAndVolum function!");
        }
        return result.get();
    }

    @Override
    public Optional<List<Book>> getAllBooksSortedByTotalNumberOfPages() {
        Try<Optional<List<Book>>> result = Try.ofSupplier(circuitBreaker.decorateSupplier(this.service::getAllBooksSortedByTotalNumberOfPages));
        if (result.isFailure()) {
            logger.error("We have a problem with the book service. I can't call the getAllBooksSortedByTotalNumberOfPages function!");
        }
        return result.get();
    }

    @Override
    public Optional<List<Book>> findAllBooksThatAreBorrowed(final Boolean isBorrow) {
        Try<Optional<List<Book>>> result = Try.ofSupplier(circuitBreaker.decorateSupplier(() -> this.service.findAllBooksThatAreBorrowed(isBorrow)));
        if (result.isFailure()) {
            logger.error("We have a problem with the book service. I can't call the findAllByBorrow function!");
        }
        return result.get();
    }

    @Override
    public Optional<List<Book>> findAllByVolum(int volum) {
        Try<Optional<List<Book>>> result = Try.ofSupplier(circuitBreaker.decorateSupplier(() -> this.service.findAllByVolum(volum)));
        if (result.isFailure()) {
            logger.error("We have a problem with the book service. I can't call the findAllByVolum function!");
        }
        return result.get();
    }

    @Override
    public Optional<List<Book>> getAllBooksBorrowedTo(final String borrowedTo) {
        Try<Optional<List<Book>>> result = Try.ofSupplier(circuitBreaker.decorateSupplier(() -> this.service.getAllBooksBorrowedTo(borrowedTo)));
        if (result.isFailure()) {
            logger.error("We have a problem with the book service. I can't call the getAllBooksBorrowedTo function!");
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

    @Override
    public boolean deleteBook(final String title) {
        Try<Boolean> result = Try.ofSupplier(circuitBreaker.decorateSupplier(() -> this.service.deleteBook(title)));
        if (result.isFailure()) {
            logger.error("We have a problem with the book service. I can't call the update function!");
        }
        return result.get();
    }


    @Override
    public boolean deleteBookByTitleAndAuthorAndVolum(String title, String author, int volum) {
        Try<Boolean> result = Try.ofSupplier(circuitBreaker.decorateSupplier(() -> this.service.deleteBookByTitleAndAuthorAndVolum(title, author, volum)));
        if (result.isFailure()) {
            logger.error("We have a problem with the book service. I can't call the deleteBookByTitleAndAuthorAndVolum function!");
        }
        return result.get();
    }
}
