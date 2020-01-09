package com.tema.sda.Tema_SDA_3;

import com.tema.sda.Tema_SDA_3.business.service.BookService;
import com.tema.sda.Tema_SDA_3.data.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TemaSda3Application.class}, properties = {"server.port=8080"})
@AutoConfigureMockMvc
@ComponentScan("gresanu.emanuel.vasile.Junit_5_Spring_MVC.*")
public class TestBookController {

    @MockBean
    private BookService service;

    @Autowired
    private MockMvc mockMvc;

    Book book = new Book();
    Book book2 = new Book();
    Book book3 = new Book();
    Book book4 = new Book();

    public TestBookController() {
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
    @DisplayName("GET /book - return all 4 books with succes")
    public void givenGetRequestForAllBooks_ThenGetAListOfForElements() throws Exception {
        //Given
        doReturn(Arrays.asList(book, book2, book3, book4)).when(service).findAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/book"))
                //Validate response code and status
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    @DisplayName("GET /book/Cartea 1 - return book with success")
    public void givenGetRequestForOneBook_ThenGetOnlyOneBook() throws Exception {
        //Given
        doReturn(Optional.of(book)).when(service).findByTitle(book.getTitle());
        mockMvc.perform(MockMvcRequestBuilders.get("/book/{bookTitle}", book.getTitle()))
                //Validate response code and status
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                //validate headers
                .andExpect(header().string(HttpHeaders.ETAG, "\"0\""))
                .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/book/Cartea%201"))
                //Validate the returned fields
                .andExpect((ResultMatcher) jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.totalNumberOfPages").value(book.getTotalNumberOfPages()))
                .andExpect(jsonPath("$.borrow").value(book.isBorrow()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()));
    }

}
