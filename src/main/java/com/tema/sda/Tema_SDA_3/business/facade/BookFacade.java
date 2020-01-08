package com.tema.sda.Tema_SDA_3.business.facade;

import com.tema.sda.Tema_SDA_3.data.entity.Book;

public interface BookFacade {

    Iterable<Book> findAll();

}
