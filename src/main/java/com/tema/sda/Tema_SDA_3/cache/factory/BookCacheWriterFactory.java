package com.tema.sda.Tema_SDA_3.cache.factory;

import com.tema.sda.Tema_SDA_3.business.service.global.ApplicationContextSingleton;
import com.tema.sda.Tema_SDA_3.cache.io.BookCacheWriter;

import javax.cache.configuration.Factory;

public class BookCacheWriterFactory implements Factory<BookCacheWriter> {
    @Override
    public BookCacheWriter create() {
        return ApplicationContextSingleton.getContext().getBean(BookCacheWriter.class);
    }
}
