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
import java.util.Optional;
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

    @PutMapping(value = "/{bookTitle}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProduct(@RequestBody final ResponseBookDTO theBookToUpdateWith,
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


/*        Optional<Product> productOptional = this.productService.findById(id);
        return productOptional.map(product -> {
            if (!product.getVersion().equals(ifMatch)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            product.setLastName(theUpdatedProduct.getLastName());
            product.setFirstName(theUpdatedProduct.getFirstName());

            if (this.productService.updateTheProduct(product)) {
                try {
                    return ResponseEntity
                            .ok()
                            .location(new URI("/product/" + product.getId()))
                            .eTag(product.getVersion().toString())
                            .body(product);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        }).orElseGet(() -> ResponseEntity.notFound().build());*/
    }

}
