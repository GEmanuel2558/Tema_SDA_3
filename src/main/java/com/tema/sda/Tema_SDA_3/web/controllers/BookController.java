package com.tema.sda.Tema_SDA_3.web.controllers;

import com.tema.sda.Tema_SDA_3.business.facade.BookFacade;
import com.tema.sda.Tema_SDA_3.data.entity.Book;
import com.tema.sda.Tema_SDA_3.web.dto.ResponseBookDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class BookController {

    private static final Logger logger = LogManager.getLogger(BookController.class);

    private final BookFacade facade;
    private final ModelMapper mapper;

    public BookController(BookFacade facade, ModelMapper mapper) {
        this.facade = facade;
        this.mapper = mapper;
    }

    @GetMapping("/")
    @ResponseBody
    public List<ResponseBookDTO> getAllBooks() {
        logger.info("Get the books");
        return ((List<Book>) this.facade.findAll()).stream()
                .map(book -> mapper.map(book, ResponseBookDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public List<ResponseBookDTO> getBooksByIdOrAll(@PathVariable(required = false) Long id) {
        logger.info("Get the product with the id " + id);
        return ((List<Book>) this.facade.findAll()).stream()
                .map(book -> mapper.map(book, ResponseBookDTO.class))
                .collect(Collectors.toList());
    }

}
