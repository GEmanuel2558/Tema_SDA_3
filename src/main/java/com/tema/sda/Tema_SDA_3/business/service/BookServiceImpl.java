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

}
