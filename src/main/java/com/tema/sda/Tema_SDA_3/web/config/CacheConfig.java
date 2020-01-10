package com.tema.sda.Tema_SDA_3.web.config;

import com.tema.sda.Tema_SDA_3.cache.factory.BookCacheLoadingFactory;
import com.tema.sda.Tema_SDA_3.cache.factory.BookCacheWriterFactory;
import com.tema.sda.Tema_SDA_3.cache.listenner.BookCacheEntryListener;
import com.tema.sda.Tema_SDA_3.data.entity.Book;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableCacheEntryListenerConfiguration;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;
import javax.cache.expiry.TouchedExpiryPolicy;
import javax.cache.spi.CachingProvider;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean(destroyMethod = "close")
    CachingProvider getCacheProvider() {
        return Caching.getCachingProvider();
    }

    @Bean(destroyMethod = "close")
    CacheManager createCacheManager(CachingProvider provider) {
        return provider.getCacheManager();
    }

    @Bean
    MutableCacheEntryListenerConfiguration<Long, Book> cacheEntryListener() {
        return new MutableCacheEntryListenerConfiguration<>(
                FactoryBuilder.factoryOf(BookCacheEntryListener.class),
                null,
                true,
                true
        );
    }

    @Bean(name = "bookCacheConfig")
    MutableConfiguration<Long, Book> createCustomerCacheConfig(MutableCacheEntryListenerConfiguration<Long, Book> listeners) {
        MutableConfiguration<Long, Book> config = new MutableConfiguration<>();
        config.setCacheLoaderFactory(new BookCacheLoadingFactory());
        config.setWriteThrough(true);
        config.setCacheWriterFactory(new BookCacheWriterFactory());
        config.setReadThrough(true);
        config.setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(new Duration(TimeUnit.MINUTES, 30)));
        config.addCacheEntryListenerConfiguration(listeners);
        return config;
    }

    @Bean(name = "bookCache")
    Cache<Long, Book> createCustomersCache(CacheManager manager,
                                           @Qualifier("bookCacheConfig") MutableConfiguration<Long, Book> configuration) {
        return manager.createCache("bookCache", configuration);
    }

}
