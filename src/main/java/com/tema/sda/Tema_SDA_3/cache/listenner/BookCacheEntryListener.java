package com.tema.sda.Tema_SDA_3.cache.listenner;

import com.tema.sda.Tema_SDA_3.data.entity.Book;

import javax.cache.event.*;

public class BookCacheEntryListener implements CacheEntryCreatedListener<Long, Book>,
        CacheEntryUpdatedListener<Long, Book>,
        CacheEntryRemovedListener<Long, Book>,
        CacheEntryExpiredListener<Long, Book> {

    @Override
    public void onCreated(Iterable<CacheEntryEvent<? extends Long, ? extends Book>> cacheEntryEvents) throws CacheEntryListenerException {
        System.out.println("Created");
        cacheEntryEvents.forEach(System.out::println);
    }

    @Override
    public void onExpired(Iterable<CacheEntryEvent<? extends Long, ? extends Book>> cacheEntryEvents) throws CacheEntryListenerException {
        System.out.println("Expired");
        cacheEntryEvents.forEach(System.out::println);
    }

    @Override
    public void onRemoved(Iterable<CacheEntryEvent<? extends Long, ? extends Book>> cacheEntryEvents) throws CacheEntryListenerException {
        System.out.println("Removed");
        cacheEntryEvents.forEach(System.out::println);
    }

    @Override
    public void onUpdated(Iterable<CacheEntryEvent<? extends Long, ? extends Book>> cacheEntryEvents) throws CacheEntryListenerException {
        System.out.println("Updated");
        cacheEntryEvents.forEach(System.out::println);
    }
}