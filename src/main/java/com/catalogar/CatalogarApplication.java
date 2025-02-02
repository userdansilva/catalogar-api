package com.catalogar;

import com.catalogar.user.CreateUserRequest;
import com.catalogar.auth.AuthenticationService;
import com.catalogar.catalog.CatalogService;
import com.catalogar.company.CompanyService;
import com.catalogar.user.UserService;
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
			UserService userService,
			AuthenticationService authenticationService
	) {
		return args -> {
			CreateUserRequest user = new CreateUserRequest(
					"Daniel Sousa",
					"daniel.sousa@catalogar.com.br",
					"password",
					"(77) 98877-6655"
			);

			authenticationService.signup(user);

			System.out.println("--- CommandLineRunner finished! ---");
		};
	}
}
