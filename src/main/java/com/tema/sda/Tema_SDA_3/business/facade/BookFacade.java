package com.tema.sda.Tema_SDA_3.business.facade;

import com.tema.sda.Tema_SDA_3.data.entity.Book;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface BookFacade {

    Iterable<Book> findAll(@NotNull Pageable pageProperties);

    Optional<Book> findByTitle(@NotNull @NotEmpty String bookTitle);

    Optional<Book> findAllByTitleAndAuthorAndVolum(final String title, final String author, final int volum);

    Optional<List<Book>> getAllBooksSortedByTotalNumberOfPages();

    Book saveNewBook(final Book theNewBook);

    boolean updateTheBook(final Book theNewBook);

    boolean deleteBook(final String title);

    boolean deleteBookByTitleAndAuthorAndVolum(String title, String author, int volum);

    Optional<List<Book>> findAllBooksThatAreBorrowed(final Boolean isBorrow);

    Optional<List<Book>> findAllByVolum(int volum);

    Optional<List<Book>> getAllBooksBorrowedTo(final String borrowedTo);

}
