package com.catalogar.user;

import com.catalogar.catalog.Catalog;
import com.catalogar.common.config.Utilities;
import com.catalogar.common.exception.ResourceNotFoundException;
import com.catalogar.common.message.MessageService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final MessageService messageService;

    public UserService(UserRepository userRepository,
                       MessageService messageService) {
        this.userRepository = userRepository;
        this.messageService = messageService;
    }

    public User getById(UUID id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageService.getMessage("error.user.not_found")
                ));
    }

    public User getByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageService.getMessage("error.user.not_found")
                ));
    }

    public User getByJwtOrCreate(Jwt jwt) {
        String name = Utilities.filterClaims(jwt).get("name");
        String email = Utilities.filterClaims(jwt).get("email");

        return userRepository.findByEmail(email)
                .orElseGet(() -> create(new User(name, email)));
    }

    private User create(User user) {
        return userRepository.save(user);
    }

    public User updateCurrentCatalog(User user, Catalog catalog) {
        user.setCurrentCatalog(catalog);

        return userRepository.save(user);
    }

    public Catalog getUserCurrentCatalog(User user) {
        return Optional.ofNullable(user.getCurrentCatalog())
                .orElseThrow(() -> new ResourceNotFoundException(messageService
                        .getMessage("error.catalog.current_catalog_not_found")));
    }

}
