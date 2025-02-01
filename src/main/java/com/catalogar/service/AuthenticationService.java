package com.catalogar.service;

import com.catalogar.dto.LoginRequestDto;
import com.catalogar.dto.UserRequestDto;
import com.catalogar.model.User;
import com.catalogar.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public AuthenticationService(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            UserService userService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public User signup(UserRequestDto userRequestDto) {
        String encodedPassword = passwordEncoder
                .encode(userRequestDto.password());

        User user = new User(
                userRequestDto.name(),
                userRequestDto.email(),
                encodedPassword,
                userRequestDto.phoneNumber()
        );

        return userService.create(user);
    }

    public User authenticate(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.email(),
                        loginRequestDto.password()
                )
        );

        return userRepository
                .findByEmail(loginRequestDto.email())
                .orElseThrow();
    }
}
