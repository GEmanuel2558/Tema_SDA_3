package com.tema.sda.Tema_SDA_3;

import com.tema.sda.Tema_SDA_3.business.service.book.BookService;
import com.tema.sda.Tema_SDA_3.data.entity.Book;
import com.tema.sda.Tema_SDA_3.data.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Iterator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@ComponentScan("com.tema.sda.Tema_SDA_3.*")
@SpringBootTest
@AutoConfigureMockMvc
public class BookServiceTest {

    @Autowired
    private BookService service;

    @MockBean
    private BookRepository repository;

    private Book book;
    private Book book2;
    private Book book3;
    private Book book4;

    public BookServiceTest() {
        book = new Book();
        book.setAuthor("Emanuel");
        book.setBorrow(false);
        book.setSection("SF");
        book.setTitle("Cartea 1");
        book.setTotalNumberOfPages(250);
        book.setVolum(1);

        book2 = new Book();
        book2.setAuthor("Mihai");
        book2.setBorrow(false);
        book2.setSection("DRAMA");
        book2.setTitle("Cartea 2");
        book2.setTotalNumberOfPages(100);
        book2.setVolum(1);

        book3 = new Book();
        book3.setAuthor("Oana");
        book3.setBorrow(true);
        book3.setBorrowedTo("Mihaela");
        book3.setSection("POLITIST");
        book3.setTitle("Cartea 3");
        book3.setTotalNumberOfPages(182);
        book3.setVolum(1);

        book4 = new Book();
        book4.setAuthor("Ioana");
        book4.setBorrow(true);
        book4.setBorrowedTo("Victor");
        book4.setSection("ROMANTIC");
        book4.setTitle("Cartea 4");
        book4.setTotalNumberOfPages(231);
        book4.setVolum(2);
    }

    @Test
    @DisplayName("Test find all")
    public void givenPageNumberAndSize_thenGetBookElements() {
        final int pageSize = 4;
        doReturn(new PageImpl<>(Arrays.asList(book, book2, book3, book4))).when(repository).findAll(any(Pageable.class));

        Iterator<Book> books = this.service.findAll(PageRequest.of(0, pageSize)).iterator();
        int counter = 0;
        while (books.hasNext()) {
            books.next();
            ++counter;
        }
        Assertions.assertEquals(pageSize, counter, "The total number should be 2");
    }

    @Test
    @DisplayName("Test save new book")
    public void giveNewBook_ThenSaveWithSuccessThisBookIntoDb() {

        Book newBookToSave = new Book();
        newBookToSave.setAuthor("Vasilica");
        newBookToSave.setBorrow(true);
        newBookToSave.setBorrowedTo("Victor");
        newBookToSave.setSection("ROMANTIC");
        newBookToSave.setTitle("Cartea 5");
        newBookToSave.setTotalNumberOfPages(231);
        newBookToSave.setVolum(2);
        newBookToSave.setId(5L);

        doReturn(newBookToSave).when(repository).save(any(Book.class));

        Book savedBook = this.service.saveNewBook(newBookToSave);
        Assertions.assertSame(newBookToSave, savedBook, "The saved book should be the same");
        Assertions.assertNotNull(savedBook.getId());
    }

}
