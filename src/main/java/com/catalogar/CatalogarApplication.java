package com.catalogar;

import com.catalogar.model.Category;
import com.catalogar.repository.CategoryRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CatalogarApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogarApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(CategoryRepository categoryRepository) {
		return args -> {
			Faker faker = new Faker();

			for (int i = 0; i <= 20; i++) {
				Category category = new Category(
						faker.funnyName().name(),
						faker.lorem().word(),
						faker.color().hex(),
						faker.color().hex()
				);

				categoryRepository.save(category);
			}
		};
	}

}
