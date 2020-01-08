package com.tema.sda.Tema_SDA_3.business.service;

import com.tema.sda.Tema_SDA_3.data.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface BookService {

    Iterable<Book> findAll();

    Page<Book> findAll(@NotNull Pageable pageRequest);

    Optional<Book> findByTitle(@NotNull @NotEmpty final String bookTitle);

    Book saveNewBook(final Book theNewBook);

    boolean updateTheBook(final Book theNewBook);

    boolean deleteBook(final String title);

}
