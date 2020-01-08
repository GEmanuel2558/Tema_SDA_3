package com.tema.sda.Tema_SDA_3.data.repository;

import com.tema.sda.Tema_SDA_3.data.entity.Book;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Indexed
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    Optional<Book> findAllByTitle(final String title);

}
