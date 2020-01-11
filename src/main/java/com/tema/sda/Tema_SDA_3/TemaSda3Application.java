package com.tema.sda.Tema_SDA_3;

import com.tema.sda.Tema_SDA_3.data.entity.Book;
import com.tema.sda.Tema_SDA_3.data.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TemaSda3Application implements CommandLineRunner {

	private final BookRepository repository;

	public TemaSda3Application(BookRepository repository) {
		this.repository = repository;
	}

	public static void main(String[] args) {
		SpringApplication.run(TemaSda3Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Book book=new Book();
		book.setAuthor("Emanuel");
		book.setBorrow(false);
		book.setSection("SF");
		book.setTitle("Cartea 1");
		book.setTotalNumberOfPages(250);
		book.setVolum(1);

		this.repository.save(book);

		Book book2=new Book();
		book2.setAuthor("Mihai");
		book2.setBorrow(false);
		book2.setSection("DRAMA");
		book2.setTitle("Cartea 2");
		book2.setTotalNumberOfPages(100);
		book2.setVolum(1);

		this.repository.save(book2);

		Book book3=new Book();
		book3.setAuthor("Oana");
		book3.setBorrow(true);
		book3.setBorrowedTo("Mihaela");
		book3.setSection("POLITIST");
		book3.setTitle("Cartea 3");
		book3.setTotalNumberOfPages(182);
		book3.setVolum(1);

		this.repository.save(book3);

		Book book4=new Book();
		book4.setAuthor("Ioana");
		book4.setBorrow(true);
		book4.setBorrowedTo("Victor");
		book4.setSection("ROMANTIC");
		book4.setTitle("Cartea 4");
		book4.setTotalNumberOfPages(231);
		book4.setVolum(2);

		this.repository.save(book4);
	}
}
