package com.tema.sda.Tema_SDA_3.data.repository;

import com.tema.sda.Tema_SDA_3.data.entity.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Indexed
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    Optional<Book> findAllByTitle(final String title);

    Optional<Book> findAllByTitleAndAuthorAndVolum(final String title, final String author, final int volum);

    int deleteBookByTitleAndAuthorAndVolum(final String title, final String author, final int volum);

    @Query("select b from Book b order by b.totalNumberOfPages asc")
    Optional<List<Book>> getAllBooksSortedByTotalNumberOfPages();

}
