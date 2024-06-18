package com.desafio.book;

import com.desafio.book.repository.AuthorRepository;
import com.desafio.book.repository.BookRepository;
import com.desafio.book.master.Master;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBookApplication implements CommandLineRunner {

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private BookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringBookApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Master master = new Master(bookRepository, authorRepository);
		master.menu();
	}
}
