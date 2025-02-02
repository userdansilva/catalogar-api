package com.catalogar.user;

import com.catalogar.catalog.Catalog;
import com.catalogar.common.exception.ResourceNotFoundException;
import com.catalogar.common.exception.UniqueFieldConflictException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User getById(UUID id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuário com o id: " + id + " não encontrado"
                ));
    }

    public User getByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuário com o email: " + email + " não encontrado"
                ));
    }

    public User create(CreateUserRequest request) {
        boolean existsByEmail = userRepository
                .existsByEmail(request.email());

        if (existsByEmail) {
            throw new UniqueFieldConflictException(
                    "Usuário com o email: " + request.email() + " já está cadastrado"
            );
        }

        return userRepository.save(userMapper.toUser(request));
    }

    public void setCurrentCatalog(User user, Catalog catalog) {
        user.setCurrentCatalogId(catalog.getId());

        userRepository.save(user);
    }
}
