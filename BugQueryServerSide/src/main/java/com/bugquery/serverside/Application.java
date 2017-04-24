package com.bugquery.serverside;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.bugquery.serverside.entities.StackOverflowPost;
import com.bugquery.serverside.repositories.StackOverflowPostRepository;

/**
 * here everything starts, default class for running spring boot application
 * 
 * @author Amit
 * @since Dec 24, 2016
 *
 */
@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class,args);
	}
	
	@Autowired
	StackOverflowPostRepository soRepo;
	
	@Bean
	public CommandLineRunner checkDBargs() {
		return (args) -> {
			StackOverflowPost soPost = new StackOverflowPost("id", 0, "parentId", "acceptedAnswerId", 0, "body", "title", "tags", 2);
			soRepo.save(soPost);
			if (args.length > 0) {
				if ("--createDB".equals(args[0]))
					log.info("Created DB");
					//create DB
				if ("--updateDB".equals(args[0]))
				 log.info("Updated DB");
			}
		};
	}

}