package com.catalogar.user;

import com.catalogar.common.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDto>> getAuthenticatedUser(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = userService.getByEmail(userDetails.getUsername());

        return ResponseEntity.ok()
                .body(userMapper.toApiResponse(user));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> create(
            @Valid @RequestBody CreateUserRequest userRequestDto
    ) {
         User user = userService.create(userRequestDto);

         return ResponseEntity.ok()
                 .body(userMapper.toApiResponse(user));
    }
}
