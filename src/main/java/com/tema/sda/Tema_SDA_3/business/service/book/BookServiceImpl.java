package com.tema.sda.Tema_SDA_3.business.service.book;

import com.tema.sda.Tema_SDA_3.data.entity.Book;
import com.tema.sda.Tema_SDA_3.data.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public Iterable<Book> findAll(Pageable pageProperties) {
        return this.repository.findAll(pageProperties).toList();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public Optional<Book> findByTitle(@NotNull @NotEmpty final String bookTitle) {
        return this.repository.findAllByTitle(bookTitle);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public Optional<Book> findAllByTitleAndAuthorAndVolum(final String title, final String author, final int volum) {
        return this.repository.findAllByTitleAndAuthorAndVolum(title, author, volum);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public Optional<List<Book>> getAllBooksSortedByTotalNumberOfPages() {
        return this.repository.customGetAllBooksSortedByTotalNumberOfPages();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public Optional<List<Book>> findAllBooksThatAreBorrowed(final Boolean isBorrow) {
        return this.repository.customGetAllBooksThatAreBorrowed(isBorrow);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public Optional<List<Book>> findAllByVolum(int volum) {
        return this.repository.findAllByVolum(volum);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public Optional<List<Book>> getAllBooksBorrowedTo(final String borrowedTo) {
        return this.repository.customGetAllBooksBorrowedTo(borrowedTo);
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

    @Override
    public boolean deleteBook(String title) {
        return this.repository.findAllByTitle(title).map(currentBook -> {
            this.repository.delete(currentBook);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean deleteBookByTitleAndAuthorAndVolum(String title, String author, int volum) {
        return 0 != this.repository.deleteBookByTitleAndAuthorAndVolum(title, author, volum);
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
