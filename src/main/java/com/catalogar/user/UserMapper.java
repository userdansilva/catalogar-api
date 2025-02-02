package com.catalogar.user;

import com.catalogar.common.dto.ApiResponse;
import com.catalogar.catalog.CatalogDto;
import com.catalogar.catalog.CatalogMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    private final CatalogMapper catalogMapper;

    public UserMapper(CatalogMapper catalogMapper) {
        this.catalogMapper = catalogMapper;
    }

    public User toUser(CreateUserRequest userRequestDto) {
        return new User(
                userRequestDto.name(),
                userRequestDto.email(),
                userRequestDto.password(),
                userRequestDto.phoneNumber()
        );
    }

    public ApiResponse<UserDto> toApiResponse(User user) {
        UserDto userDto = this.toDto(user);

        return new ApiResponse<UserDto>(userDto);
    }

    public UserDto toDto(User user) {
        List<CatalogDto> catalogDtoList = user.getCatalogs()
                .stream()
                .map(catalogMapper::toDto)
                .toList();

        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                catalogDtoList,
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public CreateUserRequest toRequestDto(User user) {
        return new CreateUserRequest(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber()
        );
    }
}
