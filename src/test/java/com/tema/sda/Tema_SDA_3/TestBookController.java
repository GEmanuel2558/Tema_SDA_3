package com.tema.sda.Tema_SDA_3;

import com.tema.sda.Tema_SDA_3.business.service.book.BookService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static com.tema.sda.Tema_SDA_3.JsonCastHelper.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TemaSda3Application.class}, properties = {"server.port=8080"})
@AutoConfigureMockMvc
@ComponentScan("gresanu.emanuel.vasile.Junit_5_Spring_MVC.*")
public class TestBookController {

    @MockBean
    private BookService service;

    @Autowired
    private MockMvc mockMvc;

    private Book book;
    private Book book2;
    private Book book3;
    private Book book4;

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
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.totalNumberOfPages").value(book.getTotalNumberOfPages()))
                .andExpect(jsonPath("$.borrow").value(book.isBorrow()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()));
    }

    @Test
    @DisplayName("POST /book - return the saved book with success")
    public void givenPostRequestForWithOneBook_ThenGetSuccessfulStatus() throws Exception {
        Book newBookToSave = new Book();
        newBookToSave.setAuthor("Valeria");
        newBookToSave.setBorrow(true);
        newBookToSave.setBorrowedTo("Vasile");
        newBookToSave.setSection("TEHNIC");
        newBookToSave.setTitle("Cartea 5");
        newBookToSave.setTotalNumberOfPages(50);
        newBookToSave.setVolum(1);
        doReturn(newBookToSave).when(service).saveNewBook(any(Book.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/book")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(newBookToSave)))
                //Validate response code and status
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                //validate headers
                .andExpect(header().string(HttpHeaders.ETAG, "\"0\""))
                .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/book/Cartea%205"))
                //Validate the returned fields
                .andExpect(jsonPath("$.title").value(newBookToSave.getTitle()))
                .andExpect(jsonPath("$.totalNumberOfPages").value(newBookToSave.getTotalNumberOfPages()))
                .andExpect(jsonPath("$.borrow").value(newBookToSave.isBorrow()))
                .andExpect(jsonPath("$.author").value(newBookToSave.getAuthor()));

    }

    @Test
    @DisplayName("PUT /book/Carte 1 - Update with success")
    public void givenPutRequestForWithOneBook_ThenGetSuccessfulStatus() throws Exception {
        Book bookToUpdate = new Book();
        bookToUpdate.setAuthor("Valeria");
        bookToUpdate.setBorrow(true);
        bookToUpdate.setBorrowedTo("Vasile");
        bookToUpdate.setSection("TEHNIC");
        bookToUpdate.setTitle("Cartea 5");
        bookToUpdate.setTotalNumberOfPages(50);
        bookToUpdate.setVolum(1);

        doReturn(Optional.of(book)).when(service).findByTitle(book.getTitle());
        doReturn(true).when(service).updateTheBook(any(Book.class));

        mockMvc.perform(MockMvcRequestBuilders.put("/book/{bookTitle}", book.getTitle())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.IF_MATCH, 0)
                .content(asJsonString(bookToUpdate)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header().string(HttpHeaders.ETAG, "\"0\""))
                .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/book/Cartea%205"))
                .andExpect(jsonPath("$.title").value(bookToUpdate.getTitle()))
                .andExpect(jsonPath("$.totalNumberOfPages").value(bookToUpdate.getTotalNumberOfPages()))
                .andExpect(jsonPath("$.borrow").value(bookToUpdate.isBorrow()))
                .andExpect(jsonPath("$.author").value(bookToUpdate.getAuthor()));
    }

    @Test
    @DisplayName("PUT /book/Carte 1 - Not Found")
    public void givenPutRequestForWithOneBook_ThenGetFailedStatus() throws Exception {
        Book bookToUpdate = new Book();
        bookToUpdate.setAuthor("Valeria");
        bookToUpdate.setBorrow(true);
        bookToUpdate.setBorrowedTo("Vasile");
        bookToUpdate.setSection("TEHNIC");
        bookToUpdate.setTitle("Cartea 5");
        bookToUpdate.setTotalNumberOfPages(50);
        bookToUpdate.setVolum(1);

        doReturn(Optional.empty()).when(service).findByTitle(book.getTitle());

        mockMvc.perform(MockMvcRequestBuilders.put("/book/{bookTitle}", 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.IF_MATCH, 0)
                .content(asJsonString(bookToUpdate)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /book/Carte 1 - Success")
    public void givenDeleteRequestForWithOneBook_ThenGetSuccessfulStatus() throws Exception {
        doReturn(Optional.of(book)).when(service).findByTitle(book.getTitle());
        doReturn(true).when(service).deleteBook(any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/book/{bookTitle}", book.getTitle()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /book/Carte 1 - Failed")
    public void givenDeleteRequestForWithOneBook_ThenGetFailedStatus() throws Exception {

        doReturn(Optional.of(book)).when(service).findByTitle(book.getTitle());
        doReturn(false).when(service).deleteBook(any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/book/{bookTitle}", book.getTitle()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("DELETE /book/Carte 1 - Not found")
    public void givenDeleteRequestForWithOneBook_ThenGetNotFoundStatus() throws Exception {
        doReturn(Optional.empty()).when(service).findByTitle(book.getTitle());

        mockMvc.perform(MockMvcRequestBuilders.delete("/book/{bookTitle}", book.getTitle()))
                .andExpect(status().isNotFound());
    }

}
