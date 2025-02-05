package com.catalogar.auth;

import com.catalogar.common.dto.ApiResponse;
import com.catalogar.user.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public AuthenticationController(
            AuthenticationService authenticationService,
            UserService userService,
            JwtService jwtService,
            UserMapper userMapper
    ) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserDto>> register(
            @Valid @RequestBody CreateUserRequest request
    ) {
        User user = authenticationService.signup(request);

        return ResponseEntity.ok()
                .body(userMapper.toApiResponse(user));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDto> authenticate(
            @Valid @RequestBody LoginRequest request
    ) {
        authenticationService.authenticate(request);

        User user = userService.getByEmail(request.email());

        LoginDto loginDto = new LoginDto(
                jwtService.generateToken(user),
                jwtService.getExpirationTime()
        );

        return ResponseEntity.ok()
                .body(loginDto);
    }
}
