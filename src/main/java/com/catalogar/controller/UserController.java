package com.catalogar.controller;

import com.catalogar.dto.ApiResponse;
import com.catalogar.dto.UserDto;
import com.catalogar.dto.UserRequestDto;
import com.catalogar.mapper.UserMapper;
import com.catalogar.model.User;
import com.catalogar.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("me")
    public ResponseEntity<ApiResponse<UserDto>> getMe() {
        String uuidSting = "f0b994db-fce7-417d-b778-333302e82a5d";
        UUID uuid = UUID.fromString(uuidSting);

        User user = userService.getById(uuid);

        return ResponseEntity.ok()
                .body(userMapper.toApiResponse(user));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> create(
            @Valid @RequestBody UserRequestDto userRequestDto
    ) {
         User user = userService.create(userRequestDto);

         return ResponseEntity.ok()
                 .body(userMapper.toApiResponse(user));
    }
}
