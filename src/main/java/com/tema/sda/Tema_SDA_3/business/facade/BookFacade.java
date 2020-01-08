package com.tema.sda.Tema_SDA_3.business.facade;

import com.tema.sda.Tema_SDA_3.data.entity.Book;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface BookFacade {

    Iterable<Book> findAll();

    Optional<Book> findByTitle(@NotNull @NotEmpty String bookTitle);

    Book saveNewBook(final Book theNewBook);

    boolean updateTheBook(final Book theNewBook);

}
