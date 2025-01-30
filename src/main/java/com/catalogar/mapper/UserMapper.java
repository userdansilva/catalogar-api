package com.catalogar.mapper;

import com.catalogar.dto.ApiResponse;
import com.catalogar.dto.UserDto;
import com.catalogar.dto.UserRequestDto;
import com.catalogar.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(UserRequestDto userRequestDto) {
        return new User(
                userRequestDto.name(),
                userRequestDto.email()
        );
    }

    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public ApiResponse<UserDto> toApiResponse(User user) {
        UserDto userDto = this.toDto(user);

        return new ApiResponse<UserDto>(userDto);
    }
}
