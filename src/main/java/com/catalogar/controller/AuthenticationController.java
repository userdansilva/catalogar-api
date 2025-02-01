package com.catalogar.controller;

import com.catalogar.dto.*;
import com.catalogar.mapper.UserMapper;
import com.catalogar.model.User;
import com.catalogar.service.AuthenticationService;
import com.catalogar.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public AuthenticationController(
            AuthenticationService authenticationService,
            JwtService jwtService,
            UserMapper userMapper
    ) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
    }

    @PostMapping("signup")
    public ResponseEntity<ApiResponse<UserDto>> register(
            @Valid @RequestBody UserRequestDto userRequestDto
    ) {
        User user = authenticationService.signup(userRequestDto);

        return ResponseEntity.ok()
                .body(userMapper.toApiResponse(user));
    }

    @PostMapping("login")
    public ResponseEntity<LoginDto> authenticate(
            @Valid @RequestBody LoginRequestDto loginRequestDto
    ) {
        User user = authenticationService
                .authenticate(loginRequestDto);

        String jwtToken = jwtService.generateToken(user);

        LoginDto loginDto = new LoginDto(
                jwtToken,
                jwtService.getExpirationTime()
        );

        return ResponseEntity.ok().body(loginDto);
    }
}
