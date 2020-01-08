package com.tema.sda.Tema_SDA_3.web.controllers;

import com.tema.sda.Tema_SDA_3.business.facade.BookFacade;
import com.tema.sda.Tema_SDA_3.data.entity.Book;
import com.tema.sda.Tema_SDA_3.web.dto.ResponseBookDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
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
    public List<ResponseBookDTO> getAllBooks() {
        logger.info("Get the books");
        return ((List<Book>) this.facade.findAll()).stream()
                .map(book -> mapper.map(book, ResponseBookDTO.class))
                .collect(Collectors.toList());
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
    public ResponseEntity<?> createProduct(@RequestBody ResponseBookDTO theNewBook) {
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

}
