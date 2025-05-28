package com.catalogar.storage;

import com.catalogar.common.dto.ApiResponse;
import com.catalogar.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/storage")
public class StorageController {
    private final UserService userService;
    private final StorageService storageService;
    private final StorageMapper storageMapper;

    public StorageController(UserService userService, StorageService storageService, StorageMapper storageMapper) {
        this.userService = userService;
        this.storageService = storageService;
        this.storageMapper = storageMapper;
    }

    @PostMapping("/generate-sas-token")
    public ResponseEntity<ApiResponse<StorageDto>> generateSasToken(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody StorageRequest request
    ) {
        userService.getByJwtOrCreate(jwt);
        Storage storage = storageService.generateSasToken(request);

        return ResponseEntity.ok().body(storageMapper.toApiResponse(storage));
    }
}
