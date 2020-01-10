package com.tema.sda.Tema_SDA_3.web;

import com.tema.sda.Tema_SDA_3.web.dto.BookDTO;

import java.io.Serializable;

public class BookWrapper implements Serializable {

    public int etag;

    public BookDTO dto;

    public BookWrapper(int etag, BookDTO dto) {
        this.etag = etag;
        this.dto = dto;
    }
}
