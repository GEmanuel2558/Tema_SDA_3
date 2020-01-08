package com.tema.sda.Tema_SDA_3.business.service;

import com.tema.sda.Tema_SDA_3.data.entity.Book;
import com.tema.sda.Tema_SDA_3.data.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<Book> findAll() {
        return this.repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Book> findAll(@NotNull Pageable pageRequest) {
        return repository.findAll(pageRequest);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> findByTitle(@NotNull @NotEmpty final String bookTitle) {
        return this.repository.findAllByTitle(bookTitle);
    }

    @Override
    public Book saveNewBook(final Book theNewBook) {
        return this.repository.save(theNewBook);
    }

    @Override
    public boolean updateTheBook(final Book theNewBook) {
        return this.repository.findAllByTitle(theNewBook.getTitle()).map(currentBook -> {
            staticFillTheBookProperties(theNewBook, currentBook);
            return true;
        }).orElse(false);
    }

    private void staticFillTheBookProperties(Book theNewBook, Book currentBook) {
        currentBook.setBorrowedTo(theNewBook.getBorrowedTo());
        currentBook.setBorrow(theNewBook.isBorrow());
        currentBook.setVolum(theNewBook.getVolum());
        currentBook.setTotalNumberOfPages(theNewBook.getTotalNumberOfPages());
        currentBook.setSection(theNewBook.getSection());
        currentBook.setAuthor(theNewBook.getAuthor());
        currentBook.setTitle(theNewBook.getTitle());
    }
}
