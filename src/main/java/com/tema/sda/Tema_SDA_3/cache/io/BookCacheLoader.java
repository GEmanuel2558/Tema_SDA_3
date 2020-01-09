package com.tema.sda.Tema_SDA_3.cache.io;

import com.tema.sda.Tema_SDA_3.data.entity.Book;
import com.tema.sda.Tema_SDA_3.data.repository.BookRepository;
import org.springframework.stereotype.Component;

import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class BookCacheLoader implements CacheLoader<Long, Book> {

    private final BookRepository db;

    public BookCacheLoader(BookRepository db) {
        this.db = db;
    }

    @Override
    public Book load(Long key) throws CacheLoaderException {
        Optional<Book> fetchCustomer = db.findById(key);
        return fetchCustomer.orElseGet(Book::new);
    }

    @Override
    public Map<Long, Book> loadAll(Iterable<? extends Long> keys) throws CacheLoaderException {
        Iterable<Book> iterable = db.findAllById((Iterable<Long>) keys);
        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toMap(Book::getId, Function.identity()));
    }
}
