package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

@SpringBootApplication
public class SpringDataJpaSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaSampleApplication.class, args);
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Bean
	public CommandLineRunner demo(CustomerRepository customerRepository) {
		return (args) -> {
			customerRepository.save(new Customer("Jayant", "Kumar"));
			customerRepository.save(new Customer("Bhavna", "Jy"));
			customerRepository.save(new Customer("Latha", "Jangay"));

			customerRepository.findAll().forEach(customer -> System.out.println(customer));

			Optional<Customer> customer0 = customerRepository.findById(1L);
			System.out.println(customer0.isPresent() ? customer0.get() : "None Found");

			customerRepository.findByLastName("Jy").forEach(customer -> System.out.println(customer));

			jdbcTemplate.query("select id, first_name, last_name from customer", (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))).
					forEach(customer -> System.out.println(customer));
		};
	}
}
