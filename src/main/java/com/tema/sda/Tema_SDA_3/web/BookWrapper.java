package com.tema.sda.Tema_SDA_3.web;

import com.tema.sda.Tema_SDA_3.web.dto.ResponseBookDTO;

import java.io.Serializable;

public class BookWrapper implements Serializable {

    public int etag;

    public ResponseBookDTO dto;

    public BookWrapper(int etag, ResponseBookDTO dto) {
        this.etag = etag;
        this.dto = dto;
    }
}
