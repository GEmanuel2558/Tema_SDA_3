package com.tema.sda.Tema_SDA_3.cache.factory;


import com.tema.sda.Tema_SDA_3.business.service.global.ApplicationContextSingleton;
import com.tema.sda.Tema_SDA_3.cache.io.BookCacheLoader;

import javax.cache.configuration.Factory;

public class BookCacheLoadingFactory implements Factory<BookCacheLoader> {
    @Override
    public BookCacheLoader create() {
        return ApplicationContextSingleton.getContext().getBean(BookCacheLoader.class);
    }
}
