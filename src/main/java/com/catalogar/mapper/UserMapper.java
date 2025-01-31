package com.catalogar.mapper;

import com.catalogar.dto.ApiResponse;
import com.catalogar.dto.CatalogDto;
import com.catalogar.dto.UserDto;
import com.catalogar.dto.UserRequestDto;
import com.catalogar.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    private final CatalogMapper catalogMapper;

    public UserMapper(CatalogMapper catalogMapper) {
        this.catalogMapper = catalogMapper;
    }

    public User toUser(UserRequestDto userRequestDto) {
        return new User(
                userRequestDto.name(),
                userRequestDto.email(),
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
}
