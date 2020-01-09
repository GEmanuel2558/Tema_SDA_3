package com.tema.sda.Tema_SDA_3.cache.io;

import com.tema.sda.Tema_SDA_3.data.entity.Book;
import com.tema.sda.Tema_SDA_3.data.repository.BookRepository;
import org.springframework.stereotype.Component;

import javax.cache.Cache;
import javax.cache.integration.CacheWriter;
import javax.cache.integration.CacheWriterException;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class BookCacheWriter implements CacheWriter<Long, Book> {

    private final BookRepository db;

    public BookCacheWriter(BookRepository db) {
        this.db = db;
    }

    @Override
    public void write(Cache.Entry<? extends Long, ? extends Book> entry) throws CacheWriterException {
        db.save(entry.getValue());
    }

    @Override
    public void writeAll(Collection<Cache.Entry<? extends Long, ? extends Book>> entries) throws CacheWriterException {
        db.saveAll(entries.stream().map(Cache.Entry::getValue).collect(Collectors.toList()));
    }

    @Override
    public void delete(Object key) throws CacheWriterException {
        db.deleteById((Long) key);
    }

    @Override
    public void deleteAll(Collection<?> keys) throws CacheWriterException {
        db.customDeleteBooksByIds((Collection<Long>) keys);
    }
}
