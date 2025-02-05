package com.catalogar.user;

import com.catalogar.catalog.CatalogDto;
import com.catalogar.catalog.CatalogMapper;
import com.catalogar.common.dto.ApiResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserMapper {
    private final CatalogMapper catalogMapper;
    private final PasswordEncoder passwordEncoder;

    public UserMapper(CatalogMapper catalogMapper, PasswordEncoder passwordEncoder) {
        this.catalogMapper = catalogMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User toUser(CreateUserRequest request) {
        String encodedPassword = passwordEncoder
                .encode(request.password());

        return new User(
                request.name(),
                request.email(),
                encodedPassword
        );
    }

    public ApiResponse<UserDto> toApiResponse(User user) {
        UserDto userDto = this.toDto(user);

        return new ApiResponse<UserDto>(userDto);
    }

    public UserDto toDto(User user) {
        List<CatalogDto> catalogsDto = user.getCatalogs()
                .stream()
                .map(catalogMapper::toDto)
                .toList();

        CatalogDto currentCatalogDto = Optional.ofNullable(user.getCurrentCatalog())
                .map(catalogMapper::toDto)
                .orElse(null);

        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                catalogsDto,
                currentCatalogDto,
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

}
