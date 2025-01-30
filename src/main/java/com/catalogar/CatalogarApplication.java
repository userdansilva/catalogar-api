package com.catalogar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CatalogarApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogarApplication.class, args);
	}

//	@Bean
//	CommandLineRunner commandLineRunner(
//			CatalogService catalogService,
//			CompanyService companyService
//	) {
//		return args -> {
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
//
//			System.out.println("--- CommandLineRunner finished! ---");
//		};
//	}
}
