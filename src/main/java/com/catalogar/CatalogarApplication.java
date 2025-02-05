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
//			CompanyService companyService,
//			UserService userService,
//			AuthenticationService authenticationService
//	) {
//		return args -> {
//			CreateUserRequest user = new CreateUserRequest(
//					"Daniel Sousa",
//					"daniel.sousa@catalogar.com.br",
//					"password"
//			);
//
//			authenticationService.signup(user);
//
//			System.out.println("--- CommandLineRunner finished! ---");
//		};
//	}
}
