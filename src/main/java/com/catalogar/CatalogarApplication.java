package com.catalogar;

import com.catalogar.dto.UserRequestDto;
import com.catalogar.service.CatalogService;
import com.catalogar.service.CompanyService;
import com.catalogar.service.UserService;
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
	CommandLineRunner commandLineRunner(
			CatalogService catalogService,
			CompanyService companyService,
			UserService userService
	) {
		return args -> {
			UserRequestDto user = new UserRequestDto(
					"Daniel Sousa",
					"daniel.sousa@catalogar.com.br"
			);

			userService.create(user);

//			Catalog catalog = new Catalog(
//				"catalogar",
//				ZonedDateTime.now()
//			);
//
//			Company company = companyService.create(
//					new CompanyRequestDto(
//							"Catalogar",
//							"https://catalogar.com.br",
//							"(77) 91234-5678",
//							"https://catalogar.com.br/logo.svg"
//					),
//					catalog
//			);
//
//			System.out.println(company);

			System.out.println("--- CommandLineRunner finished! ---");
		};
	}
}
