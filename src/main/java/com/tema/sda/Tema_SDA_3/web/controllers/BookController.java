package com.tema.sda.Tema_SDA_3.web.controllers;

import com.tema.sda.Tema_SDA_3.business.facade.BookFacade;
import com.tema.sda.Tema_SDA_3.data.entity.Book;
import com.tema.sda.Tema_SDA_3.web.dto.ResponseBookDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
//@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class BookController {

    private static final Logger logger = LogManager.getLogger(BookController.class);

    private final BookFacade facade;
    private final ModelMapper mapper;

    public BookController(BookFacade facade, ModelMapper mapper) {
        this.facade = facade;
        this.mapper = mapper;
    }

    @GetMapping
    @ResponseBody
    public List<ResponseBookDTO> getAllBooks(@RequestParam(required = false, name = "sorted") Boolean sorted) {
        if (null == sorted || !sorted) {
            logger.info("Get all the books");
            return ((List<Book>) this.facade.findAll()).stream()
                    .map(book -> mapper.map(book, ResponseBookDTO.class))
                    .collect(toList());
        } else {
            logger.info("Get all the books but sort them by the number of pages");
            return this.facade.getAllBooksSortedByTotalNumberOfPages()
                    .map(books -> books
                            .stream()
                            .map(book -> mapper.map(book, ResponseBookDTO.class))
                            .collect(Collectors.toList()))
                    .orElse(new ArrayList<>());
        }
    }

    @GetMapping("/borrow")
    @ResponseBody
    public List<ResponseBookDTO> findAllBooksThatAreBorrowed(@RequestParam(required = false, name = "isBorrow")
                                                                     Boolean isBorrow) {
        logger.info("Get all the books that are borrowed " + isBorrow);
        if (null == isBorrow) {
            isBorrow = false;
        }
        return this.facade.findAllBooksThatAreBorrowed(isBorrow)
                .map(books -> books
                        .stream()
                        .map(book -> mapper.map(book, ResponseBookDTO.class))
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>());
    }

    @GetMapping("/{bookTitle}")
    @ResponseBody
    public ResponseEntity<?> getBooksByIdOrAll(@PathVariable String bookTitle) {
        logger.info("Get the book with the title " + bookTitle);
        return this.facade.findByTitle(bookTitle).map(book -> {
            try {
                ResponseBookDTO theBody = mapper.map(book, ResponseBookDTO.class);
                final Link link = WebMvcLinkBuilder.linkTo(BookController.class).slash(book.getTitle()).withSelfRel();
                return ResponseEntity.ok().eTag("" + book.getVersion()).location(new URI(link.getHref())).body(theBody);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBook(@RequestBody ResponseBookDTO theNewBook) {
        logger.info("I will insert a new book: " + theNewBook);
        Book savedBook = this.facade.saveNewBook(mapper.map(theNewBook, Book.class));
        try {
            ResponseBookDTO theBodyToReturnBack = mapper.map(savedBook, ResponseBookDTO.class);
            final Link link = WebMvcLinkBuilder.linkTo(BookController.class).slash(savedBook.getTitle()).withSelfRel();
            return ResponseEntity.created(new URI(link.getHref())).eTag("" + savedBook.getVersion()).body(theBodyToReturnBack);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(value = "/{bookTitle}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateBook(@RequestBody final ResponseBookDTO theBookToUpdateWith,
                                        @PathVariable(name = "bookTitle") String title,
                                        @RequestHeader("If-Match") Integer ifMatch) {
        logger.info("Update the book with the title " + title + " with the new information " + theBookToUpdateWith);

        Optional<Book> bookOptional = this.facade.findByTitle(title);
        return bookOptional.map(book -> {
            //No need to cast the ResponseBookDTO to Book because I will use the book that the DB is returning back
            if (book.getVersion() != ifMatch) {
                logger.error("Somebody este had updated this product before this user had the change to do anything");
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            book.setBorrowedTo(theBookToUpdateWith.getBorrowedTo());
            book.setBorrow(theBookToUpdateWith.isBorrow());
            book.setVolum(theBookToUpdateWith.getVolum());
            book.setTotalNumberOfPages(theBookToUpdateWith.getTotalNumberOfPages());
            book.setSection(theBookToUpdateWith.getSection());
            book.setAuthor(theBookToUpdateWith.getAuthor());
            book.setTitle(theBookToUpdateWith.getTitle());

            if (this.facade.updateTheBook(book)) {
                ResponseBookDTO theBodyToReturnBack = mapper.map(book, ResponseBookDTO.class);
                final Link link = WebMvcLinkBuilder.linkTo(BookController.class).slash(book.getTitle()).withSelfRel();
                try {
                    return ResponseEntity.created(new URI(link.getHref())).eTag("" + book.getVersion()).body(theBodyToReturnBack);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{bookTitle}")
    public ResponseEntity<?> deleteTheBook(@PathVariable(name = "bookTitle") String title) {
        Optional<Book> bookOptional = this.facade.findByTitle(title);
        return bookOptional.map(book -> {
            if (this.facade.deleteBook(book.getTitle())) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTheProduct(@RequestParam(name = "bookTitle") String title,
                                              @RequestParam(name = "bookAuthor") String author,
                                              @RequestParam(name = "bookVolume") Integer volume) {
        Optional<Book> bookOptional = this.facade.findAllByTitleAndAuthorAndVolum(title, author, volume);
        return bookOptional.map(book -> {
            if (this.facade.deleteBookByTitleAndAuthorAndVolum(title, author, volume)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
