package com.catalogar.image;

import com.catalogar.common.dto.ApiResponse;
import com.catalogar.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {
    private final UserService userService;
    private final ImageService imageService;
    private final ImageMapper imageMapper;

    public ImageController(UserService userService, ImageService imageService, ImageMapper imageMapper) {
        this.userService = userService;
        this.imageService = imageService;
        this.imageMapper = imageMapper;
    }

    @GetMapping("/generate-sas-token")
    public ResponseEntity<ApiResponse<ImageSasTokenDto>> generateSasToken(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam String fileName
    ) {
        userService.getByJwtOrCreate(jwt);
        ImageSasToken imageSasToken = imageService.generateSasToken(fileName);

        return ResponseEntity.ok().body(imageMapper.toApiResponse(imageSasToken));
    }
}
